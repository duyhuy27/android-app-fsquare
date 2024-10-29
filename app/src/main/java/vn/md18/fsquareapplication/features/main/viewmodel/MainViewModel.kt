package vn.md18.fsquareapplication.features.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.core.base.BaseViewModel
import vn.md18.fsquareapplication.data.model.DataState
import vn.md18.fsquareapplication.data.network.model.request.FavoriteRequest
import vn.md18.fsquareapplication.data.network.model.request.UpdateQuantityBagRequest
import vn.md18.fsquareapplication.data.network.model.response.CreateFavoriteResponse
import vn.md18.fsquareapplication.data.network.model.response.DeleteFavoriteRespone
import vn.md18.fsquareapplication.data.network.model.response.FavoriteResponse
import vn.md18.fsquareapplication.data.network.model.response.GetBagResponse
import vn.md18.fsquareapplication.data.network.model.response.ProductResponse
import vn.md18.fsquareapplication.data.network.model.response.UpdateQuantityBagResponse
import vn.md18.fsquareapplication.features.main.repository.MainRepository
import vn.md18.fsquareapplication.utils.extensions.NetworkExtensions
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkExtensions: NetworkExtensions
) : BaseViewModel() {


    private val _productAndBrandData = MutableLiveData<List<Any>>()
    val productAndBrandData: LiveData<List<Any>> get() = _productAndBrandData

    private val _loginState = MutableLiveData<Boolean>()
    val loginState: LiveData<Boolean> = _loginState

    private val _listProductBanner = MutableLiveData<List<ProductResponse>>()
    val listProductBanner: LiveData<List<ProductResponse>> = _listProductBanner

    private val _listFavorite = MutableLiveData<List<FavoriteResponse>>()
    val listFavorite: LiveData<List<FavoriteResponse>> = _listFavorite

    private val _favoriteState: MutableLiveData<DataState<CreateFavoriteResponse>> = MutableLiveData()
    val favoriteState: LiveData<DataState<CreateFavoriteResponse>> get() = _favoriteState

    private val _deleteFavoriteState: MutableLiveData<DataState<DeleteFavoriteRespone>> = MutableLiveData()
    val deleteFavoriteState: LiveData<DataState<DeleteFavoriteRespone>> get() = _deleteFavoriteState

    private val _listBag = MutableLiveData<List<GetBagResponse>>()
    val listBag: LiveData<List<GetBagResponse>> = _listBag

    private val _updateQuantityState: MutableLiveData<DataState<UpdateQuantityBagResponse>> = MutableLiveData()
    val updateQuantityState: LiveData<DataState<UpdateQuantityBagResponse>> get() = _updateQuantityState


    override fun onDidBindViewModel() {
    }

    companion object {
        const val TAB_DASHBOARD_PAGE = 0
        const val TAB_CARD_CONTEXT = 1
        const val TAB_ORDERS = 2
        const val TAB_WALLET = 3
        const val TAB_PROFILE = 4
    }

    fun checkLoading() {
        viewModelScope.launch {
            setLoading(true)
            //delay
            delay(1000)
            _loginState.postValue(true)

            setLoading(false)
            _loginState.postValue(false)
        }
    }

    fun getProductBanner() {
        networkExtensions.checkInternet { isConnect ->
            if (isConnect) {
                compositeDisposable.add(
                    mainRepository.getProductList(size = 10, page = 1)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .toObservable()
                        .subscribe({ response ->
                            response.data.let {
                                _listProductBanner.value = it
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

    fun getFavoriteList() {
        networkExtensions.checkInternet { isConnect ->
            if (isConnect) {
                compositeDisposable.add(
                    mainRepository.getFavoriteList()
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .toObservable()
                        .subscribe({ response ->
                            response.data.let {
                                _listFavorite.value = it
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

    fun createFavorite(shoes: String) {
        val favoriteRequest = FavoriteRequest(shoes = shoes)
        networkExtensions.checkInternet { isConnect ->
            if (isConnect) {
                compositeDisposable.add(
                    mainRepository.createFavorite(favoriteRequest = favoriteRequest)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .toObservable()
                        .subscribe({ response ->
                            _favoriteState.value = DataState.Success(response)
                        }, { err ->
                            _favoriteState.value = DataState.Error(err)
                        })
                )
            } else {
                setErrorStringId(R.string.no_internet_connection)
            }
        }
    }

    fun deleteFavorite(_id: String) {
        networkExtensions.checkInternet { isConnect ->
            if (isConnect) {
                compositeDisposable.add(
                    mainRepository.deleteFavorite(id = _id)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .toObservable()
                        .subscribe({ response ->
                            _deleteFavoriteState.postValue(DataState.Success(response))
                        }, { err ->
                            _deleteFavoriteState.postValue(DataState.Error(err))
                        })
                )
            } else {
                setErrorStringId(R.string.no_internet_connection)
            }
        }
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
                                _listBag.value = it
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

}