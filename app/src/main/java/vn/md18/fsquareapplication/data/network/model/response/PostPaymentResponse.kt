package vn.md18.fsquareapplication.data.network.model.response

import com.google.gson.annotations.SerializedName

data class PostPaymentResponse (
    @SerializedName("orderID")
    val orderId: String,
    @SerializedName("redirectUrl")
    val redirectUrl: String,
    @SerializedName("paymentUrl")
    val paymentUrl: String
)