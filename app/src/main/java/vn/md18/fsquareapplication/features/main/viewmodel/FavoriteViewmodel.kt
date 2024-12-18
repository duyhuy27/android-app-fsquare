package vn.md18.fsquareapplication.features.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.core.base.BaseViewModel
import vn.md18.fsquareapplication.data.model.DataState
import vn.md18.fsquareapplication.data.network.model.request.FavoriteRequest
import vn.md18.fsquareapplication.data.network.model.response.ProductResponse
import vn.md18.fsquareapplication.data.network.model.response.favorite.CreateFavoriteResponse
import vn.md18.fsquareapplication.data.network.model.response.favorite.DeleteFavoriteRespone
import vn.md18.fsquareapplication.data.network.model.response.favorite.FavoriteResponse
import vn.md18.fsquareapplication.features.main.repository.MainRepository
import vn.md18.fsquareapplication.utils.extensions.NetworkExtensions
import javax.inject.Inject

@HiltViewModel
class FavoriteViewmodel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkExtensions: NetworkExtensions
) : BaseViewModel() {

    private val _listFavorite = MutableLiveData<List<FavoriteResponse>>()
    val listFavorite: LiveData<List<FavoriteResponse>> = _listFavorite

    private val _favoriteState: MutableLiveData<DataState<CreateFavoriteResponse>> = MutableLiveData()
    val favoriteState: LiveData<DataState<CreateFavoriteResponse>> get() = _favoriteState

    private val _deleteFavoriteState: MutableLiveData<DataState<DeleteFavoriteRespone>> = MutableLiveData()
    val deleteFavoriteState: LiveData<DataState<DeleteFavoriteRespone>> get() = _deleteFavoriteState

    private val _listProduct = MutableLiveData<List<ProductResponse>>()
    val listProduct: LiveData<List<ProductResponse>> = _listProduct

    private val _listProductBrand = MutableLiveData<List<ProductResponse>>()
    val listProductBrand: LiveData<List<ProductResponse>> = _listProductBrand
    override fun onDidBindViewModel() {

    }

    fun getFavoriteList() {
        setLoading(true)
        networkExtensions.checkInternet { isConnect ->
            if (isConnect) {
                compositeDisposable.add(
                    mainRepository.getFavoriteList()
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .toObservable()
                        .subscribe({ response ->
                            response.data.let {
                                setLoading(false)
                                _listFavorite.value = it
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

    fun getProductV1() {
        setLoading(true)
        networkExtensions.checkInternet { isConnect ->
            if (isConnect) {
                compositeDisposable.add(
                    mainRepository.getProductListV1(size = 10, page = 1)
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



    fun getProductByBrandV1(idBrand: String) {
        setLoading(true)
        networkExtensions.checkInternet { isConnect ->
            if (isConnect) {
                compositeDisposable.add(
                    mainRepository.getProductListByBrandV1(size = 10, page = 1, brand = idBrand)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .toObservable()
                        .subscribe({ response ->
                            setLoading(false)
                            response.data.let {
                                _listProductBrand.value = it
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
        setLoading(false)
        networkExtensions.checkInternet { isConnect ->
            if (isConnect) {
                compositeDisposable.add(
                    mainRepository.deleteFavorite(id = _id)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .toObservable()
                        .subscribe({ response ->
                            setLoading(false)
                            _deleteFavoriteState.postValue(DataState.Success(response))
                        }, { err ->
                            setLoading(false)
                            _deleteFavoriteState.postValue(DataState.Error(err))
                        })
                )
            } else {
                setLoading(false)
                setErrorStringId(R.string.no_internet_connection)
            }
        }
    }


}