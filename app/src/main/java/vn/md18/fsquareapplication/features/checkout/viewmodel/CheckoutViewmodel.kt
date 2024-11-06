package vn.md18.fsquareapplication.features.checkout.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.core.base.BaseViewModel
import vn.md18.fsquareapplication.data.network.model.response.GetBagResponse
import vn.md18.fsquareapplication.data.network.model.response.location.GetLocationCustomerResponse
import vn.md18.fsquareapplication.features.auth.repository.AuthRepository
import vn.md18.fsquareapplication.features.main.repository.MainRepository
import vn.md18.fsquareapplication.features.profileandsetting.repositoriy.location.LocationCustomerRepository
import vn.md18.fsquareapplication.utils.extensions.NetworkExtensions
import vn.md18.fsquareapplication.utils.fslogger.FSLogger
import javax.inject.Inject

@HiltViewModel
class CheckoutViewmodel @Inject constructor(
    private val mainRepository: MainRepository,
    private val locationCustomerRepository: LocationCustomerRepository,
    private val networkExtension: NetworkExtensions,
) : BaseViewModel() {

    private val _listBag = MutableLiveData<List<GetBagResponse>>()
    val listBag: LiveData<List<GetBagResponse>> = _listBag

    private val _listLocationCustomer = MutableLiveData<List<GetLocationCustomerResponse>?>()
    val listLocationCustomer: MutableLiveData<List<GetLocationCustomerResponse>?> = _listLocationCustomer

    private val _defaultLocation = MutableLiveData<GetLocationCustomerResponse?>()
    val defaultLocation: LiveData<GetLocationCustomerResponse?> = _defaultLocation

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
}
