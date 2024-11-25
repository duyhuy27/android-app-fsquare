package vn.md18.fsquareapplication.features.detail.repository

import io.reactivex.Flowable
import vn.md18.fsquareapplication.data.model.DataResponse
import vn.md18.fsquareapplication.data.network.model.response.PaginationResponse
import vn.md18.fsquareapplication.data.network.model.response.ProductResponse

interface DetailProductRepository {
    fun getProductDetail(id: String): Flowable<DataResponse<ProductResponse, PaginationResponse>>
}