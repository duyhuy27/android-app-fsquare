package vn.md18.fsquareapplication.data.network.model.response

import com.google.gson.annotations.SerializedName

data class GetPaymentResponse(
    @SerializedName("orderId")
    val orderId: String,
    @SerializedName("amount")
    val amount: Double,
    @SerializedName("status")
    val status: String,
    @SerializedName("paymentDate")
    val paymentDate: String
)
