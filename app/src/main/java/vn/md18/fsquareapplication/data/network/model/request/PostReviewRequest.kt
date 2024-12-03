package vn.md18.fsquareapplication.data.network.model.request

import com.google.gson.annotations.SerializedName
import java.io.File
import java.nio.file.Files

data class PostReviewRequest(
    @SerializedName("files")
    val files: List<File>?,
    @SerializedName("order")
    val order: String?,
    @SerializedName("rating")
    val rating: Int?,
    @SerializedName("content")
    val content: String?,
)
