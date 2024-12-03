package vn.md18.fsquareapplication.data.network.model.response

import com.google.gson.annotations.SerializedName

data class BrandResponse(
    @SerializedName("_id")
    val _id: String,
    @SerializedName("thumbnail")
    val thumbnail: Thumbnail?,
    @SerializedName("name")
    val name: String
)