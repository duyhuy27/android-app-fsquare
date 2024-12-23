package vn.md18.fsquareapplication.features.auth.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.core.base.BaseViewModel
import vn.md18.fsquareapplication.data.model.DataState
import vn.md18.fsquareapplication.data.model.ErrorResponse
import vn.md18.fsquareapplication.data.network.model.request.LoginRequest
import vn.md18.fsquareapplication.data.network.model.request.SignUpRequest
import vn.md18.fsquareapplication.data.network.model.request.VerifyRequest
import vn.md18.fsquareapplication.data.network.model.response.auth.LoginResponse
import vn.md18.fsquareapplication.data.network.model.response.auth.SignUpResponse
import vn.md18.fsquareapplication.data.network.model.response.auth.VerifyResponse
import vn.md18.fsquareapplication.features.auth.repository.AuthRepository
import vn.md18.fsquareapplication.utils.extensions.NetworkExtensions
import vn.md18.fsquareapplication.utils.fslogger.FSLogger
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val networkExtension : NetworkExtensions,
) : BaseViewModel() {

    private val _loginState: MutableLiveData<DataState<LoginResponse>> = MutableLiveData()
    val loginState: LiveData<DataState<LoginResponse>> get() = _loginState

    private val _signUpState: MutableLiveData<DataState<SignUpResponse>> = MutableLiveData()
    val signUpState: LiveData<DataState<SignUpResponse>> get() = _signUpState

    private val _verifyState: MutableLiveData<DataState<VerifyResponse>> = MutableLiveData()
    val verifyState: LiveData<DataState<VerifyResponse>> get() = _verifyState




    override fun onDidBindViewModel() {
    }

    fun test () {
        setLoading(true)
    }



    fun checkTokenToNavigate(callBack: () -> Unit) {
        if (!dataManager.getToken().isNullOrEmpty()) {
            callBack()
        }
    }

    fun signUp(email: String) {
        val signUpRequest = SignUpRequest(email = email)
        setLoading(true)
        networkExtension.checkInternet {
            isConnect ->
            if (isConnect) {
                compositeDisposable.add(
                    authRepository.signUp(signUpRequest = signUpRequest)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .toObservable()
                        .subscribe ({ response ->
                            setLoading(false)
                            _signUpState.value = DataState.Success(response)
                        },{
                            err ->
                            setLoading(false)
                            _signUpState.value = DataState.Error(err)
                        })
                )
            }
            else {
                setErrorStringId(R.string.no_internet_connection)
            }
        }
    }

    fun login(email: String) {
        val loginRequest = LoginRequest(email = email)
        setLoading(true)
        networkExtension.checkInternet {
                isConnect ->
            if (isConnect) {
                compositeDisposable.add(
                    authRepository.login(loginRequest = loginRequest)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .toObservable()
                        .subscribe ({ response ->
                            setLoading(false)
                            _loginState.value = DataState.Success(response)
                        },{
                            err ->
                            setLoading(false)
                            _loginState.value = DataState.Error(err)
                        })
                )
            }
            else {
                setLoading(false)
                setErrorStringId(R.string.no_internet_connection)
            }
        }
    }

    fun verify(otp: String, email: String, type: String, fcmToken: String) {
        val verifyRequest = VerifyRequest(otp = otp, email = email, type = type, fcmToken = fcmToken)
        setLoading(true)
        networkExtension.checkInternet { isConnect ->
            if (isConnect) {
                _verifyState.value = DataState.Loading
                compositeDisposable.add(
                    authRepository.verify(verifyRequest = verifyRequest)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .toObservable()
                        .subscribe({ response ->
                            response.data?.let { data ->
                                setLoading(false)
                                _verifyState.value = DataState.Success(response)
                            } ?: run {
                                setLoading(false)
                                _verifyState.value = DataState.Error(Exception("No data received"))
                            }
                        }, { error ->
                            setLoading(false)
                            _verifyState.value = DataState.Error(error)
                        })
                )
            } else {
                setLoading(false)
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