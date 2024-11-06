package vn.md18.fsquareapplication.data.network.model.response.auth

import com.google.gson.annotations.SerializedName

data class SignUpResponse(
    @SerializedName("status")
    var status: String,

    @SerializedName("message")
    var message: String,
)
