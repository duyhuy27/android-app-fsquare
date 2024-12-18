package vn.md18.fsquareapplication.features.main.repository

import androidx.room.util.query
import io.reactivex.Flowable
import vn.md18.fsquareapplication.data.model.DataResponse
import vn.md18.fsquareapplication.data.network.model.response.HistorySearchResponse
import vn.md18.fsquareapplication.data.network.model.response.PaginationResponse
import vn.md18.fsquareapplication.data.network.model.response.ProductResponse
import vn.md18.fsquareapplication.data.network.retrofit.ApplicationService
import vn.md18.fsquareapplication.di.component.datamanager.DataManager
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val applicationServices: ApplicationService,
) : SearchRepository {
    override fun searchProductByKeyword(
        keyword: String?, size: Int?,
        page: Int?
    ): Flowable<DataResponse<List<ProductResponse>, PaginationResponse>> {
        return applicationServices.getProduct(search = keyword, size = size ?: 1, page = page ?: 1)
    }

    override fun searchProductV1(
        size: Int?,
        page: Int?,
        search: String?,
        brand: String?,
        category: String?
    ): Flowable<DataResponse<List<ProductResponse>, PaginationResponse>> {
        return applicationServices.getProduct(size = size!!, page = page!!, search = search)
    }

    override fun saveKeyWordSearch(keyword: String): Flowable<Boolean> {
        return applicationServices.saveKeyWordSearch(keyword)
            .map { response ->
                response.status == "success"
            }
            .onErrorReturn {
                false
            }
    }


    override fun getKeyWordSearch(): Flowable<DataResponse<List<HistorySearchResponse>, PaginationResponse>> {
        return applicationServices.getHistorySearch()
    }
//
//    override fun deleteKeyWordSearch(): Flowable<Boolean> {
//        TODO("Not yet implemented")
//    }
}