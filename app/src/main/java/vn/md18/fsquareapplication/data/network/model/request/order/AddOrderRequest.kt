package vn.md18.fsquareapplication.data.network.model.request.order

import com.google.gson.annotations.SerializedName

data class AddOrderRequest(
    @SerializedName("order") val order: Order,
    @SerializedName("orderItems") val orderItems: List<OrderItem>
)

data class Order(
    @SerializedName("shippingAddress") val shippingAddress: ShippingAddress,
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
    @SerializedName("price") val price: Int
)