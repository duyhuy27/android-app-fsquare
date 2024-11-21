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
import vn.md18.fsquareapplication.data.network.model.request.AddBagRequest
import vn.md18.fsquareapplication.data.network.model.request.FavoriteRequest
import vn.md18.fsquareapplication.data.network.model.response.BrandResponse
import vn.md18.fsquareapplication.data.network.model.response.CategoriesResponse
import vn.md18.fsquareapplication.data.network.model.response.favorite.CreateFavoriteResponse
import vn.md18.fsquareapplication.data.network.model.response.ProductResponse
import vn.md18.fsquareapplication.data.network.model.response.bag.AddBagResponse
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

    private val _listProduct = MutableLiveData<List<ProductResponse>>()
    val listProduct: LiveData<List<ProductResponse>> = _listProduct

    private val _listBrands = MutableLiveData<List<BrandResponse>>()
    val listBrands: LiveData<List<BrandResponse>> = _listBrands

    private val _listCategories = MutableLiveData<List<CategoriesResponse>>()
    val listCategories: LiveData<List<CategoriesResponse>> = _listCategories

    private val _favoriteState: MutableLiveData<DataState<Boolean>> = MutableLiveData()
    val favoriteState: LiveData<DataState<Boolean>> get() = _favoriteState


    // remember remove
    private val _addBagState: MutableLiveData<DataState<AddBagResponse>> = MutableLiveData()
    val addBagState: LiveData<DataState<AddBagResponse>> get() = _addBagState
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
        setLoading(true)
        networkExtensions.checkInternet { isConnect ->
            if (isConnect) {
                compositeDisposable.add(
                    mainRepository.getProductList(size = 10, page = 1)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .toObservable()
                        .subscribe({ response ->
                            setLoading(false)
                            response.data.let {
                                _listProductBanner.value = it
                                setErrorString(response.message.toString())
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

    fun getProduct() {
        setLoading(true)
        networkExtensions.checkInternet { isConnect ->
            if (isConnect) {
                compositeDisposable.add(
                    mainRepository.getProductList(size = 10, page = 1)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .toObservable()
                        .subscribe({ response ->
                            setLoading(false)
                            response.data.let {
                                _listProduct.value = it
                                setErrorString(response.message.toString())
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

    fun getBrands() {
        setLoading(true)
        networkExtensions.checkInternet { isConnect ->
            if (isConnect) {
                compositeDisposable.add(
                    mainRepository.getBrands(size = 10, page = 1)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .toObservable()
                        .subscribe({ response ->
                            setLoading(false)
                            response.data.let {
                                _listBrands.value = it
                                setErrorString(response.message.toString())
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

    fun getCategories() {
        setLoading(true)
        networkExtensions.checkInternet { isConnect ->
            if (isConnect) {
                compositeDisposable.add(
                    mainRepository.getCategories(size = 10, page = 1)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .toObservable()
                        .subscribe({ response ->
                            setLoading(false)
                            response.data.let {
                                _listCategories.value = it
                                setErrorString(response.message.toString())
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

    fun createFavorite(productId: String, isAdding: Boolean) {
        val favoriteRequest = FavoriteRequest(shoes = productId)
        setLoading(true)
        networkExtensions.checkInternet { isConnect ->
            if (isConnect) {
                compositeDisposable.add(
                    mainRepository.createFavorite(favoriteRequest)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .toObservable()
                        .subscribe({ _ ->
                            setLoading(false)
                            _favoriteState.value = DataState.Success(isAdding)
                        }, { error ->
                            setLoading(false)
                            _favoriteState.value = DataState.Error(error)
                        })
                )
            } else {
                setLoading(false)
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
}