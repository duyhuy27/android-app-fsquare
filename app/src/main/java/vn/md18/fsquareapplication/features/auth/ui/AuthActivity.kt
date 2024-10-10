package vn.md18.fsquareapplication.features.auth.ui

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.core.base.BaseActivity
import vn.md18.fsquareapplication.databinding.ActivityAuthBinding
import vn.md18.fsquareapplication.features.auth.viewmodel.AuthViewModel

@AndroidEntryPoint
class AuthActivity : BaseActivity<ActivityAuthBinding, AuthViewModel>() {

    override val viewModel: AuthViewModel by viewModels()
    private lateinit var navController: NavController
    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityAuthBinding {
        return ActivityAuthBinding.inflate(layoutInflater)
    }

    override fun onViewLoaded() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.auth_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    override fun addViewListener() {

    }



    override fun onBackPressed() {
        if (!navController.popBackStack()) {
            super.onBackPressed()
        }
    }



}