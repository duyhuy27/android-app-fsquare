package vn.md18.fsquareapplication.features.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.core.base.BaseViewModel
import vn.md18.fsquareapplication.data.model.DataState
import vn.md18.fsquareapplication.data.network.model.request.AddBagRequest
import vn.md18.fsquareapplication.data.network.model.response.Classification
import vn.md18.fsquareapplication.data.network.model.response.ClassificationShoes
import vn.md18.fsquareapplication.data.network.model.response.GetClassificationResponse
import vn.md18.fsquareapplication.data.network.model.response.ProductResponse
import vn.md18.fsquareapplication.data.network.model.response.bag.AddBagResponse
import vn.md18.fsquareapplication.data.network.model.response.bag.GetBagResponse
import vn.md18.fsquareapplication.di.component.scheduler.SchedulerProvider
import vn.md18.fsquareapplication.features.detail.repository.DetailProductRepository
import vn.md18.fsquareapplication.features.main.repository.MainRepository
import vn.md18.fsquareapplication.utils.extensions.NetworkExtensions
import vn.md18.fsquareapplication.utils.fslogger.FSLogger
import javax.inject.Inject

@HiltViewModel
class DetailProductViewModel @Inject constructor(
    private val detailProductRepository: DetailProductRepository,
    private val mainRepository: MainRepository,
    private val networkExtensions: NetworkExtensions,
) : BaseViewModel() {
    var _product = MutableLiveData<ProductResponse>()
    val product: MutableLiveData<ProductResponse>
        get() = _product

    private val _listColor = MutableLiveData<List<GetClassificationResponse>>()
    val listColor: LiveData<List<GetClassificationResponse>> = _listColor
    private val _listSize = MutableLiveData<List<ClassificationShoes>>()
    val listSize: LiveData<List<ClassificationShoes>> = _listSize

    private val _classification = MutableLiveData<Classification>()
    val classification: LiveData<Classification> = _classification

    private val _addBagState: MutableLiveData<DataState<AddBagResponse>> = MutableLiveData()
    val addBagState: LiveData<DataState<AddBagResponse>> get() = _addBagState

    private val _listBag = MutableLiveData<List<GetBagResponse>>()
    val listBag: LiveData<List<GetBagResponse>> = _listBag

    override fun onDidBindViewModel() {
    }

    fun callApiGetDetailProduct(productId: String) {
        networkExtensions.checkInternet { isConnect ->
            if (isConnect) {
                setLoading(true)
                compositeDisposable.add(
                    detailProductRepository.getProductDetail(productId)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .toObservable()
                        .subscribe({ response ->
                            response.data.let {
                                setLoading(false)
                                _product.value = it
                            }
                        },
                            { throwable ->
                                setLoading(false)
                                setErrorString(throwable.message.toString())
                            })
                )
            } else {
                setLoading(false)
                setErrorStringId(R.string.error_have_no_internet)
            }
        }
    }

    fun getColorList(id: String) {
        networkExtensions.checkInternet { isConnect ->
            if (isConnect) {
                compositeDisposable.add(
                    detailProductRepository.getColor(id)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .toObservable()
                        .subscribe({ response ->
                            response.data.let {
                                _listColor.value = it
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

    fun getSizeList(id: String) {
        networkExtensions.checkInternet { isConnect ->
            if (isConnect) {
                compositeDisposable.add(
                    detailProductRepository.getSize(id)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .toObservable()
                        .subscribe({ response ->
                            response.data.let {
                                _listSize.value = it
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

    fun getClassification(id: String) {
        networkExtensions.checkInternet { isConnect ->
            if (isConnect) {
                compositeDisposable.add(
                    detailProductRepository.getClassification(id)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .toObservable()
                        .subscribe({ response ->
                            response.data.let {
                                _classification.value = it
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
}