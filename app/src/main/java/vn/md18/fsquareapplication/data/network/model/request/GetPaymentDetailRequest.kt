package vn.md18.fsquareapplication.data.network.model.request

import com.google.gson.annotations.SerializedName

data class GetPaymentDetailRequest(
    @SerializedName("orderId")
    val orderId: String,
    @SerializedName("clientOrderCode")
    val clientOrderCode: String
)
