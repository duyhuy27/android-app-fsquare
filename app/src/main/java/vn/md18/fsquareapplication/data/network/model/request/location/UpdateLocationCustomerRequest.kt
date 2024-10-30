package vn.md18.fsquareapplication.data.network.model.request.location

import com.google.gson.annotations.SerializedName

data class UpdateLocationCustomerRequest(
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
