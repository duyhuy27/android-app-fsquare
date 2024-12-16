package vn.md18.fsquareapplication.data.network.model.response

import com.google.gson.annotations.SerializedName
import vn.md18.fsquareapplication.data.network.model.response.profile.GetProfileResponse

data class GetReviewResponse(
    @SerializedName("_id")
    val id: String,
    @SerializedName("customer")
    val customer: GetProfileResponse,
    @SerializedName("rating")
    val rating: Double,
    @SerializedName("content")
    val content: String,
    @SerializedName("images")
    val images: List<Thumbnail?>,
    @SerializedName("feedback")
    val feedback: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("videos")
    val videos: List<Thumbnail?>,

)
