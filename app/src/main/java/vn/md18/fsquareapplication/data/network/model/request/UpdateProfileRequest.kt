package vn.md18.fsquareapplication.data.network.model.request

import com.google.gson.annotations.SerializedName

data class UpdateProfileRequest(
    @SerializedName("firstName")
    val firstName: String? = null,
    @SerializedName("lastName")
    val lastName: String? = null,
    @SerializedName("birthDay")
    val birthDay: String? = null,
    @SerializedName("phone")
    val phone: String? = null,
    @SerializedName("fcmToken")
    val fcmToken: String
)

