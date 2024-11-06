package vn.md18.fsquareapplication.data.network.model.response.bag

import com.google.gson.annotations.SerializedName

data class UpdateQuantityBagResponse(
    @SerializedName("_id")
    val _id: String,
    @SerializedName("quantity")
    val quantity: Int,
    @SerializedName("price")
    val price: Double
)
