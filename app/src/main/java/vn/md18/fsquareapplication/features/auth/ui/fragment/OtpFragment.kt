package vn.md18.fsquareapplication.features.auth.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
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
import vn.md18.fsquareapplication.features.main.ui.MainActivity

@AndroidEntryPoint
class OtpFragment : BaseFragment<FragmentOtpBinding, AuthViewModel>() {

    private lateinit var type: String
    private lateinit var email: String

    private var countDownTimer: CountDownTimer? = null
    private var timeLeftInMillis: Long = 60000 // 60 giây

    override val viewModel: AuthViewModel by viewModels()

    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentOtpBinding = FragmentOtpBinding.inflate(layoutInflater)

    override fun getTagFragment(): String {
        return "OTPFragment"
    }

    override fun onViewLoaded() {
        Log.d("auth", "Đã vào màn auth OTP")
        arguments?.let {
            type = it.getString("type", "")
            email = it.getString("email", "")
        }

        if (type.equals("login", ignoreCase = true)) {
            binding.titleOtpFragment.text = getString(R.string.login)
        }
        startCountDown()
    }

    override fun addViewListener() {
        val otpView = binding.edtOTP
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
                Toast.makeText(requireContext(), "OTP không hợp lệ. Vui lòng nhập lại.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.txtTime.setOnClickListener {
            if (binding.txtTime.tag == "resend") {
                resendOtp()
            }
        }
    }

    override fun addDataObserver() {
        viewModel.apply {
            verifyState.observe(this@OtpFragment) { data ->
                when (data) {
                    is DataState.Error -> {
                        Toast.makeText(requireContext(), "OTP không đúng hoặc đã hết hạn", Toast.LENGTH_SHORT).show()
                    }
                    DataState.Loading -> {
                        // Có thể hiển thị loading nếu cần
                    }
                    is DataState.Success -> {
                        if(type.equals("signup", ignoreCase = true)){
                            navigateToSuccessfullyFragment()
                        } else {
                            navigateToHomePage()
                        }
                    }
                }
            }

            loginState.observe(this@OtpFragment) { data ->
                when (data) {
                    is DataState.Error -> {
                        Toast.makeText(requireContext(), "Không thể gửi lại OTP", Toast.LENGTH_SHORT).show()
                    }
                    DataState.Loading -> {
                        // Có thể hiển thị loading nếu cần
                    }
                    is DataState.Success -> {
                        Toast.makeText(requireContext(), "OTP đã được gửi lại.", Toast.LENGTH_SHORT).show()
                        timeLeftInMillis = 60000 // Đặt lại thời gian đếm ngược
                        startCountDown() // Chỉ gọi ở đây
                    }
                }
            }

            signUpState.observe(this@OtpFragment) { data ->
                when (data) {
                    is DataState.Error -> {
                        Toast.makeText(requireContext(), "Không thể gửi lại OTP", Toast.LENGTH_SHORT).show()
                    }
                    DataState.Loading -> {
                        // Có thể hiển thị loading nếu cần
                    }
                    is DataState.Success -> {
                        Toast.makeText(requireContext(), "OTP đã được gửi lại.", Toast.LENGTH_SHORT).show()
                        timeLeftInMillis = 60000 // Đặt lại thời gian đếm ngược
                        startCountDown() // Chỉ gọi ở đây
                    }
                }
            }
        }
    }

    private fun getOtpFromView(otpView: OtpView): Editable? {
        return otpView.getText()
    }

    private fun verifyOtp(otp: String, email: String, type: String) {
        viewModel.verify(otp, email, type, "")
    }

    private fun navigateToSuccessfullyFragment() {
        findNavController().navigate(R.id.action_otpFragment_to_successfullyCreateAccountFragment)
    }

    private fun navigateToHomePage() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun isValidOtp(otp: String): Boolean {
        return otp.isNotEmpty() && otp.length == 4
    }

    private fun startCountDown() {
        // Hủy bỏ timer hiện tại nếu có
        countDownTimer?.cancel()

        binding.txtTime.tag = "countdown"
        updateCountDownText()

        countDownTimer = object : CountDownTimer(timeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                updateCountDownText()
            }

            override fun onFinish() {
                binding.txtTime.text = getString(R.string.resend)
                binding.txtTime.tag = "resend" // Đặt tag để biết có thể resend
            }
        }.start()
    }

    private fun updateCountDownText() {
        val seconds = (timeLeftInMillis / 1000).toInt()
        binding.txtTime.text = "$seconds s"
    }

    private fun stopCountDown() {
        countDownTimer?.cancel()
        countDownTimer = null
    }

    private fun resendOtp() {
        if(type.equals("login", ignoreCase = true)){
            viewModel.login(email)
        } else {
            viewModel.signUp(email)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        stopCountDown()
    }
}
