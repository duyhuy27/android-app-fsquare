package vn.md18.fsquareapplication.features.main.ui.fragment

import android.content.Intent
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import vn.md18.fsquareapplication.core.base.BaseFragment
import vn.md18.fsquareapplication.data.model.DataState
import vn.md18.fsquareapplication.databinding.FragmentProfileBinding
import vn.md18.fsquareapplication.features.main.viewmodel.MainViewModel
import vn.md18.fsquareapplication.features.profileandsetting.ui.ProfileAndSettingActivity
import vn.md18.fsquareapplication.features.profileandsetting.viewmodel.ProfileViewModel
import vn.md18.fsquareapplication.utils.Constant
import vn.md18.fsquareapplication.utils.extensions.loadImageUri

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>() {
    override val viewModel: ProfileViewModel by activityViewModels()
    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentProfileBinding = FragmentProfileBinding.inflate(layoutInflater)

    override fun getTagFragment(): String = ProfileFragment::class.java.simpleName

    override fun onViewLoaded() {
        viewModel.getProfile()
        binding.apply {
            btnEditAvatar.setOnClickListener {

            }
        }

    }

    override fun addViewListener() {
        binding.btnAddress.setOnClickListener { navigation(Constant.KEY_ADDRESS) }
        binding.btnProfile.setOnClickListener { navigation(Constant.KEY_EDIT_PROFILE)}
        binding.btnNotification.setOnClickListener{ navigation(Constant.KEY_NOTIFICATION) }
        binding.btnWallet.setOnClickListener{ navigation(Constant.KEY_PAYMENT) }
        binding.btnSecurity.setOnClickListener{ navigation(Constant.KEY_SECURITY) }
        binding.btnLanguage.setOnClickListener{ navigation(Constant.KEY_LANGUAGE) }
        binding.btnPrivacy.setOnClickListener{ navigation(Constant.KEY_POLICY) }
        binding.btnLogout.setOnClickListener{ navigation(Constant.KEY_EDIT_PROFILE) }
    }

    override fun addDataObserver() {
        viewModel.getProfile.observe(this@ProfileFragment) {
            binding.apply {
                if (it is DataState.Success){
                    it.data?.let { it1 -> txtName.text = "${ it1.firstName} ${it1.lastName}"}
                    it.data?.let { it1 -> txtPhonenumber.text = it1.phone }
                    it.data?.let { it1 -> avatar.loadImageUri(it1.avatar.toUri()) }
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }

    private fun navigation(status: String){
        val intent = Intent(requireContext(), ProfileAndSettingActivity::class.java)
        intent.putExtra("STATUS_KEY", status)
        startActivity(intent)
    }
}