package vn.md18.fsquareapplication.features.profileandsetting.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.core.base.BaseActivity
import vn.md18.fsquareapplication.databinding.ActivityProfileAndSettingBinding
import vn.md18.fsquareapplication.features.profileandsetting.viewmodel.ProfileViewModel
import vn.md18.fsquareapplication.utils.Constant

@AndroidEntryPoint
class ProfileAndSettingActivity : BaseActivity<ActivityProfileAndSettingBinding, ProfileViewModel>() {

    override val viewModel: ProfileViewModel by viewModels()
    private lateinit var navController: NavController
    private var receivedKey: String? = null
    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityProfileAndSettingBinding {
        return ActivityProfileAndSettingBinding.inflate(layoutInflater)
    }

    override fun onViewLoaded() {

        receivedKey = intent.getStringExtra("STATUS_KEY")
        val dataBundle = intent.getBundleExtra("DATA_BUNDLE")

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.profile_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        receivedKey?.let { navController(it, dataBundle)}
    }

    override fun addViewListener() {

    }

    private fun navController(screen: String, dataBundle: Bundle?){
        when(screen){
            Constant.KEY_EDIT_PROFILE -> navController.navigate(R.id.editProfileFragment)
            Constant.KEY_ADDRESS -> navController.navigate(R.id.addressFragment)
            Constant.KEY_NEW_ADDRESS -> navController.navigate(R.id.newAddressFragment, dataBundle)
            Constant.KEY_PAYMENT -> navController.navigate(R.id.paymentFragment)
            Constant.KEY_NOTIFICATION -> navController.navigate(R.id.notificationFragment)
            Constant.KEY_SECURITY -> navController.navigate(R.id.securityFragment)
            Constant.KEY_LANGUAGE -> navController.navigate(R.id.languageFragment)
            Constant.KEY_POLICY -> navController.navigate(R.id.privacyPolicyFragment)
            Constant.KEY_CONTACT -> navController.navigate(R.id.contactFragment)
        }
    }

}