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
import vn.md18.fsquareapplication.databinding.FragmentLoginWithEmailBinding
import vn.md18.fsquareapplication.features.auth.viewmodel.AuthViewModel
@AndroidEntryPoint
class LoginWithEmailFragment : BaseFragment<FragmentLoginWithEmailBinding, AuthViewModel>() {
    override val viewModel: AuthViewModel by viewModels()
    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentLoginWithEmailBinding = FragmentLoginWithEmailBinding.inflate(layoutInflater)

    override fun getTagFragment(): String {
        return "LoginWithEmailFragment"
    }

    override fun onViewLoaded() {
        Log.d("auth", "da vao man auth login with email")
    }

    override fun addViewListener() {
        binding.btnSubmit.setOnClickListener {
            val email = binding.edtInout.text.toString().trim()
            if (isValidEmail(email)) {
                login(email)
            } else {
                binding.edtInout.error = "Vui lòng nhập email hợp lệ"
                Toast.makeText(requireContext(), "Email không hợp lệ. Vui lòng kiểm tra lại.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun addDataObserver() {
        viewModel.apply {
            loginState.observe(this@LoginWithEmailFragment) {
                data ->
                when(data){
                    is DataState.Error -> TODO()
                    DataState.Loading -> TODO()
                    is DataState.Success -> {
                        val email = binding.edtInout.text.toString()
                        navigateToVerifyOtpFragment("login", email)
                    }
                }
            }
        }
    }

    private fun login(email: String){
        viewModel.login(email)
    }

    private fun navigateToVerifyOtpFragment(type: String, email: String){
        val bundle = Bundle().apply {
            putString("type", type)
            putString("email", email)
        }
        findNavController().navigate(R.id.action_loginWithEmailFragment_to_otpFragment, bundle)
    }

    private fun isValidEmail(email: String?): Boolean {
        return !email.isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

}