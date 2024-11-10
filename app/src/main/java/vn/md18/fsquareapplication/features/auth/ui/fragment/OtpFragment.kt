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
import vn.md18.fsquareapplication.utils.Constant
import vn.md18.fsquareapplication.utils.extensions.showCustomToast

@AndroidEntryPoint
class OtpFragment : BaseFragment<FragmentOtpBinding, AuthViewModel>() {

    private lateinit var type: String
    private lateinit var email: String

    private var countDownTimer: CountDownTimer? = null
    private var timeLeftInMillis: Long = 60000

    override val viewModel: AuthViewModel by viewModels()

    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentOtpBinding = FragmentOtpBinding.inflate(layoutInflater)

    override fun getTagFragment(): String {
        return "OTPFragment"
    }

    override fun onViewLoaded() {
        arguments?.let {
            type = it.getString(Constant.KEY_TYPE, "")
            email = it.getString(Constant.KEY_EMAIL, "")
        }

        if (type.equals(Constant.KEY_LOGIN, ignoreCase = true)) {
            binding.titleOtpFragment.text = getString(R.string.login)
        }
        startCountDown()
    }

    override fun addViewListener() {
        val otpView = binding.edtOTP

        binding.btnVerify.setOnClickListener {
            val otp = getOtpFromView(otpView).toString()
            if (isValidOtp(otp)) {
                verifyOtp(otp, email, type)
            } else {
                activity?.showCustomToast(getString(R.string.err_otp), Constant.ToastStatus.FAILURE)
            }
        }

        binding.txtTime.setOnClickListener {
            if (binding.txtTime.tag == "resend") {
                resendOtp()
            }
        }

        binding.toolbarOTP.onClickBackPress = {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun addDataObserver() {
        viewModel.apply {
            verifyState.observe(this@OtpFragment) { data ->
                when (data) {
                    is DataState.Error -> {
                        activity?.showCustomToast(getString(R.string.err_verify), Constant.ToastStatus.FAILURE)
                    }
                    DataState.Loading -> {
                    }
                    is DataState.Success -> {
                        data.data?.let { token ->
                            dataManager.setToken(token.toString())
                        }
                        if(type.equals(Constant.KEY_SIGNUP, ignoreCase = true)){
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
                        activity?.showCustomToast(getString(R.string.err_verify), Constant.ToastStatus.FAILURE)
                    }
                    DataState.Loading -> {

                    }
                    is DataState.Success -> {
                        activity?.showCustomToast(getString(R.string.resend_otp_success), Constant.ToastStatus.SUCCESS)
                        timeLeftInMillis = 60000
                        startCountDown()
                    }
                }
            }

            signUpState.observe(this@OtpFragment) { data ->
                when (data) {
                    is DataState.Error -> {
                        activity?.showCustomToast(getString(R.string.err_otp), Constant.ToastStatus.FAILURE)
                    }
                    DataState.Loading -> {
                    }
                    is DataState.Success -> {
                        activity?.showCustomToast(getString(R.string.resend_otp_success), Constant.ToastStatus.SUCCESS)
                        timeLeftInMillis = 60000
                        startCountDown()
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
                binding.txtTime.tag = "resend"
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
        if(type.equals(Constant.KEY_LOGIN, ignoreCase = true)){
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
