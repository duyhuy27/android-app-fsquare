package vn.md18.fsquareapplication.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import vn.md18.fsquareapplication.data.local.mapper.Converters
import vn.md18.fsquareapplication.data.local.model.UserLocal

@Database(entities = [UserLocal::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserProfileDao
}