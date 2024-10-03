package vn.md18.fsquareapplication.data.model

import com.google.gson.annotations.SerializedName

data  class ErrorResponse (
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("message")
    val message: String
)