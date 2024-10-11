package vn.md18.fsquareapplication.data.model

data class DataResponse<T, P>(
    val status: String?,
    val message: String?,
    val data: T?,              // Data có thể null
    val pagination: P?
)
