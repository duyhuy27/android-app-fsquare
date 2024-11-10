package vn.md18.fsquareapplication.data.network.model.response

import com.google.gson.annotations.SerializedName

data class UpdateProfileResponse (
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("lastName")
    val lastName: String,
    @SerializedName("avatar")
    val avatar: String,
    @SerializedName("birthDay")
    val birthDay: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("longitude")
    val longitude: Double,
    @SerializedName("latitude")
    val latitude: Double,
)