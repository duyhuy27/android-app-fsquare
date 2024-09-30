package vn.md18.fsquareapplication.utils.fslogger

import com.elvishew.xlog.printer.file.naming.FileNameGenerator
import vn.md18.fsquareapplication.utils.Constant.TXT_EXTENTION
import vn.md18.fsquareapplication.utils.FileUtil

class FSFileNameGenerator : FileNameGenerator {
    /**
     * Whether the generated file name will change or not.
     *
     * @return true if the file name is changeable
     */
    override fun isFileNameChangeable(): Boolean {
        return true
    }

    /**
     * Generate file name for specified log level and timestamp.
     *
     * @param logLevel  the level of the log
     * @param timestamp the timestamp when the logging happen
     * @return the generated file name
     */
    override fun generateFileName(logLevel: Int, timestamp: Long): String {
        return FileUtil.formatFileLogOneHome(timestamp = timestamp, suffix = TXT_EXTENTION)
    }
}