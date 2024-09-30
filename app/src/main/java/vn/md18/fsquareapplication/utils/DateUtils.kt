package vn.md18.fsquareapplication.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class DateUtils {
    companion object {
        fun getCurrentDate(currentTime: Long): String? {
            return SimpleDateFormat("dd/MM/yyyy").format(currentTime)
        }

        fun getCurrentDateTime(currentTime: Long): String? {
            return SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(currentTime)
        }

        /**
         * @param currentTime
         * @return
         */
        fun getHour(currentTime: Long): Int {
            val calendar = Calendar.getInstance()
            calendar.time = Date(currentTime)
            return calendar[Calendar.HOUR_OF_DAY]
        }

        /**
         * @param currentTime
         * @return
         */
        fun getMinute(currentTime: Long): Int {
            val calendar = Calendar.getInstance()
            calendar.time = Date(currentTime)
            return calendar[Calendar.MINUTE]
        }

        fun getTimeStamp(formatTimeStamp: String): String {
            val sdf = SimpleDateFormat(formatTimeStamp, Locale.US)
            sdf.timeZone = TimeZone.getDefault()
            return sdf.format(Date(System.currentTimeMillis()))
        }
    }
}