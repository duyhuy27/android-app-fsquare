package vn.md18.fsquareapplication.features.profileandsetting.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import vn.md18.fsquareapplication.core.base.BaseViewModel
import vn.md18.fsquareapplication.data.model.DataState
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

    private val _getProfile = MutableLiveData<DataState<GetProfileResponse?>>()
    val getProfile: LiveData<DataState<GetProfileResponse?>> get() = _getProfile
    override fun onDidBindViewModel() {

    }

    fun getProfile() {
        networkExtensions.checkInternet { isConnect ->
            if (isConnect) {
                compositeDisposable.add(
                    editProfileRepository.getProfile()
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .toObservable()
                        .subscribe({ response ->
                            _getProfile.value = DataState.Success(response)
                        }, { err ->
                            _getProfile.value = DataState.Error(err)
                        })
                )
            } else {
                _getProfile.postValue(DataState.Error(Exception("Không có kết nối Internet")))
            }
        }
    }

//    fun getCountryNames() {
//        networkExtensions.checkInternet { isConnect ->
//            if (isConnect) {
//                _countryNamesState.postValue(DataState.Loading)
//                compositeDisposable.add(
//                    profileRepository.getCountryNames()
//                        .subscribeOn(schedulerProvider.io())
//                        .observeOn(schedulerProvider.ui())
//                        .map { countryList ->
//                            // Chuyển đổi từ List<NameCountryResponse> sang List<String>
//                            countryList.map { it.name.common }.sorted()
//                        }
//                        .subscribe(
//                            { names ->
//                                _countryNamesState.value = DataState.Success(names)
//                            },
//                            { error ->
//                                _countryNamesState.postValue(DataState.Error(error))
//                                Log.e("ProfileViewModel", "Error fetching country names", error)
//                            }
//                        )
//                )
//            } else {
//                _countryNamesState.postValue(DataState.Error(Exception("Không có kết nối Internet")))
//            }
//        }
//    }

}