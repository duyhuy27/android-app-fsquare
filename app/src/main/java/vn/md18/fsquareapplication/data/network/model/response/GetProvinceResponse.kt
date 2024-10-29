package vn.md18.fsquareapplication.data.network.model.response

import com.google.gson.annotations.SerializedName

data class GetProvinceResponse(
    @SerializedName("provinceID")
    val provinceID: String,
    @SerializedName("provinceName")
    val provinceName: String
)
