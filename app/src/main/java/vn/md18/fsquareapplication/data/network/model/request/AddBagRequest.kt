package vn.md18.fsquareapplication.data.network.model.request

import com.google.gson.annotations.SerializedName

data class AddBagRequest (
    @SerializedName("size") val size: String,
    @SerializedName("quantity") val quantity: Int
)