package vn.md18.fsquareapplication.features.main.repository

import io.reactivex.Flowable
import vn.md18.fsquareapplication.data.model.DataResponse
import vn.md18.fsquareapplication.data.network.model.response.GetOrderRespose
import vn.md18.fsquareapplication.data.network.model.response.PaginationResponse

interface OrderRepository {
    fun getOrderList() : Flowable<DataResponse<List<GetOrderRespose>, PaginationResponse>>
}