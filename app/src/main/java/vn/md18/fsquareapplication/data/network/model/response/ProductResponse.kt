package vn.md18.fsquareapplication.data.network.model.response

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("_id")
    val _id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("thumbnail")
    val thumbnail: Thumbnail,
    @SerializedName("minPrice")
    val minPrice: Int,
    @SerializedName("maxPrice")
    val maxPrice: Int,
    @SerializedName("rating")
    val rating: Double,
    @SerializedName("reviewCount")
    val reviewCount: Int,
    @SerializedName("isFavorite")
    val isFavorite: Boolean,
)

data class Thumbnail(
    @SerializedName("url")
    val url : String
)