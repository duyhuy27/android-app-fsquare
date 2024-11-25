package vn.md18.fsquareapplication.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.reactivex.disposables.CompositeDisposable
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.internal.platform.android.AndroidLogHandler.setLevel
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import vn.md18.fsquareapplication.BuildConfig
import vn.md18.fsquareapplication.data.network.retrofit.ApplicationService
import vn.md18.fsquareapplication.di.component.sharepref.AppPreference
import vn.md18.fsquareapplication.utils.Constant
import vn.md18.fsquareapplication.utils.extensions.NetworkExtensions
import vn.md18.fsquareapplication.utils.extensions.NetworkInterceptor
import vn.md18.fsquareapplication.utils.extensions.OHNetworkMonitor
import vn.md18.fsquareapplication.utils.fslogger.FSInterceptor
import vn.md18.fsquareapplication.utils.fslogger.FSLogger
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Provides
    @Singleton
    fun providerHttpCacheSize(@ApplicationContext context: Context): Cache {
        val cacheSize = 10 * 1024 * 1024 //10MB
        return Cache(context.cacheDir, cacheSize.toLong())
    }

    @Provides
    fun providerAuthInterceptor(
        appPreference: AppPreference
    ): Interceptor {
        return Interceptor { chain ->
            val requestBuilder = chain.request().newBuilder()
            val token = appPreference.getString(Constant.KEY_TOKEN)
            if (token.isNotEmpty()) {
                requestBuilder.addHeader("Authorization", "Bearer $token")
            }
            requestBuilder.addHeader("Content-Type", "application/json")
            try {
                return@Interceptor chain.proceed(requestBuilder.build())
            } catch (exception: Exception) {
                throw exception
            }
        }
    }

    @Provides
    @Singleton
    fun providerOkHttpClient(httpCache: Cache, interceptor: Interceptor, ohNetworkMonitor: OHNetworkMonitor): OkHttpClient {
        val client = OkHttpClient.Builder()
        client.cache(httpCache)

        val logging = HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG) {
                setLevel(HttpLoggingInterceptor.Level.HEADERS)
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }
        }

        val appLogger = FSInterceptor().apply {
//            if (BuildConfig.DEBUG) {
            setLevel(FSInterceptor.Level.HEADERS)
            setLevel(FSInterceptor.Level.BODY)
//            }
        }
        client.apply {
            connectTimeout(BuildConfig.LOADER_EXPIRED_TIME.toLong(), TimeUnit.SECONDS)
            readTimeout(BuildConfig.LOADER_EXPIRED_TIME.toLong(), TimeUnit.SECONDS)
            writeTimeout(BuildConfig.LOADER_EXPIRED_TIME.toLong(), TimeUnit.SECONDS)
            addInterceptor(interceptor)
            addInterceptor(NetworkInterceptor(ohNetworkMonitor))
            addNetworkInterceptor(interceptor)
//            addInterceptor(logging)
            addInterceptor(appLogger)
        }

        return client.build()
    }
    @Provides
    @Singleton
    fun providerRetrofit(okHttpClient: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder().baseUrl(BuildConfig.PHP_SERVER)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
    }
    @Provides
    fun providerManagerMallRetrofit(retrofit: Retrofit.Builder): ApplicationService {
        return retrofit.build().create(ApplicationService::class.java)
    }

    @Provides
    @Singleton
    fun provideCheckInternet(compositeDisposable: CompositeDisposable): NetworkExtensions {
        return NetworkExtensions(compositeDisposable)
    }

    @Provides
    @Singleton
    fun providerOHNetworkMonitor(
        @ApplicationContext context: Context
    ): OHNetworkMonitor {
        return OHNetworkMonitor(context)
    }
}