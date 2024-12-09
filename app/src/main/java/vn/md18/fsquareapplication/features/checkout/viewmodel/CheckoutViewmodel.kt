package vn.md18.fsquareapplication.features.checkout.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.core.base.BaseViewModel
import vn.md18.fsquareapplication.core.eventbus.CheckPaymentStatus
import vn.md18.fsquareapplication.data.model.DataState
import vn.md18.fsquareapplication.data.network.model.request.CheckPaymentRequest
import vn.md18.fsquareapplication.data.network.model.request.LoginRequest
import vn.md18.fsquareapplication.data.network.model.request.PostPaymentRequest
import vn.md18.fsquareapplication.data.network.model.request.order.AddOrderRequest
import vn.md18.fsquareapplication.data.network.model.request.order.Order
import vn.md18.fsquareapplication.data.network.model.request.order.OrderFeeRequest
import vn.md18.fsquareapplication.data.network.model.request.order.OrderItem
import vn.md18.fsquareapplication.data.network.model.request.order.ShippingAddress
import vn.md18.fsquareapplication.data.network.model.response.PostPaymentResponse
import vn.md18.fsquareapplication.data.network.model.response.auth.LoginResponse
import vn.md18.fsquareapplication.data.network.model.response.bag.DeleteBagResponse
import vn.md18.fsquareapplication.data.network.model.response.bag.GetBagResponse
import vn.md18.fsquareapplication.data.network.model.response.location.GetLocationCustomerResponse
import vn.md18.fsquareapplication.data.network.model.response.order.AddOrderResponse
import vn.md18.fsquareapplication.data.network.model.response.order.OrderFeeResponse
import vn.md18.fsquareapplication.data.network.model.response.profile.GetProfileResponse
import vn.md18.fsquareapplication.features.checkout.repository.hi.CheckoutRepository
import vn.md18.fsquareapplication.features.main.repository.MainRepository
import vn.md18.fsquareapplication.features.main.repository.OrderRepository
import vn.md18.fsquareapplication.features.profileandsetting.repositoriy.ProfileRepository
import vn.md18.fsquareapplication.features.profileandsetting.repositoriy.editprofile.EditProfileRepository
import vn.md18.fsquareapplication.features.profileandsetting.repositoriy.location.LocationCustomerRepository
import vn.md18.fsquareapplication.utils.Constant
import vn.md18.fsquareapplication.utils.extensions.NetworkExtensions
import vn.md18.fsquareapplication.utils.fslogger.FSLogger
import javax.inject.Inject

@HiltViewModel
class CheckoutViewmodel @Inject constructor(
    private val mainRepository: MainRepository,
    private val orderRepository: OrderRepository,
    private val profileRepository: EditProfileRepository,
    private val locationCustomerRepository: LocationCustomerRepository,
    private val networkExtension: NetworkExtensions,
) : BaseViewModel() {

    private val _listBag = MutableLiveData<List<GetBagResponse>>()
    val listBag: LiveData<List<GetBagResponse>> = _listBag

    private val _listLocationCustomer = MutableLiveData<List<GetLocationCustomerResponse>?>()
    val listLocationCustomer: MutableLiveData<List<GetLocationCustomerResponse>?> = _listLocationCustomer

    private val _defaultLocation = MutableLiveData<GetLocationCustomerResponse?>()
    val defaultLocation: LiveData<GetLocationCustomerResponse?> = _defaultLocation

    private val _getOrderFeeState: MutableLiveData<DataState<OrderFeeResponse>> = MutableLiveData()
    val getOrderFeeState: LiveData<DataState<OrderFeeResponse>> get() = _getOrderFeeState

    private val _createOrderState: MutableLiveData<DataState<AddOrderResponse>> = MutableLiveData()
    val createOrderState: LiveData<DataState<AddOrderResponse>> get() = _createOrderState

    private val _getProfile = MutableLiveData<DataState<GetProfileResponse>>()
    val getProfile: LiveData<DataState<GetProfileResponse>> get() = _getProfile

    private val _createPaymentState: MutableLiveData<PostPaymentResponse?> = MutableLiveData()
    val createPaymentState: MutableLiveData<PostPaymentResponse?> get() = _createPaymentState

    private val _deleteBagState: MutableLiveData<DataState<DeleteBagResponse>> = MutableLiveData()
    val deleteBagState: LiveData<DataState<DeleteBagResponse>> get() = _deleteBagState



    override fun onDidBindViewModel() {}

    fun getBagList() {
        networkExtension.checkInternet { isConnect ->
            if (isConnect) {
                compositeDisposable.add(
                    mainRepository.getBagList()
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .toObservable()
                        .subscribe({ response ->
                            response.data.let {
                                FSLogger.d("dang o ham lay danh sach gio hang")
                                _listBag.value = it
                            }
                        },
                            { throwable ->
                                setErrorString(throwable.message.toString())
                            })
                )
            } else {
                setErrorStringId(R.string.error_have_no_internet)
            }
        }
    }

    fun getLocationCustomerList() {
        networkExtension.checkInternet { isConnect ->
            if (isConnect) {
                setLoading(true)
                compositeDisposable.add(
                    locationCustomerRepository.getLocationList()
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .toObservable()
                        .subscribe({ response ->
                            response.data.let { locations ->
                                setLoading(false)
                                _listLocationCustomer.value = locations

                                if (locations != null) {
                                    _defaultLocation.value = locations.find { it.isDefault == true }
                                }
                            }
                        },
                            { throwable ->
                                setLoading(false)
                                setErrorString(throwable.message.toString())
                            })
                )
            } else {
                setErrorStringId(R.string.error_have_no_internet)
            }
        }
    }

    fun getOrderFee(clientOrderCode: String, toName: String,toPhone: String, toAddress: String, toWardName: String, toDistrictName: String, toProvinceName: String, codAmount: Double, weight: Double, content: String) {
        val orderFeeRequest = OrderFeeRequest(clientOrderCode = clientOrderCode,toName = toName,toPhone = toPhone,toAddress = toAddress, toWardName = toWardName, toDistrictName = toDistrictName, toProvinceName = toProvinceName, codAmount = codAmount, weight = weight, content = content)
        FSLogger.e("Huynd: data request $orderFeeRequest")
        networkExtension.checkInternet { isConnect ->
            if (isConnect) {
                compositeDisposable.add(
                    mainRepository.getOrderFee(orderFeeRequest)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .toObservable()
                        .subscribe({ response ->
                            FSLogger.e("Huynd: data response $response")
                            _getOrderFeeState.value = DataState.Success(response)
                        }, { err ->
                            _getOrderFeeState.value = DataState.Error(err)
                        })
                )
            } else {
                setErrorStringId(R.string.no_internet_connection)
            }
        }
    }

    fun createOrder(toName: String, toPhone: String, toAddress: String, toWardName: String, toDistrictName: String, toProvinceName: String, clientOrderCode: String, weight: Double, codAmount: Double, shippingFee: Double, content: String, isFreeShip: Boolean, isPayment: Boolean, note: String) {
        val shippingAddress = ShippingAddress(
            toName = toName,
            toPhone = toPhone,
            toWardName = toWardName,
            toAddress = toAddress, toDistrictName = toDistrictName, toProvinceName = toProvinceName
        )
        val orderItems = _listBag.value?.map { bagItem ->
            OrderItem(
                size = bagItem.size._id,
                shoes = bagItem.shoes._id,
                quantity = bagItem.quantity,
                price = bagItem.price
            )
        } ?: run {
            setErrorString("No items in the order!")
            return
        }

        if (orderItems.isEmpty()) {
            setErrorString("Your cart is empty!")
            return
        }
        val order = Order(
            clientOrderCode = clientOrderCode,
            shippingAddress = shippingAddress,
            weight = weight,
            codAmount = codAmount,
            shippingFee = shippingFee,
            content = content,
            isFreeShip = isFreeShip,
            isPayment = isPayment,
            note = note
        )

        val addOrderRequest = AddOrderRequest(order = order, orderItems = orderItems)

        setLoading(true)
        networkExtension.checkInternet { isConnect ->
            if (isConnect) {
                compositeDisposable.add(
                    orderRepository.createOrder(addOrderRequest = addOrderRequest)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .toObservable()
                        .subscribe({ response ->
                            setLoading(false)
                            _createOrderState.value = DataState.Success(response)
                        }, { err ->
                            setLoading(false)
                            _createOrderState.value = DataState.Error(err)
                            FSLogger.d("loi khi them order $err")
                        })
                )
            } else {
                setLoading(false)
                setErrorStringId(R.string.no_internet_connection)
            }
        }
    }

    fun getProfile() {
//        setLoading(true)
        networkExtension.checkInternet { isConnect ->
            if (isConnect) {
                compositeDisposable.add(
                    profileRepository.getProfile()
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .toObservable()
                        .subscribe({ response ->
                            response.data?.let {
                                setLoading(false)
                                _getProfile.value = DataState.Success(it) // Gói response.data vào DataState.Success
                            }
                        }, { err ->
                            setLoading(false)
                            _getProfile.value = DataState.Error(err)
                        })
                )
            } else {
                _getProfile.postValue(DataState.Error(Exception("Không có kết nối Internet")))
            }
        }
    }

    fun createPayment(clientOrderCode: String, amount: Double, toPhone: String) {
        val postPaymentRequest = PostPaymentRequest(clientOrderCode, amount, toPhone)
        setLoading(true)
        networkExtension.checkInternet { isConnect ->
            if (isConnect) {
                compositeDisposable.add(
                    mainRepository.postPayments(postPaymentRequest = postPaymentRequest)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .toObservable()
                        .subscribe({ response ->
                            setLoading(false)
                            _createPaymentState.value = response.data
                        }, { err ->
                            setLoading(false)
                            setErrorString("$err")
                        })
                )
            } else {
                setLoading(false)
                setErrorStringId(R.string.no_internet_connection)
            }
        }
    }

    fun deleteBag() {
        networkExtension.checkInternet { isConnect ->
            if (isConnect) {
                compositeDisposable.add(
                    mainRepository.deleteBag()
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .toObservable()
                        .subscribe({ response ->
                            _deleteBagState.postValue(DataState.Success(response))
                        }, { err ->
                            _deleteBagState.postValue(DataState.Error(err))
                        })
                )
            } else {
                setErrorStringId(R.string.no_internet_connection)
            }
        }
    }



    private val _paymentSuccess = MutableLiveData<Boolean>()
    val paymentSuccess: LiveData<Boolean> get() = _paymentSuccess

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onCheckPaymentStatus(event: CheckPaymentStatus) {
        FSLogger.e("Huynd: SubscribeSubscribe onCheckPaymentStatus - status: ${event.status}")
        checkPaymentSuccess {
            _paymentSuccess.postValue(event.status)
        }
    }

     fun checkPaymentSuccess(callBack: (Boolean) -> Unit) {
        setLoading(true)
        val clientOrderId = dataManager.getOrderClientID() ?: Constant.EMPTY_STRING
        val orderId = dataManager.getOrderId() ?: Constant.EMPTY_STRING

        FSLogger.e("Huynd: checkPaymentSuccess Request - clientOrderId: $clientOrderId, orderId: $orderId")

        if (clientOrderId.isEmpty() || orderId.isEmpty()) {
            FSLogger.e("Huynd: Invalid clientOrderId or orderId")
            callBack(false)
            return
        }

        val request = CheckPaymentRequest(clientOrderCode = clientOrderId, orderId = orderId)

        networkExtension.checkInternet { isConnect ->
            if (isConnect) {
                compositeDisposable.add(
                    orderRepository.getPaymentStatus(request)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .toObservable()
                        .subscribe({ response ->
                            setLoading(false)
                            _paymentSuccess.value = response
                            FSLogger.e("Huynd: checkPaymentSuccess Response: $response")
                            callBack(response)
                        }, { err ->
                            setLoading(false)
                            FSLogger.e("Huynd: checkPaymentSuccess Error: ${err.message}")
                            callBack(false)
                        })
                )
            } else {
                FSLogger.e("Huynd: No internet connection")
                callBack(false)
            }
        }
    }


}
