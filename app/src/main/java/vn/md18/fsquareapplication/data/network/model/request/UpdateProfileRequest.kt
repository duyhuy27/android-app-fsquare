package vn.md18.fsquareapplication.data.network.model.request

import com.google.gson.annotations.SerializedName

data class UpdateProfileRequest(

    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("lastName")
    val lastName: String,
    @SerializedName("birthDay")
    val birthDay: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("fcmToken")
    val fcmToken: String,
)
