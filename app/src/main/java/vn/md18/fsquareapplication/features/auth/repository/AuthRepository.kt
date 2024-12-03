package vn.md18.fsquareapplication.features.auth.repository

import io.reactivex.Flowable
import vn.md18.fsquareapplication.data.network.model.request.LoginRequest
import vn.md18.fsquareapplication.data.network.model.request.SignUpRequest
import vn.md18.fsquareapplication.data.network.model.request.VerifyRequest
import vn.md18.fsquareapplication.data.network.model.response.auth.LoginResponse
import vn.md18.fsquareapplication.data.network.model.response.auth.SignUpResponse
import vn.md18.fsquareapplication.data.network.model.response.auth.VerifyResponse

interface AuthRepository {
    fun signUp(signUpRequest: SignUpRequest) : Flowable<SignUpResponse>
    fun login(loginRequest: LoginRequest) : Flowable<LoginResponse>
    fun verify(verifyRequest: VerifyRequest) : Flowable<VerifyResponse>
}