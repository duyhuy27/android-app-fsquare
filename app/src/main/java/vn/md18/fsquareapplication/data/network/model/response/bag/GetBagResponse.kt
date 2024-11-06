package vn.md18.fsquareapplication.data.network.model.response.bag

import com.google.gson.annotations.SerializedName

data class GetBagResponse(
    @SerializedName("_id")
    val _id: String,
    @SerializedName("shoes")
    val shoes: String,
    @SerializedName("thumbnail")
    val thumbnail: String,
    @SerializedName("color")
    val color: String,
    @SerializedName("size")
    val size: String,
    @SerializedName("quantity")
    val quantity: Int,
    @SerializedName("price")
    val price: Double,
)
