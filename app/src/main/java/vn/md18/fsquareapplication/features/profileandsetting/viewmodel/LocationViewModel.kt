package vn.md18.fsquareapplication.features.profileandsetting.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.core.base.BaseViewModel
import vn.md18.fsquareapplication.data.network.model.response.GetBagResponse
import vn.md18.fsquareapplication.data.network.model.response.GetDistrictsResponse
import vn.md18.fsquareapplication.data.network.model.response.GetProvinceResponse
import vn.md18.fsquareapplication.data.network.model.response.GetWardsRepose
import vn.md18.fsquareapplication.features.profileandsetting.repositoriy.DistrictRepository
import vn.md18.fsquareapplication.features.profileandsetting.repositoriy.ProfileRepository
import vn.md18.fsquareapplication.features.profileandsetting.repositoriy.ProvinceRepository
import vn.md18.fsquareapplication.features.profileandsetting.repositoriy.WardRepository
import vn.md18.fsquareapplication.utils.extensions.NetworkExtensions
import javax.inject.Inject


@HiltViewModel
class LocationViewModel @Inject constructor(
    private val provinceRepository: ProvinceRepository,
    private val networkExtensions: NetworkExtensions,
    private val districtRepository: DistrictRepository,
    private val wardRepository: WardRepository,

) : BaseViewModel() {
    private val _listProvince = MutableLiveData<List<GetProvinceResponse>>()
    val listProvince: LiveData<List<GetProvinceResponse>> = _listProvince

    private val _listDistrict = MutableLiveData<List<GetDistrictsResponse>>()
    val listDistrict: LiveData<List<GetDistrictsResponse>> = _listDistrict

    private val _listWard = MutableLiveData<List<GetWardsRepose>>()
    val listWard: LiveData<List<GetWardsRepose>> = _listWard


    override fun onDidBindViewModel() {
    }

    fun getProvinceList() {
        networkExtensions.checkInternet { isConnect ->
            if (isConnect) {
                compositeDisposable.add(
                    provinceRepository.getProvinceList()
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .toObservable()
                        .subscribe({ response ->
                            response.data.let {
                                _listProvince.value = it
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

    fun getDistrictList(_id:String) {
        networkExtensions.checkInternet { isConnect ->
            if (isConnect) {
                compositeDisposable.add(
                    districtRepository.getDistrictList(id = _id)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .toObservable()
                        .subscribe({ response ->
                            response.data.let {
                                _listDistrict.value = it
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


    fun getWardList(_id:String) {
        networkExtensions.checkInternet { isConnect ->
            if (isConnect) {
                compositeDisposable.add(
                    wardRepository.getWardList(id = _id)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .toObservable()
                        .subscribe({ response ->
                            response.data.let {
                                _listWard.value = it
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