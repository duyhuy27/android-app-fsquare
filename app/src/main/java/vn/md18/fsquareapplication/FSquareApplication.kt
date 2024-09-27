package vn.md18.fsquareapplication

import android.app.Application
import android.content.Context
import androidx.annotation.Keep
import androidx.multidex.MultiDex
import dagger.hilt.android.HiltAndroidApp

@Keep
@HiltAndroidApp
class FSquareApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}