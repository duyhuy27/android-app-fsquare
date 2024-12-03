package vn.md18.fsquareapplication.data.network.model.response

import com.google.gson.annotations.SerializedName

data class GetStatisticalResponse(
    @SerializedName("_id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("thumbnail")
    val thumbnail: Thumbnail,
    @SerializedName("totalSales")
    val totalSales: Int,
    @SerializedName("totalRevenue")
    val totalRevenue: Double
)
