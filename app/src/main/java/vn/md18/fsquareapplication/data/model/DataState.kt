package vn.md18.fsquareapplication.data.model

sealed class DataState<out R> {

    /**
     * Success State
     */
    data class Success<out T>(val data: T) : DataState<T>()

    /**
     * Error State
     */
    data class Error(val exception: Exception) : DataState<Nothing>()

    /**
     * Loading State
     */
    object Loading : DataState<Nothing>()


}