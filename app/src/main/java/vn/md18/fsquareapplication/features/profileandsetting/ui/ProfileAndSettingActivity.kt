package vn.md18.fsquareapplication.features.profileandsetting.ui

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
import vn.md18.fsquareapplication.databinding.ActivityAuthBinding
import vn.md18.fsquareapplication.databinding.ActivityProfileAndSettingBinding
import vn.md18.fsquareapplication.features.auth.viewmodel.AuthViewModel
import vn.md18.fsquareapplication.features.profileandsetting.viewmodel.ProfileViewModel

@AndroidEntryPoint
class ProfileAndSettingActivity : BaseActivity<ActivityProfileAndSettingBinding, ProfileViewModel>() {

    override val viewModel: ProfileViewModel by viewModels()
    private lateinit var navController: NavController
    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityProfileAndSettingBinding {
        return ActivityProfileAndSettingBinding.inflate(layoutInflater)
    }

    override fun onViewLoaded() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.profile_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    override fun addViewListener() {

    }

}