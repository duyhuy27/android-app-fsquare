package vn.md18.fsquareapplication.data.network.model.response.location

import com.google.gson.annotations.SerializedName

data class UpdateLocationCustomerResponse(
    @SerializedName("title")
    val title: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("wardName")
    val wardName: String,
    @SerializedName("districtName")
    val districtName: String,
    @SerializedName("provinceName")
    val provinceName: String,
    @SerializedName("isDefault")
    val isDefault: Boolean
)
