package vn.md18.fsquareapplication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import vn.md18.fsquareapplication.core.base.BaseFragment
import vn.md18.fsquareapplication.databinding.FragmentLoadingBinding
import vn.md18.fsquareapplication.databinding.FragmentSplashBinding
import vn.md18.fsquareapplication.features.auth.viewmodel.AuthViewModel
import vn.md18.fsquareapplication.utils.extensions.NetworkExtensions
import javax.inject.Inject


class SplashFragment : BaseFragment<FragmentSplashBinding, AuthViewModel>() {
    @Inject
    lateinit var networkExtensions: NetworkExtensions

    override val viewModel: AuthViewModel by viewModels()

    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentSplashBinding = FragmentSplashBinding.inflate(layoutInflater)

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
        findNavController().navigate(R.id.action_splashFragment_to_loadingFragment)
    }


}