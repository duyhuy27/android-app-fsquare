package vn.md18.fsquareapplication.data.network.model.response.order

import com.google.gson.annotations.SerializedName

data class DeleteOrderResponse(
    @SerializedName("status")
    var status: String,

    @SerializedName("message")
    var message: String,
)
