package vn.md18.fsquareapplication.features.main.repository

import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.Query
import vn.md18.fsquareapplication.data.model.DataResponse
import vn.md18.fsquareapplication.data.network.model.response.PaginationResponse
import vn.md18.fsquareapplication.data.network.model.response.ProductResponse

interface MainRepository {

    fun getProductList(
        size: Int? = null,
        page: Int? = null,
        search: String? = null,
        brand: String? = null,
        category: String? = null
    ): Flowable<DataResponse<List<ProductResponse>, PaginationResponse>>
}