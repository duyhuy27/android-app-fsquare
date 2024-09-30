package vn.md18.fsquareapplication.data.network.model.request

import com.google.gson.annotations.SerializedName

data class SignUpRequest(
    @SerializedName("email")
    var email: String
)
