package vn.md18.fsquareapplication.features.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.core.base.BaseViewModel
import vn.md18.fsquareapplication.data.network.model.response.BrandResponse
import vn.md18.fsquareapplication.data.network.model.response.ProductResponse
import vn.md18.fsquareapplication.features.main.repository.MainRepository
import vn.md18.fsquareapplication.utils.ErrorUtils
import vn.md18.fsquareapplication.utils.extensions.NetworkExtensions
import vn.md18.fsquareapplication.utils.fslogger.FSLogger
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
                    mainRepository.getProductList(size = 1, page = 1)
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

}