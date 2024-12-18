package vn.md18.fsquareapplication.features.auth.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import vn.md18.fsquareapplication.core.base.BaseFragment
import vn.md18.fsquareapplication.databinding.FragmentOtpBinding
import vn.md18.fsquareapplication.databinding.FragmentSuccessfullyCreateAccountBinding
import vn.md18.fsquareapplication.features.auth.viewmodel.AuthViewModel
import vn.md18.fsquareapplication.features.main.ui.MainActivity
import vn.md18.fsquareapplication.utils.Constant

@AndroidEntryPoint
class SuccessfullyCreateAccountFragment : BaseFragment<FragmentSuccessfullyCreateAccountBinding, AuthViewModel>() {
    override val viewModel: AuthViewModel by viewModels()

    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentSuccessfullyCreateAccountBinding = FragmentSuccessfullyCreateAccountBinding.inflate(layoutInflater)

    override fun getTagFragment(): String {
        return "SuccessfullyFragment"
    }

    override fun onViewLoaded() {

    }

    override fun addViewListener() {
        binding.btnSubmit.setOnClickListener{
            navigateToHomePage()
        }
    }

    override fun addDataObserver() {

    }

    private fun navigateToHomePage() {
        val bundle = Bundle().apply {
            putBoolean(Constant.KEY_SEND_TOKEN_FIREBASE_TO_BE, true)
        }
        openActivity(MainActivity::class.java, bundle)
        requireActivity().finish()
    }


}