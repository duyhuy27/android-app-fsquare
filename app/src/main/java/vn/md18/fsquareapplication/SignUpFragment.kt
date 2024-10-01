package vn.md18.fsquareapplication

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import vn.md18.fsquareapplication.core.base.BaseFragment
import vn.md18.fsquareapplication.data.model.DataState
import vn.md18.fsquareapplication.databinding.FragmentLoginBinding
import vn.md18.fsquareapplication.databinding.FragmentOtpBinding
import vn.md18.fsquareapplication.databinding.FragmentSignUpBinding
import vn.md18.fsquareapplication.features.auth.viewmodel.AuthViewModel

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding, AuthViewModel>() {
    override val viewModel: AuthViewModel by viewModels()
    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentSignUpBinding = FragmentSignUpBinding.inflate(layoutInflater)

    override fun getTagFragment(): String {
        TODO("Not yet implemented")
    }

    override fun onViewLoaded() {
        TODO("Not yet implemented")
    }

    override fun addViewListener() {
//        binding.btnSubmit.setOnClickListener {
//            val email = binding.edEmail.text.toString().trim()
//            if (isValidEmail(email)) {
//                // Email hợp lệ, tiến hành đăng ký
//                signUp(email)
//            } else {
//                // Email không hợp lệ, hiển thị thông báo lỗi
//                binding.edEmail.error = "Vui lòng nhập email hợp lệ"
//                Toast.makeText(requireContext(), "Email không hợp lệ. Vui lòng kiểm tra lại.", Toast.LENGTH_SHORT).show()
//            }
//        }
    }

    override fun addDataObserver() {
        viewModel.apply {
            signUpState.observe(this@SignUpFragment) {
                data ->
                when(data){


                    is DataState.Error -> TODO()
                    DataState.Loading -> TODO()
                    is DataState.Success -> {
//                        val email = binding.edEmail.text.toString()
//                        navigateToVerifyOtpFragment("signup", email)
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