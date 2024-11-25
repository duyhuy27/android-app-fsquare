package vn.md18.fsquareapplication.features.detail.viewmodel

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.core.base.BaseViewModel
import vn.md18.fsquareapplication.data.network.model.response.ProductResponse
import vn.md18.fsquareapplication.di.component.scheduler.SchedulerProvider
import vn.md18.fsquareapplication.features.detail.repository.DetailProductRepository
import vn.md18.fsquareapplication.utils.extensions.NetworkExtensions
import javax.inject.Inject

@HiltViewModel
class DetailProductViewModel @Inject constructor(
    private val detailProductRepository: DetailProductRepository,
    private val networkExtensions: NetworkExtensions,
) : BaseViewModel() {
    var _product = MutableLiveData<ProductResponse>()
    val product: MutableLiveData<ProductResponse>
        get() = _product

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
}