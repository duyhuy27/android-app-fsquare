package vn.md18.fsquareapplication.features.main.repository

import io.reactivex.Flowable
import io.reactivex.Single
import vn.md18.fsquareapplication.data.model.DataResponse
import vn.md18.fsquareapplication.data.network.model.response.PaginationResponse
import vn.md18.fsquareapplication.data.network.model.response.ProductResponse
import vn.md18.fsquareapplication.data.network.retrofit.ApplicationService
import vn.md18.fsquareapplication.di.component.datamanager.DataManager
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val applicationServices: ApplicationService,
    private val dataManager: DataManager,
) : MainRepository {

    override fun getProductList(
        size: Int?,
        page: Int?,
        search: String?,
        brand: String?,
        category: String?
    ): Flowable<DataResponse<List<ProductResponse>, PaginationResponse>> {
        return applicationServices.getProduct(size = size!!, page = page!!)
    }
}