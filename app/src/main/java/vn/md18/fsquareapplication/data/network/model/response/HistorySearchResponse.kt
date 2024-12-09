package vn.md18.fsquareapplication.data.network.model.response

import com.google.gson.annotations.SerializedName

data class HistorySearchResponse(
    @SerializedName("_id")
    val _id: String,
    @SerializedName("customer")
    val customer: String,
    @SerializedName("keyword")
    val keyword: String,
    @SerializedName("createdAt")
    val createdAt: String
)
