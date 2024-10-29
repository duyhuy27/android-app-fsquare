package vn.md18.fsquareapplication.data.network.model.response

import com.google.gson.annotations.SerializedName

data class GetWardsRepose(
    @SerializedName("wardCode")
    val wardCode: String,
    @SerializedName("wardName")
    val wardName: String
)
