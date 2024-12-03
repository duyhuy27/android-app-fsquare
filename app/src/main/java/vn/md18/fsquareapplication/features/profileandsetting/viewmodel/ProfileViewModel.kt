package vn.md18.fsquareapplication.features.profileandsetting.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import vn.md18.fsquareapplication.core.base.BaseViewModel
import vn.md18.fsquareapplication.data.model.DataState
import vn.md18.fsquareapplication.data.network.model.request.UpdateProfileRequest
import vn.md18.fsquareapplication.data.network.model.response.UpdateProfileResponse
import vn.md18.fsquareapplication.data.network.model.response.location.UpdateLocationCustomerResponse
import vn.md18.fsquareapplication.data.network.model.response.profile.GetProfileResponse
import vn.md18.fsquareapplication.features.profileandsetting.repositoriy.ProfileRepository
import vn.md18.fsquareapplication.features.profileandsetting.repositoriy.editprofile.EditProfileRepository
import vn.md18.fsquareapplication.utils.extensions.NetworkExtensions
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val networkExtensions: NetworkExtensions,
    private val editProfileRepository: EditProfileRepository

) : BaseViewModel() {

    private val _countryNamesState: MutableLiveData<DataState<List<String>>> = MutableLiveData()
    val countryNamesState: LiveData<DataState<List<String>>> get() = _countryNamesState

    private val _getProfile = MutableLiveData<DataState<GetProfileResponse>>()
    val getProfile: LiveData<DataState<GetProfileResponse>> get() = _getProfile

    private val _updateProfileState: MutableLiveData<DataState<UpdateProfileResponse>> = MutableLiveData()
    val updateProfileState: LiveData<DataState<UpdateProfileResponse>> get() = _updateProfileState
    override fun onDidBindViewModel() {

    }

    fun getProfile() {
//        setLoading(true)
        networkExtensions.checkInternet { isConnect ->
            if (isConnect) {
                compositeDisposable.add(
                    editProfileRepository.getProfile()
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .toObservable()
                        .subscribe({ response ->
                            response.data?.let {
                                setLoading(false)
                                _getProfile.value = DataState.Success(it) // Gói response.data vào DataState.Success
                            }
                        }, { err ->
                            setLoading(false)
                            _getProfile.value = DataState.Error(err)
                        })
                )
            } else {
                _getProfile.postValue(DataState.Error(Exception("Không có kết nối Internet")))
            }
        }
    }

    fun updateProfile(firstName: String, lastName: String, birthDay: String, phone: String, fcmToken: String) {
        val updateProfileRequest = UpdateProfileRequest(firstName, lastName, birthDay, phone, fcmToken)
        networkExtensions.checkInternet { isConnect ->
            if (isConnect) {
                compositeDisposable.add(
                    editProfileRepository.updateProfile(updateProfileRequest)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .toObservable()
                        .subscribe({ response ->
                            _updateProfileState.value = DataState.Success(response)
                        }, { err ->
                            _updateProfileState.value = DataState.Error(err)
                        })
                )
            } else {
                _updateProfileState.postValue(DataState.Error(Exception("Không có kết nối Internet")))
            }
        }
    }
}