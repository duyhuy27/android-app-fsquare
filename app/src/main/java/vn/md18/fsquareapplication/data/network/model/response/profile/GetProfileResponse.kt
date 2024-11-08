package vn.md18.fsquareapplication.data.network.model.response.profile

import com.google.gson.annotations.SerializedName

data class GetProfileResponse(
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("lastName")
    val lastName: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("avatar")
    val avatar: String,
    @SerializedName("birthDay")
    val birthDay: String,
    @SerializedName("phone")
    val phone: String,
)
