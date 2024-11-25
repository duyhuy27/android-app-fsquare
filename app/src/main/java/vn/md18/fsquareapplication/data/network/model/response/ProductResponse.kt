package vn.md18.fsquareapplication.data.network.model.response

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("_id")
    val _id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("brand")
    val brand: String,
    @SerializedName("category")
    val category: String,
    @SerializedName("describe")
    val describe: String,
    @SerializedName("description")
    val description: String,
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
    @SerializedName("sales")
    val sales: Int,
    @SerializedName("isFavorite")
    var isFavorite: Boolean,
)

data class Thumbnail(
    @SerializedName("url")
    val url : String
)