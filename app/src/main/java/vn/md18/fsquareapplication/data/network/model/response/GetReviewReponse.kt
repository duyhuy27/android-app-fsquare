package vn.md18.fsquareapplication.data.network.model.response

import com.google.gson.annotations.SerializedName

data class ReviewResponse(
    @SerializedName("_id")
    val _id: String,
    @SerializedName("customer")
    val customer: Customer,
    @SerializedName("rating")
    val rating: Int,
    @SerializedName("content")
    val content: String,
    @SerializedName("images")
    val images: List<String>,
    @SerializedName("videos")
    val videos: List<String>,
    @SerializedName("feedback")
    val feedback: String,
    @SerializedName("createdAt")
    val createdAt: String
)

data class Customer(
    @SerializedName("_id")
    val firstName: String,
    @SerializedName("_id")
    val lastName: String,
    @SerializedName("_id")
    val avatar: String
)

