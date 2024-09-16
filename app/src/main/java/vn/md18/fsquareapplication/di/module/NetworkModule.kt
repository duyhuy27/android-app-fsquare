package vn.md18.fsquareapplication.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import vn.md18.fsquareapplication.BuildConfig
import vn.md18.fsquareapplication.data.network.retrofit.ApplicationService
import vn.md18.fsquareapplication.di.component.sharepref.AppPreference
import vn.md18.fsquareapplication.utils.Constant
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
    fun providerOkHttpClient(httpCache: Cache, interceptor: Interceptor): OkHttpClient {
        val client = OkHttpClient.Builder()
        client.cache(httpCache)
        val logging = HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.HEADERS
                HttpLoggingInterceptor.Level.BODY
            }
        }
        client.apply {
            connectTimeout(BuildConfig.LOADER_EXPIRED_TIME.toLong(), TimeUnit.SECONDS)
            readTimeout(BuildConfig.LOADER_EXPIRED_TIME.toLong(), TimeUnit.SECONDS)
            writeTimeout(BuildConfig.LOADER_EXPIRED_TIME.toLong(), TimeUnit.SECONDS)
            addInterceptor(interceptor)
            addInterceptor(logging)
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
}