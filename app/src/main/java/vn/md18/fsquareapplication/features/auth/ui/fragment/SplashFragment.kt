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
import vn.md18.fsquareapplication.databinding.FragmentSplashBinding
import vn.md18.fsquareapplication.features.auth.viewmodel.AuthViewModel
import vn.md18.fsquareapplication.utils.extensions.NetworkExtensions
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding, AuthViewModel>() {
    @Inject
    lateinit var networkExtensions: NetworkExtensions

    override val viewModel: AuthViewModel by viewModels()

    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentSplashBinding = FragmentSplashBinding.inflate(layoutInflater)

    override fun getTagFragment(): String {
        return "SplashFragment"
    }

    override fun onViewLoaded() {
        Log.d("auth", "da vao man auth splash")
        checkInternetAndNavigate()
    }

    override fun addViewListener() {

    }

    override fun addDataObserver() {

    }

    private fun checkInternetAndNavigate() {
//        networkExtensions.checkInternet { isConnected ->
//            if (isConnected) {
//                lifecycleScope.launch {
//                    delay(3000)
//                    navigateToLoadingFragment()
//                }
//            } else {
//                Log.e("phuczk", "no internet connection")
//            }
//        }

        lifecycleScope.launch {
            delay(3000)
            navigateToLoadingFragment()
        }
    }

    private fun navigateToLoadingFragment() {
        findNavController().navigate(R.id.action_splashFragment_to_loadingFragment)
    }


}