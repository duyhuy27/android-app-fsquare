package vn.md18.fsquareapplication.features.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.core.base.BaseViewModel
import vn.md18.fsquareapplication.data.model.DataState
import vn.md18.fsquareapplication.data.network.model.request.AddBagRequest
import vn.md18.fsquareapplication.data.network.model.request.UpdateQuantityBagRequest
import vn.md18.fsquareapplication.data.network.model.request.location.UpdateLocationCustomerRequest
import vn.md18.fsquareapplication.data.network.model.request.location.UpdateOrderRequest
import vn.md18.fsquareapplication.data.network.model.response.GetOrderRespose
import vn.md18.fsquareapplication.data.network.model.response.order.AddOrderResponse
import vn.md18.fsquareapplication.data.network.model.response.order.DeleteOrderResponse
import vn.md18.fsquareapplication.data.network.model.response.order.UpdateOrderResponse
import vn.md18.fsquareapplication.features.main.repository.OrderRepository
import vn.md18.fsquareapplication.utils.extensions.NetworkExtensions
import vn.md18.fsquareapplication.utils.fslogger.FSLogger
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val orderRepository: OrderRepository,
    private val networkExtensions: NetworkExtensions,

    ) : BaseViewModel() {
    private val _listOrder = MutableLiveData<List<GetOrderRespose>>()
    val listOrder: LiveData<List<GetOrderRespose>> = _listOrder

    private val _updateOrderState: MutableLiveData<DataState<UpdateOrderResponse>> = MutableLiveData()
    val updateOrderState: LiveData<DataState<UpdateOrderResponse>> get() = _updateOrderState

    private val _addOrderState: MutableLiveData<DataState<AddOrderResponse>> = MutableLiveData()
    val addOrderState: LiveData<DataState<AddOrderResponse>> get() = _addOrderState

    private val _deleteOrderState: MutableLiveData<DataState<DeleteOrderResponse>> = MutableLiveData()
    val deleteOrderState: LiveData<DataState<DeleteOrderResponse>> get() = _deleteOrderState

    fun getOrderList() {
        networkExtensions.checkInternet { isConnect ->
            if (isConnect) {
                compositeDisposable.add(
                    orderRepository.getOrderList()
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .toObservable()
                        .subscribe({ response ->
                            response.data.let {
                                _listOrder.value = it
                                setErrorString(response.message.toString())
                            }
                        },
                            { throwable ->
                                setErrorString(throwable.message.toString())
                            })
                )
            }
            else {
                setErrorStringId(R.string.error_have_no_internet)
            }
        }
    }

    fun deleteOrder(id: String) {
        networkExtensions.checkInternet { isConnect ->
            if (isConnect) {
                compositeDisposable.add(
                    orderRepository.deleteOrder(id)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .toObservable()
                        .subscribe({ response ->
                            _deleteOrderState.postValue(DataState.Success(response))
                        }, { err ->
                            _deleteOrderState.postValue(DataState.Error(err))
                        })
                )
            } else {
                setErrorStringId(R.string.no_internet_connection)
            }
        }
    }

    fun updateOrder(id: String, status: String) {
        val updateOrderRequest = UpdateOrderRequest( status)
        networkExtensions.checkInternet { isConnect ->
            if (isConnect) {
                setLoading(true)
                compositeDisposable.add(
                    orderRepository.updateOrderStatus(id, updateOrderRequest)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .toObservable()
                        .subscribe({ response ->
                            FSLogger.d("update address")
                            setLoading(false)
                            _updateOrderState.value = DataState.Success(response)
                        }, { err ->
                            setLoading(false)
                            _updateOrderState.value = DataState.Error(err)
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