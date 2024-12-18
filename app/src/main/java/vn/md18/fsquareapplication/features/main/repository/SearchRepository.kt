package vn.md18.fsquareapplication.features.main.repository

import io.reactivex.Flowable
import vn.md18.fsquareapplication.data.model.DataResponse
import vn.md18.fsquareapplication.data.network.model.response.HistorySearchResponse
import vn.md18.fsquareapplication.data.network.model.response.PaginationResponse
import vn.md18.fsquareapplication.data.network.model.response.ProductResponse

interface SearchRepository {
    fun searchProductByKeyword(keyword: String?,  size: Int? = null,
                               page: Int? = null, ) : Flowable<DataResponse<List<ProductResponse>, PaginationResponse>>

    fun searchProductV1(
        size: Int? = null,
        page: Int? = null,
        search: String? = null,
        brand: String? = null,
        category: String? = null
    ): Flowable<DataResponse<List<ProductResponse>, PaginationResponse>>

    fun saveKeyWordSearch(keyword: String) : Flowable<Boolean>

    fun getKeyWordSearch() : Flowable<DataResponse<List<HistorySearchResponse>, PaginationResponse>>
//
//    fun deleteKeyWordSearch() : Flowable<Boolean>
}