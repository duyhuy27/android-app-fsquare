package vn.md18.fsquareapplication.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Flowable
import io.reactivex.Single
import vn.md18.fsquareapplication.data.local.model.UserLocal

@Dao
interface UserProfileDao {

    @Query("SELECT * FROM USER_TABLE")
    fun getUser(): Flowable<UserLocal>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveUser(userProfileLocalEntity: UserLocal) : Single<Long>
}