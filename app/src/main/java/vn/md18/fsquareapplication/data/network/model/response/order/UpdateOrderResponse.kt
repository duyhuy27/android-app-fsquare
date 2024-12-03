package vn.md18.fsquareapplication.data.network.model.response.order

import com.google.gson.annotations.SerializedName

data class UpdateOrderResponse(
    @SerializedName("_id") val id: String,
    @SerializedName("status") val status: String
)
