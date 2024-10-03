package vn.md18.fsquareapplication.utils.extensions

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class NetworkExtensions @Inject constructor(private val compositeDisposable: CompositeDisposable) {
    fun checkInternet(function: (Boolean) -> Unit) {
        compositeDisposable.add(
            ReactiveNetwork.checkInternetConnectivity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        function.invoke(it)
                    }, {}
                )
        )
    }

    fun disposeCheckInternet() {
        compositeDisposable.clear()
    }
}

fun Context.checkInternetConnection(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val network = connectivityManager?.activeNetwork
        val networkCapabilities = connectivityManager?.getNetworkCapabilities(network)

        networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            ?: false
    } else {
        @Suppress("DEPRECATION")
        connectivityManager?.activeNetworkInfo?.isConnected == true
    }
}

data class ResultSource<out T>(val status: Status, val data: T?, val message: String?) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING,
        DEFAULT
    }

    companion object {
        fun <T> success(data: T): ResultSource<T> {
            return ResultSource(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String, data: T? = null): ResultSource<T> {
            return ResultSource(Status.ERROR, data, message)
        }

        fun <T> loading(data: T? = null): ResultSource<T> {
            return ResultSource(Status.LOADING, data, null)
        }

        fun <T> default(): ResultSource<T> {
            return ResultSource(Status.DEFAULT, null, null)
        }
    }
}

suspend fun <T> getResult(call: suspend () -> Response<T>): ResultSource<T> {
    try {
        val response = call()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) return ResultSource.success(body)
        }
        return error(" ${response.code()} ${response.message()}")
    } catch (e: Exception) {
        return error(e.message ?: e.toString())
    }
}

suspend fun <T> getResultDatabase(call: suspend () -> Flow<T>): Flow<ResultSource<T>> = withContext(Dispatchers.IO) {
    val result = call().map {
        val resultDB = it as? Collection<*>

        resultDB?.let { list ->
            if (list.isNotEmpty()) {
                ResultSource.success(it)
            } else {
                errorDB(" List is $it empty")
            }
        } ?: ResultSource.success(it)
    }
    result
}

private fun <T> error(message: String): ResultSource<T> {
    return ResultSource.error("Network call failed due to: $message")
}

private fun <T> errorDB(message: String): ResultSource<T> {
    return ResultSource.error("Database failed due to: $message")
}
