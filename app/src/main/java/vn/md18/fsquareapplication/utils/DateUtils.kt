package vn.md18.fsquareapplication.utils

import java.text.SimpleDateFormat

class DateUtils {
    companion object {
        fun getCurrentDate(currentTime: Long): String? {
            return SimpleDateFormat("dd/MM/yyyy").format(currentTime)
        }
    }
}