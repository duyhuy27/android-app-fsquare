package vn.md18.fsquareapplication.data.network.retrofit

import io.reactivex.Flowable
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import vn.md18.fsquareapplication.data.network.AppAPi
import vn.md18.fsquareapplication.data.network.model.request.LoginRequest
import vn.md18.fsquareapplication.data.network.model.request.SignUpRequest
import vn.md18.fsquareapplication.data.network.model.request.VerifyRequest
import vn.md18.fsquareapplication.data.network.model.response.LoginResponse
import vn.md18.fsquareapplication.data.network.model.response.SignUpResponse
import vn.md18.fsquareapplication.data.network.model.response.VerifyResponse

interface ApplicationService {

    // auth
    @POST(AppAPi.AUTH_SIGNUP)
    fun signUp(
        @Body signUpRequest: SignUpRequest
    ) : Flowable<SignUpResponse>


    @POST(AppAPi.AUTH_LOGIN)
    fun login(
        @Body loginRequest: LoginRequest
    ) : Flowable<LoginResponse>

    @POST(AppAPi.AUTH_VERIFY_OTP)
    fun verify(
        @Body verifyRequest: VerifyRequest
    ) : Flowable<VerifyResponse>

    //get country name

}