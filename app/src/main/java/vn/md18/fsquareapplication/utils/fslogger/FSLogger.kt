package vn.md18.fsquareapplication.utils.fslogger

import android.content.Context
import android.os.Build
import com.elvishew.xlog.LogConfiguration
import com.elvishew.xlog.LogLevel
import com.elvishew.xlog.XLog
import com.elvishew.xlog.flattener.ClassicFlattener
import com.elvishew.xlog.interceptor.BlacklistTagsFilterInterceptor
import com.elvishew.xlog.libcat.LibCat
import com.elvishew.xlog.printer.AndroidPrinter
import com.elvishew.xlog.printer.Printer
import com.elvishew.xlog.printer.file.FilePrinter
import com.elvishew.xlog.printer.file.backup.FileSizeBackupStrategy2
import com.elvishew.xlog.printer.file.writer.SimpleWriter
import io.reactivex.disposables.CompositeDisposable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import timber.log.Timber
import vn.md18.fsquareapplication.BuildConfig
import vn.md18.fsquareapplication.FSquareApplication
import vn.md18.fsquareapplication.di.component.scheduler.SchedulerProviderImpl
import vn.md18.fsquareapplication.utils.Constant
import vn.md18.fsquareapplication.utils.Constant.TXT_EXTENTION
import vn.md18.fsquareapplication.utils.ErrorUtils
import vn.md18.fsquareapplication.utils.FileUtil
import vn.md18.fsquareapplication.utils.extensions.delayFunction
import java.io.File

object FSLogger {
    private var globalFilePrinter: Printer? = null
    private const val ANONYMOUS_CLASS = '$'
    private const val MAX_TAG_LENGTH = 23

    private val fqcnIgnore = listOf(
        FSLogger::class.java.name,
    )


    private val apiService get() = FSquareApplication.getInstance().apiService
    private val compositeDisposable = CompositeDisposable()
    private var enableLog: Int? = null
    private var sendLogFrequency: Int? = null
    private var lastLogUpload: Long? = null
    private var maxFileSize: Long = 10 * 1024 * 1024
    private var maxFileSizeProd: Long = 5 * 1024 * 1024

    /**
     * Initialize XLog.
     */
    fun initOneHomeLogger(context: Context) {
        val config = LogConfiguration.Builder()
            .logLevel(
                LogLevel.ALL // Specify log level, logs below this level won't be printed, default: LogLevel.ALL
            )
            .tag(javaClass.simpleName) // Specify TAG, default: "X-LOG"
//            .enableThreadInfo()                                 // Enable thread info, disabled by default
            // .enableStackTrace(2)                                // Enable stack trace info with depth 2, disabled by default
//            .enableBorder()                                     // Enable border, disabled by default
//             .jsonFormatter(OneHomeJsonFormatter())               // Default: DefaultJsonFormatter
            // .xmlFormatter(new MyXmlFormatter())                 // Default: DefaultXmlFormatter
            // .throwableFormatter(new MyThrowableFormatter())     // Default: DefaultThrowableFormatter
            // .threadFormatter(new MyThreadFormatter())           // Default: DefaultThreadFormatter
            // .stackTraceFormatter(new MyStackTraceFormatter())   // Default: DefaultStackTraceFormatter
            // .borderFormatter(new MyBoardFormatter())            // Default: DefaultBorderFormatter
            // .addObjectFormatter(AnyClass.class,                 // Add formatter for specific class of object
            //     new AnyClassObjectFormatter())                  // Use Object.toString() by default
            .addInterceptor(
                BlacklistTagsFilterInterceptor( // Add blacklist tags filter
                    "blacklist1", "blacklist2", "blacklist3"
                )
            ) // .addInterceptor(new WhitelistTagsFilterInterceptor( // Add whitelist tags filter
            //     "whitelist1", "whitelist2", "whitelist3"))
            // .addInterceptor(new MyInterceptor())                // Add a log interceptor
            .build()
        val androidPrinter: com.elvishew.xlog.printer.Printer =
            AndroidPrinter() // Printer that print the log using android.util.Log
        val filePrinter: Printer = FilePrinter.Builder(
            File(
                context.externalCacheDir?.absolutePath,
                "log"
            ).path
        ) // Specify the path to save log file
            .fileNameGenerator(FSFileNameGenerator()) // Default: ChangelessFileNameGenerator("log")
            .backupStrategy(
                if (BuildConfig.ENV == Constant.BuildFlavor.Prod.value) {
                    FileSizeBackupStrategy2(
                        maxFileSizeProd,
                        1
                    )
                } else FileSizeBackupStrategy2(maxFileSize, 2)
            )             // Default: FileSizeBackupStrategy(1024 * 1024)
            // .cleanStrategy(new FileLastModifiedCleanStrategy(MAX_TIME))     // Default: NeverCleanStrategy()
            .flattener(ClassicFlattener()) // Default: DefaultFlattener
            .writer(object : SimpleWriter() {
                // Default: SimpleWriter
                override fun onNewFileCreated(file: File) {
                    super.onNewFileCreated(file)
                    val header = """
                 
                 >>>>>>>>>>>>>>>> File Header >>>>>>>>>>>>>>>>
                 Device Manufacturer: ${Build.MANUFACTURER}
                 Device Model       : ${Build.MODEL}
                 Android Version    : ${Build.VERSION.RELEASE}
                 Android SDK        : ${Build.VERSION.SDK_INT}
                 App VersionName    : ${BuildConfig.VERSION_NAME}
                 App VersionCode    : ${BuildConfig.VERSION_CODE}
                 <<<<<<<<<<<<<<<< File Header <<<<<<<<<<<<<<<<
                 
                 """.trimIndent()
                    appendLog(header)
                }
            })
            .build()

        if (BuildConfig.ENV == Constant.BuildFlavor.Prod.value) {
            XLog.init( // Initialize XLog
                config,  // Specify the log configuration, if not specified, will use new LogConfiguration.Builder().build()
                filePrinter
            )
        } else {
            XLog.init( // Initialize XLog
                config,  // Specify the log configuration, if not specified, will use new LogConfiguration.Builder().build()
                androidPrinter, //Bật tắt hiển thị log
                filePrinter //Ghi file log
            )
        }


        // For future usage: partial usage in MainActivity.
        globalFilePrinter = filePrinter

        // Intercept all logs(including logs logged by third party modules/libraries) and print them to file.
        LibCat.config(true, filePrinter)

    }

    fun e(message: String) {
        prepareLog(LogLevel.ERROR, message)
    }

    @JvmName("OHE")
    fun e(tag: String, message: String) {
        prepareLog(LogLevel.ERROR, message, tag = tag)
    }

    fun e(message: String, throwMessage: String?) {
        prepareLog(LogLevel.ERROR, message, throwMessage = throwMessage)
    }

    fun d(message: String) {
        prepareLog(LogLevel.DEBUG, message)
    }

    @JvmName("OHD")
    fun d(tag: String, message: String) {
        prepareLog(LogLevel.DEBUG, message, tag = tag)
    }

    fun d(message: String, throwMessage: String?) {
        prepareLog(LogLevel.DEBUG, message, throwMessage = throwMessage)
    }

    fun v(message: String) {
        prepareLog(LogLevel.VERBOSE, message)
    }

    @JvmName("OHV")
    fun v(tag: String, message: String) {
        prepareLog(LogLevel.VERBOSE, message, tag = tag)
    }

    fun v(message: String, throwMessage: String?) {
        prepareLog(LogLevel.VERBOSE, message, throwMessage = throwMessage)
    }

    fun i(message: String) {
        prepareLog(LogLevel.INFO, message)
    }

    @JvmName("OHI")
    fun i(tag: String, message: String) {
        prepareLog(LogLevel.INFO, message, tag = tag)
    }

    fun i(message: String, throwMessage: String?) {
        prepareLog(LogLevel.INFO, message, throwMessage = throwMessage)
    }

    fun w(message: String) {
        prepareLog(LogLevel.WARN, message)
    }

    @JvmName("OHW")
    fun w(tag: String, message: String) {
        prepareLog(LogLevel.WARN, message, tag = tag)
    }

    fun w(message: String, throwMessage: String?) {
        prepareLog(LogLevel.WARN, message, throwMessage = throwMessage)
    }

    fun json(json: String) {
        prepareLog(LogLevel.NONE, json)
    }

    private fun prepareLog(
        level: Int,
        message: String,
        tag: String? = null,
        throwMessage: String? = null
    ) {
        var tagLog = tag

        if (tagLog == null) {
            val stackTrace = Throwable().stackTrace
            tagLog = stackTrace.first { it.className !in fqcnIgnore }?.let(::createStackElementTag)
        }

        val buildLogger =
            if (throwMessage != null) XLog.enableStackTrace(2).tag(tagLog).enableBorder()
                .build() else XLog.tag(tagLog).build()
        val validateMessage = throwMessage?.let {
            "$message: $throwMessage"
        } ?: message

        when (level) {
            LogLevel.ERROR -> {
                buildLogger.e(validateMessage)
            }

            LogLevel.VERBOSE -> {
                buildLogger.v(validateMessage)
            }

            LogLevel.DEBUG -> {
                buildLogger.d(validateMessage)
            }

            LogLevel.INFO -> {
                buildLogger.i(validateMessage)
            }

            LogLevel.WARN -> {
                buildLogger.w(validateMessage)
            }

            LogLevel.NONE -> {
                buildLogger.json(validateMessage)
            }
        }
    }

    private fun createStackElementTag(element: StackTraceElement): String {
        val tag = element.className.substringAfterLast('.')
        val m = ANONYMOUS_CLASS in tag
        if (m) {
            return tag.substringBefore('$')
        }

        // Tag length limit was removed in API 26.
        return if (tag.length <= MAX_TAG_LENGTH || Build.VERSION.SDK_INT >= 26) {
            tag
        } else {
            tag.substring(0, MAX_TAG_LENGTH)
        }
    }

    fun prepareLogFile() {
        val path = FileUtil.makeExternalCacheDirPath(
            FileUtil.formatFileLogOneHome(
                timestamp = System.currentTimeMillis(),
                suffix = TXT_EXTENTION
            )
        )
        //Kiểm tra hợp lệ để chuẩn bị uplog
        validatePrepareLog {
            startUpdateLogFile(path)
        }
    }

    private fun validatePrepareLog(function: () -> Unit) {
        /// User không cho phép upload
        if (this.enableLog == 0 || this.enableLog == null) return

        /// Chưa update lần nào
        if (this.lastLogUpload == null) {
            function.invoke()
            return
        }

        // Dựa theo Frequency
        val frequencyInMillis = ((this.sendLogFrequency?.times(60) ?: 0) * 1000).toLong()
        this.lastLogUpload?.let { lastLogUpload ->
            if (System.currentTimeMillis() >= lastLogUpload + frequencyInMillis) {
                delayFunction(1000) {
                    function.invoke()
                }
            }
        }
    }

    private fun startUpdateLogFile(path: String) {
        FileUtil.uploadLogFile(path) {
            uploadLogFileToBE(it)
        }
    }

    private fun uploadLogFileToBE(file: File) {
        val requestBodyType =
            Constant.TYPE_UPLOAD_LOG_FILE.toRequestBody(MultipartBody.FORM)
        val requestFile: RequestBody =
            file.asRequestBody(MultipartBody.FORM)
        val requestBodyFile: MultipartBody.Part =
            MultipartBody.Part.createFormData("file", file.name, requestFile)

//        compositeDisposable.add(
//            apiService.uploadAvatar(
//                requestBodyFile, requestBodyType
//            ).subscribeOn(SchedulerProviderImpl().io())
//                .observeOn(SchedulerProviderImpl().io())
//                .subscribe({
//                    Timber.e("Upload log file successfully: ${it.fileName}")
//                    this.lastLogUpload = System.currentTimeMillis()
//                    file.delete()
//                }, { throwable ->
//                    ErrorUtils.getError(throwable)?.let { error ->
//                        Timber.e("Upload log file failed: ${error.message}")
//                    }
//                })
//        )
    }

    /**
     * Gán giá trị cho các biến dùng để Upload log file
     */
//    fun setupDataForUploadLog(user: UserResponse) {
//        this.apply {
//            enableLog = user.enableLog
//            sendLogFrequency = user.sendLogFrequency
//            lastLogUpload = user.lastLogUpload
//        }
//    }
}