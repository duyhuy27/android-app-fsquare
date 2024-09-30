package vn.md18.fsquareapplication.features.auth.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.core.base.BaseActivity
import vn.md18.fsquareapplication.databinding.ActivitySignUpBinding
import vn.md18.fsquareapplication.features.auth.viewmodel.AuthViewModel
import vn.md18.fsquareapplication.utils.extensions.safeClickWithRx
import vn.md18.fsquareapplication.utils.extensions.setOnSafeClickListener
import vn.md18.fsquareapplication.utils.fslogger.FSLogger
import java.sql.Time

@AndroidEntryPoint
class SignUpActivity : BaseActivity<ActivitySignUpBinding, AuthViewModel>() {

    override val viewModel: AuthViewModel by viewModels()
    override fun inflateLayout(layoutInflater: LayoutInflater): ActivitySignUpBinding = ActivitySignUpBinding.inflate(layoutInflater)

    override fun onViewLoaded() {
       Log.e("Huynd","Huynd: Phi vao khi man hinh khoi tao")
        FSLogger.e("Huynd: hehe")
    }

    override fun addViewListener() {
        binding.btnSubmit.setOnClickListener {
            viewModel.signUp(binding.edtInout.getText())
        }
    }

    override fun addDataObserver() {
        super.addDataObserver()
//        viewModel.apply {
//            loginState.observe(this@SignUpActivity) {
//                data ->
//                Toast.makeText(this@SignUpActivity, "Gui otp thanh cong cho email $data", Toast.LENGTH_LONG).show()
//                Timber.e("Huynd: ")
//            }
//        }
    }
}