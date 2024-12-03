package vn.md18.fsquareapplication.data.network.model.response.location

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GetLocationCustomerResponse (
    @SerializedName("_id")
    val id: String,
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

) : Serializable