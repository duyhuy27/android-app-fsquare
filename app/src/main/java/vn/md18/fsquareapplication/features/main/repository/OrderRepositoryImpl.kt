package vn.md18.fsquareapplication.features.main.repository

import io.reactivex.Flowable
import vn.md18.fsquareapplication.data.model.DataResponse
import vn.md18.fsquareapplication.data.network.model.request.CheckPaymentRequest
import vn.md18.fsquareapplication.data.network.model.request.PostReviewRequest
import vn.md18.fsquareapplication.data.network.model.request.location.UpdateOrderRequest
import vn.md18.fsquareapplication.data.network.model.request.order.AddOrderRequest
import vn.md18.fsquareapplication.data.network.model.response.GetOrderRespose
import vn.md18.fsquareapplication.data.network.model.response.PaginationResponse
import vn.md18.fsquareapplication.data.network.model.response.PostReviewResponse
import vn.md18.fsquareapplication.data.network.model.response.order.AddOrderResponse
import vn.md18.fsquareapplication.data.network.model.response.order.DeleteOrderResponse
import vn.md18.fsquareapplication.data.network.model.response.order.GetOrderDetailResponse
import vn.md18.fsquareapplication.data.network.model.response.order.UpdateOrderResponse
import vn.md18.fsquareapplication.data.network.retrofit.ApplicationService
import vn.md18.fsquareapplication.utils.fslogger.FSLogger
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private var applicationService: ApplicationService
) : OrderRepository {
    override fun getOrderList(status: String?): Flowable<DataResponse<List<GetOrderRespose>, PaginationResponse>> {
        return applicationService.getOrder(status = status!!)
    }

    override fun getOrderDetail(id: String): Flowable<DataResponse<GetOrderDetailResponse, PaginationResponse>> {
        return applicationService.getOrderById(id)
    }

    override fun updateOrderStatus(
        id: String,
        updateOrderRequest: UpdateOrderRequest
    ): Flowable<UpdateOrderResponse> {
        return applicationService.updateOrderStatus(id, updateOrderRequest)
    }

    override fun createOrder(addOrderRequest: AddOrderRequest): Flowable<AddOrderResponse> {
        return applicationService.createOrder(addOrderRequest)
    }

    override fun deleteOrder(id: String): Flowable<DeleteOrderResponse> {
        return applicationService.deleteOrder(id)
    }

    override fun postReviews(postReviewRequest: PostReviewRequest): Flowable<PostReviewResponse> {
        return applicationService.postReview(postReviewRequest)
    }

    override fun getPaymentStatus(checkPaymentRequest: CheckPaymentRequest): Flowable<Boolean> {
        return applicationService.checkPaymentOrder(checkPaymentRequest).map {
            FSLogger.e("Huynd: Check payment status: ${it.status}")
            it.status == "success"
        }.onErrorReturn {
            false
        }
    }

}
