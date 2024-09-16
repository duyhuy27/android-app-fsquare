package vn.md18.fsquareapplication.data.local.mapper

import androidx.room.TypeConverter
import com.google.gson.Gson

class Converters {
    @TypeConverter
    fun listToJsonString(value: List<Any>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonStringToList(value: String) = Gson().fromJson(value, Array<Any>::class.java).toList()
}