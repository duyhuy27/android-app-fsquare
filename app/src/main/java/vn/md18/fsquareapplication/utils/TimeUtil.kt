package vn.md18.fsquareapplication.utils

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object TimeUtil {
    fun long2String(time: Long?, format: String?): String {
        val simpleDateFormat = SimpleDateFormat(format)
        return try {
            val date = time?.let { Date(it) }
            simpleDateFormat.format(date)
        } catch (e: Exception) {
            Constant.EMPTY_STRING
        }
    }

    //change time 24h to 12h
    @SuppressLint("SimpleDateFormat")
    fun formatTime12Hour(formatTime: String?): String? {
        var formatTime = formatTime
        val tk = StringTokenizer(formatTime)
        val date = tk.nextToken()
        val time = tk.nextToken()
        val sdf = SimpleDateFormat("K:mm")
        val sdfs = SimpleDateFormat("K:mm aa")
        val dt: Date
        try {
            dt = sdf.parse(time)
            formatTime = date + " " + sdfs.format(dt)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return formatTime
    }
}