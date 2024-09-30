package vn.md18.fsquareapplication.features.auth.repository

import io.reactivex.Flowable
import vn.md18.fsquareapplication.data.network.model.request.SignUpRequest
import vn.md18.fsquareapplication.data.network.retrofit.ApplicationService
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val applicationService: ApplicationService
)  : AuthRepository {

    override fun signUp(signUpRequest: SignUpRequest): Flowable<SignUpRequest> {
        return applicationService.signUp(signUpRequest)
    }
}