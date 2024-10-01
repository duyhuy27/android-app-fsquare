package vn.md18.fsquareapplication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import vn.md18.fsquareapplication.core.base.BaseFragment
import vn.md18.fsquareapplication.databinding.ActivitySignUpBinding
import vn.md18.fsquareapplication.databinding.FragmentLoadingBinding
import vn.md18.fsquareapplication.databinding.FragmentLoginBinding
import vn.md18.fsquareapplication.features.auth.viewmodel.AuthViewModel
import vn.md18.fsquareapplication.utils.extensions.NetworkExtensions
import javax.inject.Inject

class LoadingFragment() : BaseFragment<FragmentLoadingBinding, AuthViewModel>() {

    @Inject
    lateinit var networkExtensions: NetworkExtensions

    override val viewModel: AuthViewModel by viewModels()
    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentLoadingBinding = FragmentLoadingBinding.inflate(layoutInflater)

    override fun getTagFragment(): String {
        TODO("Not yet implemented")
    }

    override fun onViewLoaded() {
        checkInternetAndNavigate()
    }

    override fun addViewListener() {
        TODO("Not yet implemented")
    }

    override fun addDataObserver() {
        TODO("Not yet implemented")
    }

    private fun checkInternetAndNavigate() {
        networkExtensions.checkInternet { isConnected ->
            if (isConnected) {
                lifecycleScope.launch {
                    delay(3000)
                    navigateToLoadingFragment()
                }
            } else {
                Log.e("phuczk", "no internet connection")
            }
        }
    }

    private fun navigateToLoadingFragment() {
        findNavController().navigate(R.id.action_loadingFragment_to_loginFragment)
    }


}