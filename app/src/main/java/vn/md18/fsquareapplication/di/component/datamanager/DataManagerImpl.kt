package vn.md18.fsquareapplication.di.component.datamanager

import android.content.SharedPreferences
import vn.md18.fsquareapplication.di.component.sharepref.AppPreference
import vn.md18.fsquareapplication.utils.Constant

import javax.inject.Inject

class DataManagerImpl @Inject constructor(private val mPreferences: AppPreference) : DataManager {

    override fun getSharePreferences(): SharedPreferences {
        return mPreferences.getSharePreferences()
    }

    override fun saveUidUser(uid: String) {
        getSharePreferences().edit().putString(Constant.KEY_UID, uid).apply()
    }

    override fun saveUserName(userName: String) {
        getSharePreferences().edit().putString(Constant.KEY_USER_NAME, userName).apply()
    }

    override fun getUserName(): String {
        return getSharePreferences().getString(Constant.KEY_USER_NAME, Constant.EMPTY_STRING)?: Constant.EMPTY_STRING
    }

    override fun getUidUser(): String {
        return getSharePreferences().getString(Constant.KEY_UID, Constant.EMPTY_STRING) ?: Constant.EMPTY_STRING
    }

    override fun saveIntro() {
        getSharePreferences().edit().putBoolean(Constant.KEY_INTRO, true).apply()
    }

    override fun getIntro(): Boolean {
        return getSharePreferences().getBoolean(Constant.KEY_INTRO, false)
    }

    override fun getToken(): String {
        return getSharePreferences().getString(Constant.KEY_TOKEN, Constant.EMPTY_STRING) ?: Constant.EMPTY_STRING
    }

    override fun setToken(token : String) {
        getSharePreferences().edit().putString(Constant.KEY_TOKEN,token).apply()
    }

}