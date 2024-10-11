package vn.md18.fsquareapplication.features.auth.ui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.core.base.BaseActivity
import vn.md18.fsquareapplication.databinding.ActivityAuthBinding
import vn.md18.fsquareapplication.databinding.ActivitySignUpBinding
import vn.md18.fsquareapplication.features.auth.viewmodel.AuthViewModel
import vn.md18.fsquareapplication.features.profileandsetting.viewmodel.ProfileViewModel
import javax.inject.Inject

@AndroidEntryPoint
class SignUpActivity() : BaseActivity<ActivitySignUpBinding, ProfileViewModel>() {

    override val viewModel: ProfileViewModel by viewModels()
    override fun inflateLayout(layoutInflater: LayoutInflater): ActivitySignUpBinding {
        return ActivitySignUpBinding.inflate(layoutInflater)
    }

    override fun onViewLoaded() {

    }

    override fun addViewListener() {

    }


}
