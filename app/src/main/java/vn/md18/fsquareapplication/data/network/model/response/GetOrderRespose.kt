package vn.md18.fsquareapplication.data.network.model.response

import com.google.gson.annotations.SerializedName

data class GetOrderRespose(
    @SerializedName("id")
    val id: String,
    @SerializedName("value")
    val value: Int,
    @SerializedName("status")
    val status: String,
    @SerializedName("createAt")
    val createdAt: String,
    @SerializedName("firstProduct")
    val firstProduct: Product
)

data class Product(
    @SerializedName("name")
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
    val thumbnail: String?
)