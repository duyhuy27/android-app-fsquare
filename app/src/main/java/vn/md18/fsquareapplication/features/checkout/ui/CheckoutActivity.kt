package vn.md18.fsquareapplication.features.checkout.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.core.base.BaseActivity
import vn.md18.fsquareapplication.databinding.ActivityCartBinding
import vn.md18.fsquareapplication.databinding.ActivityProfileAndSettingBinding
import vn.md18.fsquareapplication.features.main.viewmodel.MainViewModel
import vn.md18.fsquareapplication.features.profileandsetting.viewmodel.ProfileViewModel
import vn.md18.fsquareapplication.utils.Constant

@AndroidEntryPoint
class CheckoutActivity : BaseActivity<ActivityCartBinding, MainViewModel>() {
    override val viewModel: MainViewModel by viewModels()
    private lateinit var navController: NavController
    private var receivedKey: String? = null
    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityCartBinding {
        return ActivityCartBinding.inflate(layoutInflater)
    }

    override fun onViewLoaded() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.checkout_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        receivedKey?.let { navController(it) }
    }

    override fun addViewListener() {

    }

    private fun navController(screen: String){
        when(screen){
            Constant.KEY_EDIT_PROFILE -> navController.navigate(R.id.editProfileFragment)
            Constant.KEY_ADDRESS -> navController.navigate(R.id.addressFragment)
            Constant.KEY_NEW_ADDRESS -> navController.navigate(R.id.newAddressFragment)
            Constant.KEY_PAYMENT -> navController.navigate(R.id.paymentFragment)
            Constant.KEY_NOTIFICATION -> navController.navigate(R.id.notificationFragment)
            Constant.KEY_SECURITY -> navController.navigate(R.id.securityFragment)
            Constant.KEY_LANGUAGE -> navController.navigate(R.id.languageFragment)
            Constant.KEY_POLICY -> navController.navigate(R.id.privacyPolicyFragment)
        }
    }

}