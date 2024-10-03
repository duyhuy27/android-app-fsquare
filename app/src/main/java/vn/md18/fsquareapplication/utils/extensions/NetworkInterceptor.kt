package vn.md18.fsquareapplication.utils.extensions

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class NetworkInterceptor @Inject constructor(private val networkExtensions: OHNetworkMonitor) : Interceptor {
    @Throws(NoNetworkException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()

        if (networkExtensions.isConnected()) {
            return chain.proceed(request)
        } else {
            throw NoNetworkException("Network Error")
        }
    }

}

class NoNetworkException(message:String): IOException(message)