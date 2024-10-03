package vn.md18.fsquareapplication.features.auth.ui.fragment

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.core.base.BaseFragment
import vn.md18.fsquareapplication.data.model.DataState
import vn.md18.fsquareapplication.databinding.FragmentSignUpBinding
import vn.md18.fsquareapplication.features.auth.viewmodel.AuthViewModel
import vn.md18.fsquareapplication.utils.Constant
import vn.md18.fsquareapplication.utils.extensions.showCustomToast

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding, AuthViewModel>() {
    override val viewModel: AuthViewModel by viewModels()
    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentSignUpBinding = FragmentSignUpBinding.inflate(layoutInflater)

    override fun getTagFragment(): String {
        return "SignUpFragment"
    }

    override fun onViewLoaded() {

    }

    override fun addViewListener() {
        binding.btnSubmit.setOnClickListener {
            val email = binding.edtInout.getText()
            if (isValidEmail(email)) {
                signUp(email)
            } else {
                activity?.showCustomToast(R.string.err_validate_email.toString(), Constant.ToastStatus.FAILURE)
            }
        }
    }

    override fun addDataObserver() {
        viewModel.apply {
            signUpState.observe(this@SignUpFragment) {
                data ->
                when(data){
                    is DataState.Error -> {
                        activity?.showCustomToast(R.string.err_signup.toString(), Constant.ToastStatus.FAILURE)
                    }
                    DataState.Loading -> {

                    }
                    is DataState.Success -> {
                        val email = binding.edtInout.getText()
                        navigateToVerifyOtpFragment(Constant.KEY_SIGNUP, email)
                    }
                }
            }
        }
    }

    private fun signUp(email: String){
        viewModel.signUp(email)
    }

    private fun navigateToVerifyOtpFragment(type: String, email: String){
        val bundle = Bundle().apply {
            putString(Constant.KEY_TYPE, type)
            putString(Constant.KEY_EMAIL, email)
        }
        findNavController().navigate(R.id.action_signUpFragment_to_otpFragment, bundle)
    }

    private fun isValidEmail(email: String?): Boolean {
        return !email.isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}