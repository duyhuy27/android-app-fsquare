package vn.md18.fsquareapplication.features.auth.ui.fragment

import android.text.Editable
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import vn.md18.fsquareapplication.databinding.FragmentOtpBinding
import com.mukesh.OtpView
import dagger.hilt.android.AndroidEntryPoint
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.core.base.BaseFragment
import vn.md18.fsquareapplication.data.model.DataState
import vn.md18.fsquareapplication.features.auth.viewmodel.AuthViewModel

@AndroidEntryPoint
class OtpFragment : BaseFragment<FragmentOtpBinding, AuthViewModel>() {

    private lateinit var type: String
    private lateinit var email: String

    override val viewModel: AuthViewModel by viewModels()

    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentOtpBinding = FragmentOtpBinding.inflate(layoutInflater)

    override fun getTagFragment(): String {
        TODO("Not yet implemented")
    }

    override fun onViewLoaded() {
        arguments?.let {
            type = it.getString("type", "login")
            email = it.getString("email", "")
        }
    }

    override fun addViewListener() {
        val otpView = binding.edtOTP
        binding.btnVerify.setOnClickListener {
            val otp = getOtpFromView(otpView).toString()
            if (isValidOtp(otp)) {
                verifyOtp(otp, email, type)
            } else {

            }
        }
    }

    override fun addDataObserver() {
        viewModel.apply {
            verifyState.observe(this@OtpFragment) {
                data ->
                when(data){
                    is DataState.Error -> TODO()
                    DataState.Loading -> TODO()
                    is DataState.Success -> {
                        navigateToSuccessfullyFragment()
                    }
                }
            }
        }
    }

    private fun getOtpFromView(otpView: OtpView): Editable? {
        return otpView.getText()
    }

    private fun verifyOtp(otp: String, emal: String, type: String) {
        viewModel.verify(otp, emal, type, "")
    }

    private fun navigateToSuccessfullyFragment(){
        findNavController().navigate(R.id.action_signUpFragment_to_otpFragment)
    }

    private fun isValidOtp(otp: String): Boolean {
        return !otp.isNullOrEmpty() && otp.length == 4
    }
}
