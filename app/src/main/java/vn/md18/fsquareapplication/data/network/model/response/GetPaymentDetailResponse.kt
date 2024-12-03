package vn.md18.fsquareapplication.data.network.model.response

import com.google.gson.annotations.SerializedName

data class GetPaymentDetailResponse(
    @SerializedName("status")
    val status: String
)
