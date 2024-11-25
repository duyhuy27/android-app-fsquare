package vn.md18.fsquareapplication.data.network.model.response.order

import com.google.gson.annotations.SerializedName

data class OrderFeeResponse(
    @SerializedName("status")
    var status: String,

    @SerializedName("message")
    var message: String,

    @SerializedName("data")
    var data: Double?
)
