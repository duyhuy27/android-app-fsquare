package vn.md18.fsquareapplication.features.auth.repository

import io.reactivex.Flowable
import vn.md18.fsquareapplication.data.network.model.request.SignUpRequest

interface AuthRepository {
    fun signUp(signUpRequest: SignUpRequest) : Flowable<SignUpRequest>
}