package vn.md18.fsquareapplication.data.network.model.response.order

import com.google.gson.annotations.SerializedName
import vn.md18.fsquareapplication.data.network.model.response.Thumbnail

data class AddOrderResponse(
    @SerializedName("customer") val customer: String,
    @SerializedName("clientOrderCode") val clientOrderCode: String,
    @SerializedName("shippingAddress") val shippingAddress: ShippingAddress,
    @SerializedName("orderItems") val orderItems: List<OrderItem>,
    @SerializedName("weight") val weight: Double,
    @SerializedName("codAmount") val codAmount: Int,
    @SerializedName("shippingFee") val shippingFee: Int,
    @SerializedName("content") val content: String,
    @SerializedName("isFreeShip") val isFreeShip: Boolean,
    @SerializedName("isPayment") val isPayment: Boolean,
    @SerializedName("note") val note: String
)

data class ShippingAddress(
    @SerializedName("toName") val toName: String,
    @SerializedName("toAddress") val toAddress: String,
    @SerializedName("toProvinceName") val toProvinceName: String,
    @SerializedName("toDistrictName") val toDistrictName: String,
    @SerializedName("toWardName") val toWardName: String,
    @SerializedName("toPhone") val toPhone: String
)

data class OrderItem(
    @SerializedName("size") val size: String,
    @SerializedName("shoes") val shoes: String,
    @SerializedName("quantity") val quantity: Int,
    @SerializedName("price") val price: Double,
    @SerializedName("color") val color: String,
    @SerializedName("thumbnail") val thumbnail: Thumbnail?
)
