package vn.md18.fsquareapplication.data.network.model.response

import com.google.gson.annotations.SerializedName

data class DeleteFavoriteRespone(
    @SerializedName("status")
    var status: String,

    @SerializedName("message")
    var message: String,
)
