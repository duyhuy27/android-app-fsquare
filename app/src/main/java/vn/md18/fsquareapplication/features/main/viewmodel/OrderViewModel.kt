package vn.md18.fsquareapplication.features.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.core.base.BaseViewModel
import vn.md18.fsquareapplication.data.model.DataState
import vn.md18.fsquareapplication.data.network.model.request.location.UpdateOrderRequest
import vn.md18.fsquareapplication.data.network.model.response.GetOrderRespose
import vn.md18.fsquareapplication.data.network.model.response.order.AddOrderResponse
import vn.md18.fsquareapplication.data.network.model.response.order.DeleteOrderResponse
import vn.md18.fsquareapplication.data.network.model.response.order.UpdateOrderResponse
import vn.md18.fsquareapplication.features.main.repository.OrderRepository
import vn.md18.fsquareapplication.utils.OrderStatus
import vn.md18.fsquareapplication.utils.extensions.NetworkExtensions
import vn.md18.fsquareapplication.utils.fslogger.FSLogger
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val orderRepository: OrderRepository,
    private val networkExtensions: NetworkExtensions
) : BaseViewModel() {

    private val _listOrder = MutableLiveData<List<GetOrderRespose>>()
    val listOrder: LiveData<List<GetOrderRespose>> = _listOrder

    private val _updateOrderState: MutableLiveData<DataState<UpdateOrderResponse>> = MutableLiveData()
    val updateOrderState: LiveData<DataState<UpdateOrderResponse>> get() = _updateOrderState

    private val _addOrderState: MutableLiveData<DataState<AddOrderResponse>> = MutableLiveData()
    val addOrderState: LiveData<DataState<AddOrderResponse>> get() = _addOrderState

    private val _deleteOrderState: MutableLiveData<DataState<DeleteOrderResponse>> = MutableLiveData()
    val deleteOrderState: LiveData<DataState<DeleteOrderResponse>> get() = _deleteOrderState

    var currentStatus: OrderStatus? = null

    fun getOrderList(status: OrderStatus? = null) {
        setLoading(true)
        networkExtensions.checkInternet { isConnect ->
            if (isConnect) {
                compositeDisposable.add(
                    orderRepository.getOrderList(status?.status.toString())
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .toObservable()
                        .subscribe({ response ->
                            response.data?.let {
                                if (status != null) {
                                    setLoading(false)
                                    _listOrder.value = it
                                } else {
                                    setLoading(false)
                                    _listOrder.value = it

                                }
                            }
                        }, { throwable ->
                            setLoading(false)
                            setErrorString(throwable.message.toString())
                        })
                )
            } else {
                setErrorStringId(R.string.error_have_no_internet)
            }
        }
    }


    fun updateOrder(id: String, status: OrderStatus) {
        val updateOrderRequest = UpdateOrderRequest(status.status)
        setLoading(true)
        networkExtensions.checkInternet { isConnect ->
            if (isConnect) {
                compositeDisposable.add(
                    orderRepository.updateOrderStatus(id, updateOrderRequest)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .toObservable()
                        .subscribe({ response ->
                            setLoading(false)
                            _updateOrderState.value = DataState.Success(response)
                        }, { err ->
                            setLoading(false)
                            FSLogger.e("loi order: $err")
                            _updateOrderState.value = DataState.Error(err)
                        })
                )
            } else {
                setErrorStringId(R.string.no_internet_connection)
            }
        }
    }

    fun deleteOrder(id: String) {
        setLoading(true)
        networkExtensions.checkInternet { isConnect ->
            if (isConnect) {
                compositeDisposable.add(
                    orderRepository.deleteOrder(id)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .toObservable()
                        .subscribe({ response ->
                            setLoading(false)
                            _deleteOrderState.value = DataState.Success(response)
                        }, { err ->
                            setLoading(false)
                            _deleteOrderState.value = DataState.Error(err)
                        })
                )
            } else {
                setErrorStringId(R.string.no_internet_connection)
            }
        }
    }

    override fun onDidBindViewModel() {
    }
}
