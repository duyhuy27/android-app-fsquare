package vn.md18.fsquareapplication.data.network.model.request

import com.google.gson.annotations.SerializedName

data class PostPaymentRequest(
    @SerializedName("clientOrderCode")
    val clientOrderCode: String,
    @SerializedName("totalAmount")
    val totalAmount: Double,
    @SerializedName("toPhone")
    val toPhone: String
)
