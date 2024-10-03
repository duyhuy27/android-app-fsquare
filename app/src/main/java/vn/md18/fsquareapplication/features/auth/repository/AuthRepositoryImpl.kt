package vn.md18.fsquareapplication.features.auth.repository

import io.reactivex.Flowable
import vn.md18.fsquareapplication.data.network.model.request.LoginRequest
import vn.md18.fsquareapplication.data.network.model.request.SignUpRequest
import vn.md18.fsquareapplication.data.network.model.request.VerifyRequest
import vn.md18.fsquareapplication.data.network.model.response.LoginResponse
import vn.md18.fsquareapplication.data.network.model.response.SignUpResponse
import vn.md18.fsquareapplication.data.network.model.response.VerifyResponse
import vn.md18.fsquareapplication.data.network.retrofit.ApplicationService
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val applicationService: ApplicationService
)  : AuthRepository {

    override fun signUp(signUpRequest: SignUpRequest): Flowable<SignUpResponse> {
        return applicationService.signUp(signUpRequest)
    }

    override fun login(loginRequest: LoginRequest): Flowable<LoginResponse> {
        return applicationService.login(loginRequest)
    }

    override fun verify(verifyRequest: VerifyRequest): Flowable<VerifyResponse> {
        return applicationService.verify(verifyRequest)
    }
}