package vn.md18.fsquareapplication.data.network.model.response

import com.google.gson.annotations.SerializedName

data class GetClassificationResponse(
    @SerializedName("_id")
    val _id: String,
    @SerializedName("thumbnail")
    val thumbnail: Thumbnail,
    @SerializedName("color")
    val color: String,

)

data class Classification(
    @SerializedName("_id")
    val id: String,
    @SerializedName("images")
    val images: List<Thumbnail>,
    @SerializedName("videos")
    val videos: List<String>,
    @SerializedName("color")
    val color: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("price")
    val price: Double
)

data class ClassificationShoes(
    @SerializedName("_id")
    val id : String,
    @SerializedName("sizeNumber")
    val sizeNumber : String,
    @SerializedName("weight")
    val weight : Double,
    @SerializedName("quantity")
    val quantity : Int,
    @SerializedName("classification")
    val classification : String,
    @SerializedName("isActive")
    val isActive : Boolean,
)