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

    override fun saveOrderId(orderId: String) {
        getSharePreferences().edit().putString(Constant.KEY_ORDER_ID, orderId).apply()
    }

    override fun getOrderId(): String? {
        return getSharePreferences().getString(Constant.KEY_ORDER_ID, Constant.EMPTY_STRING)
    }

    override fun saveOrderClientID(orderClientID: String) {
        getSharePreferences().edit().putString(Constant.KEY_ORDER_CLIENT_CODE, orderClientID).apply()
    }

    override fun getOrderClientID(): String? {
        return getSharePreferences().getString(Constant.KEY_ORDER_CLIENT_CODE, Constant.EMPTY_STRING)
    }

    override fun saveOrderID(orderID: String) {
        getSharePreferences().edit().putString("ABCC", orderID).apply()
    }

    override fun getOrderID(): String? {
        return getSharePreferences().getString("ABCC", Constant.EMPTY_STRING)
    }

    override fun setTokenFirebase(token: String) {
        getSharePreferences().edit().putString(Constant.KEY_TOKEN_FIREBASE, token).apply()
    }

    override fun getTokenFirebase(): String? {
        return getSharePreferences().getString(Constant.KEY_TOKEN_FIREBASE, Constant.EMPTY_STRING)
    }


}