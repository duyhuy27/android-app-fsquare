package vn.md18.fsquareapplication.features.checkout.viewmodel

import vn.md18.fsquareapplication.core.base.BaseViewModel
import vn.md18.fsquareapplication.features.auth.repository.AuthRepository
import vn.md18.fsquareapplication.utils.extensions.NetworkExtensions
import javax.inject.Inject

class CheckoutViewmodel @Inject constructor(
    private val authRepository: AuthRepository,
    private val networkExtension : NetworkExtensions,
) : BaseViewModel(){
    override fun onDidBindViewModel() {
        TODO("Not yet implemented")
    }
}