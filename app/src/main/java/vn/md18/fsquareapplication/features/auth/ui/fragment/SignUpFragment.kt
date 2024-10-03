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

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding, AuthViewModel>() {
    override val viewModel: AuthViewModel by viewModels()
    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentSignUpBinding = FragmentSignUpBinding.inflate(layoutInflater)

    override fun getTagFragment(): String {
        return "SignUpFragment"
    }

    override fun onViewLoaded() {
        Log.d("auth", "da vao man auth signup")
    }

    override fun addViewListener() {
        binding.btnSubmit.setOnClickListener {
            val email = binding.edtInout.text.toString().trim()
            if (isValidEmail(email)) {
                signUp(email)
            } else {
                binding.edtInout.error = "Vui lòng nhập email hợp lệ"
                Toast.makeText(requireContext(), "Email không hợp lệ. Vui lòng kiểm tra lại.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun addDataObserver() {
        viewModel.apply {
            signUpState.observe(this@SignUpFragment) {
                data ->
                when(data){
                    is DataState.Error -> {
                        Toast.makeText(requireContext(), "Email duoc dang ky hoac khong ton tai", Toast.LENGTH_SHORT).show()
                    }
                    DataState.Loading -> {

                    }
                    is DataState.Success -> {
                        val email = binding.edtInout.text.toString()
                        navigateToVerifyOtpFragment("signup", email)
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
            putString("type", type)
            putString("email", email)
        }
        findNavController().navigate(R.id.action_signUpFragment_to_otpFragment, bundle)
    }

    private fun isValidEmail(email: String?): Boolean {
        return !email.isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}