package vn.md18.fsquareapplication.data.network.model.response

import com.google.gson.annotations.SerializedName

data class PaginationResponse(
    @SerializedName("hasNextPage")
    val hasNextPage: Boolean,
    @SerializedName("hasPreviousPage")
    val hasPreviousPage: Boolean,
    @SerializedName("nextPage")
    val nextPage: Int,
    @SerializedName("page")
    val page: Int,
    @SerializedName("prevPage")
    val prevPage: Any,
    @SerializedName("size")
    val size: Int,
    @SerializedName("totalItems")
    val totalItems: Int,
    @SerializedName("totalPages")
    val totalPages: Int
)