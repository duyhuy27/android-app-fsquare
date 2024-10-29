package vn.md18.fsquareapplication.data.network.model.response

import com.google.gson.annotations.SerializedName

data class FavoriteResponse(
    @SerializedName("_id")
    val _id: String,
    @SerializedName("shoesId")
    val shoesId: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("thumbnail")
    val thumbnail: Thumbnail,
    @SerializedName("minPrice")
    val minPrice: Int,
    @SerializedName("maxPrice")
    val maxPrice: Int,
    @SerializedName("avgRating")
    val avgRating: Double,
    @SerializedName("reviewCount")
    val reviewCount: Int,
)