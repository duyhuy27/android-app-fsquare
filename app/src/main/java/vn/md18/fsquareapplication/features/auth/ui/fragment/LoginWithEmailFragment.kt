package vn.md18.fsquareapplication.features.auth.ui.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.core.base.BaseFragment
import vn.md18.fsquareapplication.data.model.DataState
import vn.md18.fsquareapplication.databinding.FragmentLoginWithEmailBinding
import vn.md18.fsquareapplication.features.auth.viewmodel.AuthViewModel
import vn.md18.fsquareapplication.utils.Constant
import vn.md18.fsquareapplication.utils.extensions.showCustomToast

@AndroidEntryPoint
class LoginWithEmailFragment : BaseFragment<FragmentLoginWithEmailBinding, AuthViewModel>() {
    override val viewModel: AuthViewModel by viewModels()
    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentLoginWithEmailBinding = FragmentLoginWithEmailBinding.inflate(layoutInflater)
    override fun getTagFragment(): String {
        return "LoginWithEmailFragment"
    }

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
    }

    override fun onViewLoaded() {
        // Kiểm tra và tự động điền email nếu đã lưu trước đó
        val savedEmail = sharedPreferences.getString("saved_email", "")
        if (!savedEmail.isNullOrEmpty()) {
            binding.edtInout.setText(savedEmail)
            binding.chkSave.isChecked = true
        }
    }

    override fun addViewListener() {
        binding.btnSubmit.setOnClickListener {
            val email = binding.edtInout.getText()
            if (isValidEmail(email)) {
                login(email)
            } else {
                activity?.showCustomToast(getString(R.string.err_validate_email), Constant.ToastStatus.FAILURE)
            }
        }
    }

    override fun addDataObserver() {
        viewModel.apply {
            loginState.observe(this@LoginWithEmailFragment) { data ->
                when(data){
                    is DataState.Error -> {
                        activity?.showCustomToast(getString(R.string.err_login), Constant.ToastStatus.FAILURE)
                    }
                    DataState.Loading -> {
                        // Handle loading state if necessary
                    }
                    is DataState.Success -> {
                        val email = binding.edtInout.getText()
                        if (binding.chkSave.isChecked) {
                            sharedPreferences.edit().putString("saved_email", email).apply()
                        } else {
                            sharedPreferences.edit().remove("saved_email").apply()
                        }

                        navigateToVerifyOtpFragment(Constant.KEY_LOGIN, email)
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
            putString(Constant.KEY_TYPE, type)
            putString(Constant.KEY_EMAIL, email)
        }
        findNavController().navigate(R.id.action_loginWithEmailFragment_to_otpFragment, bundle)
    }

    private fun isValidEmail(email: String?): Boolean {
        return !email.isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}