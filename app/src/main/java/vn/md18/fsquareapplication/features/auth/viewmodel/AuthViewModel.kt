package vn.md18.fsquareapplication.features.auth.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.core.base.BaseViewModel
import vn.md18.fsquareapplication.data.model.ErrorResponse
import vn.md18.fsquareapplication.data.network.model.request.SignUpRequest
import vn.md18.fsquareapplication.features.auth.repository.AuthRepository
import vn.md18.fsquareapplication.utils.extensions.NetworkExtensions
import vn.md18.fsquareapplication.utils.rx.ObservableWrapper
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val networkExtension : NetworkExtensions,
) : BaseViewModel() {

    private val _loginState : MutableLiveData<String> = MutableLiveData("false")

    val loginState : LiveData<String> get() = _loginState


    override fun onDidBindViewModel() {
        Log.e("Huynd", "Nhay vao day")
    }

    fun test () {
        setLoading(true)
    }

    fun signUp(email: String) {
        val signUpRequest = SignUpRequest(email = email)
        networkExtension.checkInternet {
            isConnect ->
            if (isConnect) {
                compositeDisposable.add(
                    authRepository.signUp(signUpRequest = signUpRequest)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .toObservable()
                        .subscribeWith(object :
                            ObservableWrapper<SignUpRequest>(this::setLoading, this::onError) {
                            override fun onSuccessData(data: SignUpRequest) {
                                _loginState.value = data.email
                            }
                        }

                        )
                )
            }
            else {
                setErrorStringId(R.string.no_internet_connection)
            }
        }
    }

    fun onError(error: ErrorResponse) {
        setLoading(false)
        if(error.status == "Conflict"){

        }
    }


}