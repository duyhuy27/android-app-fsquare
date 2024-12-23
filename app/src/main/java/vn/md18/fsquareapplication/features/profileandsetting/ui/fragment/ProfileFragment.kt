package vn.md18.fsquareapplication.features.profileandsetting.ui.fragment
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.hbb20.CountryPickerView
import com.hbb20.countrypicker.models.CPCountry
import dagger.hilt.android.AndroidEntryPoint
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.core.base.BaseFragment
import vn.md18.fsquareapplication.databinding.FragmentProfileBinding
import vn.md18.fsquareapplication.features.auth.ui.fragment.LoginFragment
import vn.md18.fsquareapplication.features.profileandsetting.ui.ProfileAndSettingActivity
import vn.md18.fsquareapplication.features.profileandsetting.viewmodel.ProfileViewModel
import vn.md18.fsquareapplication.utils.Constant
import vn.md18.fsquareapplication.utils.extensions.showCustomToast

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>() {

    override val viewModel: ProfileViewModel by viewModels()


    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentProfileBinding = FragmentProfileBinding.inflate(layoutInflater)

    override fun getTagFragment(): String {
        return "ProfileFragment"
    }

    override fun onViewLoaded() {
    }

    override fun addViewListener() {

        binding.btnAddress.setOnClickListener { Toast.makeText(requireContext(), "hihi1", Toast.LENGTH_SHORT).show() }
        binding.btnProfile.setOnClickListener { Toast.makeText(context, "hihi", Toast.LENGTH_SHORT).show() }
        binding.btnNotification.setOnClickListener{ navigation(Constant.KEY_NOTIFICATION) }
//        binding.btnSecurity.setOnClickListener{ navigation(Constant.KEY_SECURITY) }
        binding.btnPrivacy.setOnClickListener{ navigation(Constant.KEY_POLICY) }
        binding.btnLogout.setOnClickListener{ logout() }
    }

    override fun addDataObserver() {
    }

    private fun navigation(status: String){
        val intent = Intent(requireContext(), ProfileAndSettingActivity::class.java)
        intent.putExtra("STATUS_KEY", status)
        startActivity(intent)
    }

    private fun logout(){
        dataManager.setToken("")
        val intent = Intent(requireContext(), LoginFragment::class.java)
        startActivity(intent)
    }
}
