package vn.md18.fsquareapplication.features.detail.repository

import io.reactivex.Flowable
import vn.md18.fsquareapplication.data.model.DataResponse
import vn.md18.fsquareapplication.data.network.model.response.Classification
import vn.md18.fsquareapplication.data.network.model.response.ClassificationShoes
import vn.md18.fsquareapplication.data.network.model.response.GetClassificationResponse
import vn.md18.fsquareapplication.data.network.model.response.GetReviewResponse
import vn.md18.fsquareapplication.data.network.model.response.PaginationResponse
import vn.md18.fsquareapplication.data.network.model.response.ProductResponse

interface DetailProductRepository {
    fun getProductDetail(id: String): Flowable<DataResponse<ProductResponse, PaginationResponse>>

    fun getProductDetailV1(id: String): Flowable<DataResponse<ProductResponse, PaginationResponse>>
    fun getColor(id: String) : Flowable<DataResponse<List<GetClassificationResponse>, PaginationResponse>>

    fun getClassification(id: String) : Flowable<DataResponse<Classification, PaginationResponse>>

    fun getSize(id: String) : Flowable<DataResponse<List<ClassificationShoes>, PaginationResponse>>

    fun getReviews(id: String) : Flowable<DataResponse<List<GetReviewResponse>, PaginationResponse>>
}