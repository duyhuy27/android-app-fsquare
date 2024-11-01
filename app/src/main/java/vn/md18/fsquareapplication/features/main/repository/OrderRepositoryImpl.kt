package vn.md18.fsquareapplication.features.main.repository

import io.reactivex.Flowable
import vn.md18.fsquareapplication.data.model.DataResponse
import vn.md18.fsquareapplication.data.network.model.response.GetOrderRespose
import vn.md18.fsquareapplication.data.network.model.response.PaginationResponse
import vn.md18.fsquareapplication.data.network.retrofit.ApplicationService
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private var applicationService: ApplicationService
) : OrderRepository {
    override fun getOrderList(): Flowable<DataResponse<List<GetOrderRespose>, PaginationResponse>> {
        return applicationService.getOrder()
    }

}
