package vn.md18.fsquareapplication.utils

import com.google.gson.Gson
import retrofit2.HttpException
import timber.log.Timber
import vn.md18.fsquareapplication.data.model.ErrorResponse
import java.net.UnknownHostException

class ErrorUtils {
    companion object {
        fun getError(throwable: Throwable) : ErrorResponse? {
            try {
             val gson = Gson()
             return when(throwable) {
                 is UnknownHostException -> {
                     ErrorResponse(
                         status = null,
                         message = "No Internet"
                     )
                 }
                 is HttpException -> {
                     var errorResponse = gson.fromJson(
                         throwable.response()?.errorBody()!!.toString(),
                         ErrorResponse::class.java
                     )

                     if (errorResponse == null && throwable.code() == 401) {
                         errorResponse = ErrorResponse(
                             status = "401",
                             message = "No Perrmision To Asset"
                         )
                     }
                     errorResponse
                 }
                 else -> {
                     ErrorResponse(message = "No Define")
                 }
             }
            }
            catch (exception: Exception) {
                Timber.e("exception : ${exception.message.toString()}")
                return null
            }
        }
    }
}