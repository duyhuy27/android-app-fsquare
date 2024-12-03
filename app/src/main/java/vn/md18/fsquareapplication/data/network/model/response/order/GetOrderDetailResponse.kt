package vn.md18.fsquareapplication.data.network.model.response.order

import com.google.gson.annotations.SerializedName
import vn.md18.fsquareapplication.data.network.model.response.Thumbnail

data class GetOrderDetailResponse(
    @SerializedName("id") val id: String,
    @SerializedName("clientOrderCode") val orderID: String,
    @SerializedName("shippingAddress") val shippingAddress: ShippingDetailAddress,
    @SerializedName("weight") val totalWeight: Double,
    @SerializedName("codAmount") val value: Int,
    @SerializedName("shippingFee") val shippingFee: Double,
    @SerializedName("transportMethod") val transportMethod: String,
    @SerializedName("isFreeShip") val isFreeShip: Boolean,
    @SerializedName("status") val status: String,
    @SerializedName("statusTimestamps") val statusTimestamps: StatusTimestamps,
    @SerializedName("orderItems") val products: List<ProductDetail>
)

data class ShippingDetailAddress(
    @SerializedName("toName") val name: String,
    @SerializedName("toAddress") val address: String,
    @SerializedName("toProvinceName") val province: String,
    @SerializedName("toDistrictName") val district: String,
    @SerializedName("toWardName") val ward: String,
    @SerializedName("toPhone") val tel: String
)

data class StatusTimestamps(
    @SerializedName("pending") val pending: String?,
    @SerializedName("processing") val processing: String?,
    @SerializedName("shipped") val shipped: String?,
    @SerializedName("delivered") val delivered: String?,
    @SerializedName("confirmed") val confirmed: String?,
    @SerializedName("cancelled") val cancelled: String?,
    @SerializedName("returned") val returned: String?
)

data class ProductDetail(
    @SerializedName("size") val size: Int,
    @SerializedName("shoes") val shoes: String,
    @SerializedName("color") val color: String,
    @SerializedName("price") val price: Double,
    @SerializedName("quantity") val quantity: Int,
    @SerializedName("thumbnail") val thumbnail: Thumbnail?
)
