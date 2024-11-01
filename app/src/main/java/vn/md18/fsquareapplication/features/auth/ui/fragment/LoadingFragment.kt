package vn.md18.fsquareapplication.features.auth.ui.fragment

import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.core.base.BaseFragment
import vn.md18.fsquareapplication.databinding.FragmentLoadingBinding
import vn.md18.fsquareapplication.features.auth.viewmodel.AuthViewModel
import vn.md18.fsquareapplication.features.main.ui.MainActivity
import vn.md18.fsquareapplication.utils.extensions.NetworkExtensions
import vn.md18.fsquareapplication.utils.fslogger.FSLogger
import javax.inject.Inject

@AndroidEntryPoint
class LoadingFragment() : BaseFragment<FragmentLoadingBinding, AuthViewModel>() {

    @Inject
    lateinit var networkExtensions: NetworkExtensions

    override val viewModel: AuthViewModel by viewModels()
    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentLoadingBinding =
        FragmentLoadingBinding.inflate(layoutInflater)

    override fun getTagFragment(): String {
        return "LoadingFragment"
    }

    override fun onViewLoaded() {
        checkInternetAndNavigate()
        FSLogger.e("Get token: ${dataManager.getToken()}")
    }

    override fun addViewListener() {

    }

    override fun addDataObserver() {

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
//        lifecycleScope.launch {
//            if (dataManager.getToken() != null) {
//                FSLogger.e("day la token: ${dataManager.getToken()}")
//                openActivity(MainActivity::class.java)
//            } else {
//                delay(3000)
//                navigateToLoadingFragment()
//            }
//        }
    }

    private fun navigateToLoadingFragment() {
        findNavController().navigate(R.id.action_loadingFragment_to_loginFragment)
    }


}