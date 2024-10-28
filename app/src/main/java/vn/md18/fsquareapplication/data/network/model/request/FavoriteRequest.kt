package vn.md18.fsquareapplication.data.network.model.request

import com.google.gson.annotations.SerializedName

data class FavoriteRequest (
    @SerializedName("shoes")
    val shoes: String

)