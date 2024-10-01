package vn.md18.fsquareapplication.data.network.model.request

import com.google.gson.annotations.SerializedName

data class VerifyRequest(
    @SerializedName("otp")
    var otp: String,

    @SerializedName("email")
    var email: String,

    @SerializedName("type")
    var type: String,

    @SerializedName("fcmToken")
    var fcmToken: String

)
