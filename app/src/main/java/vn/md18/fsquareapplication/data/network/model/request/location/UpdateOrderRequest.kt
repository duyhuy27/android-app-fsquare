package vn.md18.fsquareapplication.data.network.model.request.location

import com.google.gson.annotations.SerializedName

data class UpdateOrderRequest(
    @SerializedName("newStatus") val newStatus: String
)
