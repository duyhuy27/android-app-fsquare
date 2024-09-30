package vn.md18.fsquareapplication.data.network.retrofit

import io.reactivex.Flowable
import retrofit2.http.Body
import retrofit2.http.POST
import vn.md18.fsquareapplication.data.network.AppAPi
import vn.md18.fsquareapplication.data.network.model.request.SignUpRequest

interface ApplicationService {
    @POST(AppAPi.AUTH_SIGNUP)
    fun signUp(
        @Body signUpRequest: SignUpRequest
    ) : Flowable<SignUpRequest>
}