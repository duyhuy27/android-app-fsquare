package vn.md18.fsquareapplication.features.detail.repository

import io.reactivex.Flowable
import vn.md18.fsquareapplication.data.model.DataResponse
import vn.md18.fsquareapplication.data.network.model.response.PaginationResponse
import vn.md18.fsquareapplication.data.network.model.response.ProductResponse
import vn.md18.fsquareapplication.data.network.retrofit.ApplicationService
import javax.inject.Inject

class DetailProductRepositoryImpl @Inject constructor(private val applicationService: ApplicationService) :
    DetailProductRepository {
    override fun getProductDetail(id: String): Flowable<DataResponse<ProductResponse, PaginationResponse>> {
        return applicationService.getProductDetail(id)
    }
}