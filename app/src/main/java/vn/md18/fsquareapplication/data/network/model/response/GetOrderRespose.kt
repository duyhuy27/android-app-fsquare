package vn.md18.fsquareapplication.data.network.model.response

import com.google.gson.annotations.SerializedName

data class GetOrderRespose(
    @SerializedName("_id")
    val id: String,
    @SerializedName("value")
    val value: Int,
    @SerializedName("status")
    val status: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("firstOrderItem")
    val firstProduct: Product,
    @SerializedName("isReview")
    val isReview: Boolean
)

data class Product(
    @SerializedName("shoes")
    val name: String,
    @SerializedName("size")
    val size: String,
    @SerializedName("color")
    val color: String,
    @SerializedName("price")
    val price: Double,
    @SerializedName("quantity")
    val quantity: Int,
    @SerializedName("thumbnail")
    val thumbnail: Thumbnail?
)