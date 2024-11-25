package vn.md18.fsquareapplication.features.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.core.base.BaseViewModel
import vn.md18.fsquareapplication.data.model.DataState
import vn.md18.fsquareapplication.data.network.model.request.AddBagRequest
import vn.md18.fsquareapplication.data.network.model.request.UpdateQuantityBagRequest
import vn.md18.fsquareapplication.data.network.model.request.order.OrderFeeRequest
import vn.md18.fsquareapplication.data.network.model.response.bag.AddBagResponse
import vn.md18.fsquareapplication.data.network.model.response.bag.DeleteBagResponse
import vn.md18.fsquareapplication.data.network.model.response.bag.GetBagResponse
import vn.md18.fsquareapplication.data.network.model.response.bag.UpdateQuantityBagResponse
import vn.md18.fsquareapplication.data.network.model.response.order.OrderFeeResponse
import vn.md18.fsquareapplication.features.main.repository.MainRepository
import vn.md18.fsquareapplication.utils.extensions.NetworkExtensions
import vn.md18.fsquareapplication.utils.fslogger.FSLogger
import javax.inject.Inject

@HiltViewModel
class BagViewmodel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkExtensions: NetworkExtensions
) : BaseViewModel() {
    private val _listBag = MutableLiveData<List<GetBagResponse>>()
    val listBag: LiveData<List<GetBagResponse>> = _listBag

    private val _updateQuantityState: MutableLiveData<DataState<UpdateQuantityBagResponse>> = MutableLiveData()
    val updateQuantityState: LiveData<DataState<UpdateQuantityBagResponse>> get() = _updateQuantityState

    private val _addBagState: MutableLiveData<DataState<AddBagResponse>> = MutableLiveData()
    val addBagState: LiveData<DataState<AddBagResponse>> get() = _addBagState

    private val _deleteBagState: MutableLiveData<DataState<DeleteBagResponse>> = MutableLiveData()
    val deleteBagState: LiveData<DataState<DeleteBagResponse>> get() = _deleteBagState

    private val _deleteBagByIdState: MutableLiveData<DataState<DeleteBagResponse>> = MutableLiveData()
    val deleteBagByIdState: LiveData<DataState<DeleteBagResponse>> get() = _deleteBagByIdState
    override fun onDidBindViewModel() {

    }

    fun getBagList() {
        setLoading(true)
        networkExtensions.checkInternet { isConnect ->
            if (isConnect) {
                compositeDisposable.add(
                    mainRepository.getBagList()
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .toObservable()
                        .subscribe({ response ->
                            setLoading(false)
                            response.data.let {
                                _listBag.value = it
                            }
                        },
                            { throwable ->
                                setLoading(false)
                                setErrorString(throwable.message.toString())
                            })
                )
            }
            else {
                setLoading(false)
                setErrorStringId(R.string.error_have_no_internet)
            }
        }
    }

    fun updateQuantity(_id: String, action: String) {
        var request = UpdateQuantityBagRequest(action = action)
        setLoading(true)
        networkExtensions.checkInternet { isConnect ->
            if (isConnect) {
                compositeDisposable.add(
                    mainRepository.updateQuantityBag(id = _id, updateQuantityBagRequest = request)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .toObservable()
                        .subscribe({ response ->
                            setLoading(false)
                            _updateQuantityState.postValue(DataState.Success(response))
                        }, { err ->
                            setLoading(false)
                            _updateQuantityState.postValue(DataState.Error(err))
                        })
                )
            } else {
                setErrorStringId(R.string.no_internet_connection)
            }
        }
    }

    fun createBag(size: String, quantity: Int) {
        val addBagRequest = AddBagRequest(size, quantity)
        networkExtensions.checkInternet { isConnect ->
            if (isConnect) {
                compositeDisposable.add(
                    mainRepository.createBag(addBagRequest)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .toObservable()
                        .subscribe({ response ->
                            _addBagState.value = DataState.Success(response)
                        }, { err ->
                            _addBagState.value = DataState.Error(err)
                        })
                )
            } else {
                setErrorStringId(R.string.no_internet_connection)
            }
        }
    }

    fun deleteBag() {
        networkExtensions.checkInternet { isConnect ->
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

    fun deleteBagById(id: String) {
        networkExtensions.checkInternet { isConnect ->
            if (isConnect) {
                compositeDisposable.add(
                    mainRepository.deleteBagById(id)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .toObservable()
                        .subscribe({ response ->
                            _deleteBagByIdState.postValue(DataState.Success(response))
                        }, { err ->
                            _deleteBagByIdState.postValue(DataState.Error(err))
                        })
                )
            } else {
                setErrorStringId(R.string.no_internet_connection)
            }
        }
    }
}