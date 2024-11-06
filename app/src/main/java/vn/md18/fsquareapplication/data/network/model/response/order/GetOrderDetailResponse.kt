package vn.md18.fsquareapplication.data.network.model.response.order

import com.google.gson.annotations.SerializedName

data class GetOrderDetailResponse(
    @SerializedName("id") val id: String,
    @SerializedName("orderID") val orderID: String,
    @SerializedName("shippingAddress") val shippingAddress: ShippingDetailAddress,
    @SerializedName("totalWeight") val totalWeight: Double,
    @SerializedName("value") val value: Int,
    @SerializedName("shippingFee") val shippingFee: Int,
    @SerializedName("transportMethod") val transportMethod: String,
    @SerializedName("isFreeShip") val isFreeShip: Boolean,
    @SerializedName("status") val status: String,
    @SerializedName("statusTimestamps") val statusTimestamps: StatusTimestamps,
    @SerializedName("products") val products: List<ProductDetail>
)

data class ShippingDetailAddress(
    @SerializedName("name") val name: String,
    @SerializedName("address") val address: String,
    @SerializedName("province") val province: String,
    @SerializedName("district") val district: String,
    @SerializedName("ward") val ward: String,
    @SerializedName("tel") val tel: String
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
    @SerializedName("price") val price: Int,
    @SerializedName("quantity") val quantity: Int,
    @SerializedName("thumbnail") val thumbnail: String
)
