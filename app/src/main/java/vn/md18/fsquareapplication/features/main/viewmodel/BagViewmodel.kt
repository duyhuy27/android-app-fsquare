package vn.md18.fsquareapplication.features.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.core.base.BaseViewModel
import vn.md18.fsquareapplication.data.model.DataState
import vn.md18.fsquareapplication.data.network.model.request.AddBagRequest
import vn.md18.fsquareapplication.data.network.model.request.FavoriteRequest
import vn.md18.fsquareapplication.data.network.model.request.UpdateQuantityBagRequest
import vn.md18.fsquareapplication.data.network.model.response.AddBagResponse
import vn.md18.fsquareapplication.data.network.model.response.DeleteBagResponse
import vn.md18.fsquareapplication.data.network.model.response.DeleteFavoriteRespone
import vn.md18.fsquareapplication.data.network.model.response.GetBagResponse
import vn.md18.fsquareapplication.data.network.model.response.UpdateQuantityBagResponse
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
    override fun onDidBindViewModel() {

    }

    fun getBagList() {
        networkExtensions.checkInternet { isConnect ->
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
            }
            else {
                setErrorStringId(R.string.error_have_no_internet)
            }
        }
    }

    fun updateQuantity(_id: String, action: String) {
        var request = UpdateQuantityBagRequest(action = action)
        networkExtensions.checkInternet { isConnect ->
            if (isConnect) {
                compositeDisposable.add(
                    mainRepository.updateQuantityBag(id = _id, updateQuantityBagRequest = request)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .toObservable()
                        .subscribe({ response ->
                            _updateQuantityState.postValue(DataState.Success(response))
                        }, { err ->
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
}