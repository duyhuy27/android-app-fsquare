package vn.md18.fsquareapplication

import android.app.Application
import android.content.Context
import androidx.annotation.Keep
import androidx.lifecycle.LifecycleObserver
import androidx.multidex.MultiDex
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.internal.Contexts.getApplication
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
        AppCenter.start(
            getApplication(this), "\"64d8a3c2-566a-4395-a507-10c417b165d5\"",
            Analytics::class.java, Crashes::class.java
        )
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