package vn.md18.fsquareapplication.data.network.model.response

import com.google.gson.annotations.SerializedName

data class GetDistrictsResponse (
    @SerializedName("districtID")
    val districtID: String,
    @SerializedName("districtName")
    val districtName: String
)