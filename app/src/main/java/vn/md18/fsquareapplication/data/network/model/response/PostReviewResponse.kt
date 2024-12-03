package vn.md18.fsquareapplication.data.network.model.response

import com.google.gson.annotations.SerializedName

data class PostReviewResponse(
    @SerializedName("shoes")
    val shoes: List<String>,
    @SerializedName("customer")
    val customer: String,
    @SerializedName("rating")
    val rating: Int,
    @SerializedName("content")
    val content: String
)
