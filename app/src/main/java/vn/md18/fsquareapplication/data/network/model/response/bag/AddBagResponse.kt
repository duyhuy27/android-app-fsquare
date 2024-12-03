package vn.md18.fsquareapplication.data.network.model.response.bag

import com.google.gson.annotations.SerializedName

data class AddBagResponse(
    @SerializedName("_id") val id: String,
    @SerializedName("customer") val customer: String,
    @SerializedName("size") val size: String,
    @SerializedName("quantity") val quantity: Int
)
