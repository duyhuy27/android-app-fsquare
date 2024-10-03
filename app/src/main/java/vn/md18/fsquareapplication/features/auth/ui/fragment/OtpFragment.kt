package vn.md18.fsquareapplication.features.auth.ui.fragment

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
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
        return "OTPFragment"
    }

    override fun onViewLoaded() {
        Log.d("auth", "da vao man auth otp")
        arguments?.let {
            type = it.getString("type", "login")
            email = it.getString("email", "")
        }

        if(type.equals("login")){
            binding.titleOtpFragment.setText("Login")
        }
    }

    override fun addViewListener() {
        val otpView = binding.edtOTP
        // Thêm listener để theo dõi sự thay đổi OTP
        otpView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d("OTP", "OTP nhập vào: $s")
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.btnVerify.setOnClickListener {
            val otp = getOtpFromView(otpView).toString()
            if (isValidOtp(otp)) {
                verifyOtp(otp, email, type)
            } else {
                // Hiển thị thông báo lỗi nếu OTP không hợp lệ
                Toast.makeText(requireContext(), "OTP không hợp lệ. Vui lòng nhập lại.", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun addDataObserver() {
        viewModel.apply {
            verifyState.observe(this@OtpFragment) {
                data ->
                when(data){
                    is DataState.Error -> {

                    }
                    DataState.Loading -> {

                    }
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
        findNavController().navigate(R.id.action_otpFragment_to_successfullyCreateAccountFragment)
    }

    private fun isValidOtp(otp: String): Boolean {
        return !otp.isNullOrEmpty() && otp.length == 4
    }
}
