package vn.md18.fsquareapplication.di.component.datamanager

import android.content.SharedPreferences

interface DataManager {
    fun getSharePreferences(): SharedPreferences

    fun saveUidUser(uid: String)

    fun saveUserName(userName: String)

    fun getUserName(): String?

    fun getUidUser(): String?

    fun saveIntro()

    fun getIntro(): Boolean

    fun getToken() : String?

    fun setToken(token : String)

}