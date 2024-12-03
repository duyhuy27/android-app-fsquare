package vn.md18.fsquareapplication.features.main.repository

import io.reactivex.Flowable
import vn.md18.fsquareapplication.data.model.DataResponse
import vn.md18.fsquareapplication.data.network.model.request.AddBagRequest
import vn.md18.fsquareapplication.data.network.model.request.PostReviewRequest
import vn.md18.fsquareapplication.data.network.model.request.UpdateQuantityBagRequest
import vn.md18.fsquareapplication.data.network.model.request.location.UpdateOrderRequest
import vn.md18.fsquareapplication.data.network.model.request.order.AddOrderRequest
import vn.md18.fsquareapplication.data.network.model.response.bag.AddBagResponse
import vn.md18.fsquareapplication.data.network.model.response.bag.DeleteBagResponse
import vn.md18.fsquareapplication.data.network.model.response.GetOrderRespose
import vn.md18.fsquareapplication.data.network.model.response.PaginationResponse
import vn.md18.fsquareapplication.data.network.model.response.PostReviewResponse
import vn.md18.fsquareapplication.data.network.model.response.bag.UpdateQuantityBagResponse
import vn.md18.fsquareapplication.data.network.model.response.order.AddOrderResponse
import vn.md18.fsquareapplication.data.network.model.response.order.DeleteOrderResponse
import vn.md18.fsquareapplication.data.network.model.response.order.GetOrderDetailResponse
import vn.md18.fsquareapplication.data.network.model.response.order.UpdateOrderResponse

interface OrderRepository {
    fun getOrderList(
        status: String? = null
    ) : Flowable<DataResponse<List<GetOrderRespose>, PaginationResponse>>

    fun getOrderDetail(id: String) : Flowable<DataResponse<GetOrderDetailResponse, PaginationResponse>>

    fun updateOrderStatus(id: String, updateOrderRequest: UpdateOrderRequest) : Flowable<UpdateOrderResponse>
    fun createOrder (addOrderRequest: AddOrderRequest) : Flowable<AddOrderResponse>

    fun deleteOrder( id: String ) : Flowable<DeleteOrderResponse>

    fun postReviews (postReviewRequest: PostReviewRequest) : Flowable<PostReviewResponse>
}