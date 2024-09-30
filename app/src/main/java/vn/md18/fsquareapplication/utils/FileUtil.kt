package vn.md18.fsquareapplication.utils

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.content.CursorLoader
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.text.TextUtils
import timber.log.Timber
import vn.md18.fsquareapplication.BuildConfig
import vn.md18.fsquareapplication.FSquareApplication
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.text.CharacterIterator
import java.text.SimpleDateFormat
import java.text.StringCharacterIterator
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.TimeUnit
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream
import kotlin.math.abs

class FileUtil {

    companion object {
        fun fileSize(bytes: Long): String {
            val absB = if (bytes == Long.MIN_VALUE) Long.MAX_VALUE else abs(bytes)
            if (absB < 1024) {
                return "$bytes B"
            }
            var value = absB
            val ci: CharacterIterator = StringCharacterIterator("KMGTPE")
            var i = 40

            while (i >= 0 && absB > 0xfffccccccccccccL shr i) {
                value = value shr 10
                ci.next()
                i -= 10
            }
            value *= java.lang.Long.signum(bytes).toLong()
            return String.format("%.1f %cB", value / 1024.0, ci.current())
        }

        fun isFileLessThan(file: File, sizeFileMax: Int): Boolean {
            //1MB= 1024KB = 1024 * 1024B
            val maxSize = sizeFileMax * 1024 * 1024
            val sizeFile = file.length()
            val middleSizeFile = java.lang.Long.toString(sizeFile)
            val finalFileSize = middleSizeFile.toInt()
            return finalFileSize <= maxSize
        }

        fun convertBytesToMegaByte(bytes: Long): String {

            val megabytes = bytes.toDouble() / (1024 * 1024)

            return String
                .format("%.2f", megabytes)
                .replace(",", ".")
                .replace(".00", "")
                .plus(" MB")
        }

        private fun validate(file: File): Boolean {
            return file.exists()
        }

        private fun copyFile(fromFile: File, toFile: File, overwrite: Boolean = true): File? {
            try {
                /// Validate original file
                if (!validate(fromFile)) return null

                /// Validate toFile file
                //if (!validate(fromFile)) return null

                return fromFile.copyTo(toFile, overwrite)
            } catch (e: Exception) {
                return null
            }
        }

        fun uploadLogFile(path: String, function: (File) -> Unit) {
            /// original file
            val file = File(path)

            /// Copy filePath
            val copyFilePath = makeExternalCacheDirPath(
                "${Constant.SYSTEM_NAME}_${BuildConfig.APPLICATION_ID}_"
                        + "${DateUtils.getTimeStamp(Constant.TimeDateFormat.FORMAT_TIMESTAMP_LOG)}${Constant.LOG_EXTENSION}"
            )

            // Copy file
            val fileCopy = copyFile(file, File(copyFilePath))

            /// Send log
            fileCopy?.let {
                function.invoke(it)
            }
        }

        fun makeExternalCacheDirPath(path: String): String {
            return externalCacheDir().plus("log").plus(File.separator).plus(path)
        }

        private fun externalCacheDir() = FSquareApplication.getInstance().externalCacheDir?.path.plus(File.separator)

        fun formatFileLogOneHome(timestamp: Long, suffix: String): String {
            val sdf = SimpleDateFormat((Constant.TimeDateFormat.FORMAT_DATE_LOG), Locale.US)
            sdf.timeZone = TimeZone.getDefault()
            //vn.vnpt.ONEHome.stag-2024-01-08
            return BuildConfig.APPLICATION_ID.plus("-").plus(sdf.format(Date(timestamp)))
                .plus(suffix)
        }

        private const val BUFFER_SIZE = 6 * 1024
        private var filesListInDir: ArrayList<String> = ArrayList()

        /**
         * Nén một file
         * @param dir đường dẫn
         * @param zipDirName đường dẫn zip file
         */
        private fun zipFile(files: Array<String?>, zipFile: String) {
            var origin: BufferedInputStream?
            val out = ZipOutputStream(BufferedOutputStream(FileOutputStream(zipFile)))
            out.use { out ->
                val data = ByteArray(BUFFER_SIZE)
                for (i in files.indices) {
                    val file = FileInputStream(files[i])
                    origin = BufferedInputStream(file, BUFFER_SIZE)
                    origin?.use {
                        files[i]?.let {
                            val entry =
                                ZipEntry(files[i]?.substring(it.lastIndexOf("/").plus(1)))
                            out.putNextEntry(entry)
                            var count: Int? = null
                            while (origin?.read(data, 0, BUFFER_SIZE).also { readInt ->
                                    if (readInt != null) {
                                        count = readInt
                                    }
                                } != -1) {
                                count?.let { it1 -> out.write(data, 0, it1) }
                            }
                        }
                    }
                }
            }
        }

        /**
         * This method populates all the files in a directory to a List
         * @param dir
         * @throws IOException
         */
        private fun populateFilesList(dir: File) {
            val files = dir.listFiles()
            for (file in files) {
                if (file.isFile) filesListInDir.add(file.absolutePath) else populateFilesList(file)
            }
        }

        /**
         * Nén cả thư mục
         * @param dir đường dẫn
         * @param zipDirName đường dẫn zip file
         */
        private fun zipDirectory(dirFile: File, zipDirName: String, zipSuccess: (Boolean) -> Unit) {
            try {
                //now zip files one by one
                //create ZipOutputStream to write to the zip file
                val fos = FileOutputStream(zipDirName)
                val zos = ZipOutputStream(fos)
                for (filePath in filesListInDir) {
                    println("Zipping $filePath")
                    //for ZipEntry we need to keep only relative file path, so we used substring on absolute path
                    val zip =
                        ZipEntry(
                            filePath.substring(
                                dirFile.absolutePath.length + 1,
                                filePath.length
                            )
                        )
                    zos.putNextEntry(zip)
                    //read the file and write to ZipOutputStream
                    val fis = FileInputStream(filePath)
                    val buffer = ByteArray(1024)
                    var len: Int
                    while (fis.read(buffer).also { len = it } > 0) {
                        zos.write(buffer, 0, len)
                    }
                    zos.closeEntry()
                    fis.close()
                }
                zipSuccess.invoke(true)
                zos.close()
                fos.close()
            } catch (e: IOException) {
                zipSuccess.invoke(false)
                e.printStackTrace()
            }
        }

        private fun pathFileLogCompress(): File {
            val fileLogCompressPath = externalCacheDir().plus("ONEHomeLogCompress")
            return File(fileLogCompressPath)
        }

        /**
         * Tạo 1 folder rỗng để gắn file zip vào đó, xóa đi khi thoát ứng dụng
         */
        fun createLogCompressFolder() {
            if (!pathFileLogCompress().exists()) {
                pathFileLogCompress().mkdirs()
            }
        }

        fun removeLogCompressFolder() {
            if (pathFileLogCompress().exists()) {
                pathFileLogCompress().deleteRecursively()
            }
        }

        private fun externalCacheLogCompressDir() = externalCacheDir().plus("ONEHomeLogCompress").plus(File.separator)

        fun prepareShareFileLog(readyToShare: (Boolean) -> Unit) {
            val backupDB = File(externalCacheDir().plus( "log"))

            filesListInDir.clear()
            populateFilesList(backupDB)

            val dirZipFileName = externalCacheLogCompressDir().plus("ONEHomeLog.zip")
            zipDirectory(dirFile = backupDB, zipDirName = dirZipFileName) {
                readyToShare.invoke(it)
            }
        }

        /**
         * Format duration time milliseconds to ex "03:08:19"
         */
        fun formatDuration(duration: Long): String {
            return if (TimeUnit.MILLISECONDS.toHours(duration) > 0) {
                String.format(
                    Locale.getDefault(), "%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(duration),
                    TimeUnit.MILLISECONDS.toMinutes(duration) % TimeUnit.HOURS.toMinutes(1),
                    TimeUnit.MILLISECONDS.toSeconds(duration) % TimeUnit.MINUTES.toSeconds(1)
                )
            } else {
                String.format(
                    Locale.getDefault(), "%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(duration),
                    TimeUnit.MILLISECONDS.toSeconds(duration) % TimeUnit.MINUTES.toSeconds(1),
                )
            }
        }

    }

    object RealPathUtil {

        fun getRealPath(context: Context, fileUri: Uri): String? {
            // SDK >= 11 && SDK < 19
            return if (Build.VERSION.SDK_INT < 19) {
                getRealPathFromURIAPI11to18(context, fileUri)
            } else {
                getRealPathFromURIAPI19(context, fileUri)
            }// SDK > 19 (Android 4.4) and up
        }

        fun getImageFullPath(context: Context, fileUri: Uri): String? {
            // SDK >= 11 && SDK < 19
            return if (Build.VERSION.SDK_INT < 19) {
                getRealPathFromURIAPI11to18(context, fileUri)
            } else {
                getImageFullPathApi19(context, fileUri)
            }// SDK > 19 (Android 4.4) and up
        }

        @SuppressLint("NewApi")
        fun getRealPathFromURIAPI11to18(context: Context, contentUri: Uri): String? {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            var result: String? = null

            val cursorLoader = CursorLoader(context, contentUri, proj, null, null, null)
            val cursor = cursorLoader.loadInBackground()

            if (cursor != null) {
                val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                cursor.moveToFirst()
                result = cursor.getString(columnIndex)
                cursor.close()
            }
            return result
        }

        /**
         * Get a file path from a Uri. This will get the the path for Storage Access
         * Framework Documents, as well as the _data field for the MediaStore and
         * other file-based ContentProviders.
         *
         * @param context The context.
         * @param uri     The Uri to query.
         * @author Niks
         */
        @SuppressLint("NewApi")
        fun getRealPathFromURIAPI19(context: Context, uri: Uri): String? {

            val isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT

            // DocumentProvider
            if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
                // ExternalStorageProvider
                if (isExternalStorageDocument(uri)) {
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split =
                        docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    val type = split[0]

                    if ("primary".equals(type, ignoreCase = true)) {
                        return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                    }
                } else if (isDownloadsDocument(uri)) {
                    var cursor: Cursor? = null
                    try {
                        cursor = context.contentResolver.query(
                            uri,
                            arrayOf(MediaStore.MediaColumns.DISPLAY_NAME),
                            null,
                            null,
                            null
                        )
                        cursor!!.moveToNext()
                        val fileName = cursor.getString(0)
                        val path = Environment.getExternalStorageDirectory()
                            .toString() + "/Download/" + fileName
                        if (!TextUtils.isEmpty(path)) {
                            return path
                        }
                    } finally {
                        cursor?.close()
                    }
                    val id = DocumentsContract.getDocumentId(uri)
                    if (id.startsWith("raw:")) {
                        return id.replaceFirst("raw:".toRegex(), "")
                    }
                    val contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads"),
                        java.lang.Long.valueOf(id)
                    )

                    return getDataColumn(context, contentUri, null, null)
                } else if (isMediaDocument(uri)) {
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split =
                        docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    val type = split[0]

                    var contentUri: Uri? = null
                    when (type) {
                        "image" -> contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        "video" -> contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                        "audio" -> contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                    }

                    val selection = "_id=?"
                    val selectionArgs = arrayOf(split[1])

                    return getDataColumn(context, contentUri, selection, selectionArgs)
                }// MediaProvider
                // DownloadsProvider
            } else if ("content".equals(uri.scheme, ignoreCase = true)) {

                // Return the remote address
                return if (isGooglePhotosUri(uri)) uri.lastPathSegment else getDataColumn(
                    context,
                    uri,
                    null,
                    null
                )
            } else if ("file".equals(uri.scheme, ignoreCase = true)) {
                return uri.path
            }// File
            // MediaStore (and general)

            return null
        }

        private fun getImageFullPathApi19(context: Context, uri: Uri): String? {
            val isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
            if (isKitKat && isFileProviders(uri)) {
                var filePath: String?
                val projection =
                    arrayOf(MediaStore.Images.Media.DATA, MediaStore.Images.Media.DISPLAY_NAME)

                val cursor: Cursor? =
                    context.contentResolver?.query(uri, projection, null, null, null)

                if (cursor != null) {
                    try {
                        if (cursor.moveToFirst()) {
                            val columnIndexData: Int =
                                cursor.getColumnIndex(MediaStore.Images.Media.DATA)
                            val columnIndexDisplayName: Int =
                                cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)

                            filePath = if (columnIndexData != -1) {
                                cursor.getString(columnIndexData)
                            } else if (columnIndexDisplayName != -1) {
                                // Nếu cột DATA không tồn tại, sử dụng cột DISPLAY_NAME
                                val fileName = cursor.getString(columnIndexDisplayName)
                                val file = File(
                                    Environment.getExternalStorageDirectory(),
                                    "${Constant.ANDROID_MEDIA}/${context.packageName}/$fileName"
                                )
                                file.absolutePath
                            } else {
                                null
                            }
                        } else {
                            filePath = null
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        filePath = null
                    } finally {
                        cursor.close()
                    }
                } else {
                    filePath = null
                }

                // Nếu filePath vẫn là null, thử lấy đường dẫn từ Uri.getPath()
                if (filePath == null) {
                    filePath = uri.path
                }

                return filePath
            }
            return null
        }

        /**
         * Get the value of the data column for this Uri. This is useful for
         * MediaStore Uris, and other file-based ContentProviders.
         *
         * @param context       The context.
         * @param uri           The Uri to query.
         * @param selection     (Optional) Filter used in the query.
         * @param selectionArgs (Optional) Selection arguments used in the query.
         * @return The value of the _data column, which is typically a file path.
         * @author Niks
         */
        private fun getDataColumn(
            context: Context, uri: Uri?, selection: String?,
            selectionArgs: Array<String>?,
        ): String? {

            var cursor: Cursor? = null
            val column = "_data"
            val projection = arrayOf(column)

            try {
                cursor =
                    context.contentResolver.query(uri!!, projection, selection, selectionArgs, null)
                if (cursor != null && cursor.moveToFirst()) {
                    val index = cursor.getColumnIndexOrThrow(column)
                    return cursor.getString(index)
                }
            } finally {
                cursor?.close()
            }
            return null
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is ExternalStorageProvider.
         */
        private fun isExternalStorageDocument(uri: Uri): Boolean {
            return "com.android.externalstorage.documents" == uri.authority
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is DownloadsProvider.
         */
        private fun isDownloadsDocument(uri: Uri): Boolean {
            return "com.android.providers.downloads.documents" == uri.authority
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is MediaProvider.
         */
        private fun isMediaDocument(uri: Uri): Boolean {
            return "com.android.providers.media.documents" == uri.authority
        }

        private fun isFileProviders(uri: Uri): Boolean {
            return "${BuildConfig.APPLICATION_ID}.fileprovider" == uri.authority
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is Google Photos.
         */
        private fun isGooglePhotosUri(uri: Uri): Boolean {
            return "com.google.android.apps.photos.content" == uri.authority
        }
    }
}