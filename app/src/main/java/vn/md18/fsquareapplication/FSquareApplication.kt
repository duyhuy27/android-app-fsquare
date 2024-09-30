package vn.md18.fsquareapplication

import android.app.Application
import android.content.Context
import androidx.annotation.Keep
import androidx.lifecycle.LifecycleObserver
import androidx.multidex.MultiDex
import dagger.hilt.android.HiltAndroidApp
import vn.md18.fsquareapplication.data.network.retrofit.ApplicationService
import vn.md18.fsquareapplication.utils.fslogger.FSLogger
import javax.inject.Inject

@Keep
@HiltAndroidApp
class FSquareApplication : Application(), LifecycleObserver {
    @Inject
    lateinit var apiService: ApplicationService
    init {
        instance = this
    }

    companion object {
        private var instance: FSquareApplication? = null

        @JvmStatic
        fun getInstance(): FSquareApplication {
            return instance as FSquareApplication
        }
    }
    override fun onCreate() {
        super.onCreate()
        initAppLogger()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
    private fun initAppLogger() {
        FSLogger.initOneHomeLogger(baseContext)
    }
}