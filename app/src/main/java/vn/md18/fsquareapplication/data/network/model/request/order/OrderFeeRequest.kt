package vn.md18.fsquareapplication.data.network.model.request.order

import com.google.gson.annotations.SerializedName

data class OrderFeeRequest(
    @SerializedName("clientOrderCode")
    val clientOrderCode: String,
    @SerializedName("toName")
    val toName: String,
    @SerializedName("toPhone")
    val toPhone: String,
    @SerializedName("toAddress")
    val toAddress: String,
    @SerializedName("toWardName")
    val toWardName: String,
    @SerializedName("toDistrictName")
    val toDistrictName: String,
    @SerializedName("toProvinceName")
    val toProvinceName: String,
    @SerializedName("codAmount")
    val codAmount: Double,
    @SerializedName("weight")
    val weight: Double,
    @SerializedName("content")
    val content: String,
)
