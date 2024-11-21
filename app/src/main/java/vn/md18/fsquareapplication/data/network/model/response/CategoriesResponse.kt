package vn.md18.fsquareapplication.data.network.model.response

import com.google.gson.annotations.SerializedName

data class CategoriesResponse(
    @SerializedName("_id")
    var _id: String,
    @SerializedName("thumbnail")
    var thumbnail: String,
    @SerializedName("name")
    var name: String
)
