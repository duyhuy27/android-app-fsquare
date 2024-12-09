package vn.md18.fsquareapplication.features.main.ui.fragment

import BottomDialogLoggoutFragment
import android.content.Intent
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.core.base.BaseFragment
import vn.md18.fsquareapplication.data.model.DataState
import vn.md18.fsquareapplication.databinding.DialogConfirmDeleteFavBinding
import vn.md18.fsquareapplication.databinding.FragmentProfileBinding
import vn.md18.fsquareapplication.features.auth.ui.AuthActivity
import vn.md18.fsquareapplication.features.auth.ui.fragment.LoginFragment
import vn.md18.fsquareapplication.features.auth.ui.fragment.SplashFragment
import vn.md18.fsquareapplication.features.main.ui.NotificationActivity
import vn.md18.fsquareapplication.features.main.viewmodel.MainViewModel
import vn.md18.fsquareapplication.features.profileandsetting.ui.ProfileAndSettingActivity
import vn.md18.fsquareapplication.features.profileandsetting.viewmodel.ProfileViewModel
import vn.md18.fsquareapplication.utils.Constant
import vn.md18.fsquareapplication.utils.extensions.loadImageURL
import vn.md18.fsquareapplication.utils.extensions.loadImageUri
import vn.md18.fsquareapplication.utils.fslogger.FSLogger

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
        binding.btnSecurity.setOnClickListener{ navigation(Constant.KEY_SECURITY) }
        binding.btnPrivacy.setOnClickListener{ navigation(Constant.KEY_POLICY) }
        binding.btnLogout.setOnClickListener{ logout() }
    }

    override fun addDataObserver() {
        viewModel.getProfile.observe(this@ProfileFragment) {
            binding.apply {
                if (it is DataState.Success){
                    it.data?.let { it1 -> txtName.text = "${ it1.firstName} ${it1.lastName}"}
                    it.data?.let { it1 -> txtPhonenumber.text = it1.phone }
                    it.data?.let { it1 -> avatar.apply {
                        val thumbnailUrl = it1.avatar?.url
                        if (!thumbnailUrl.isNullOrEmpty()) {
                            loadImageURL(thumbnailUrl)
                        } else {
                            setImageResource(R.drawable.avatar)
                        }
                    } }
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }

    private fun navigation(status: String){
        openActivity(NotificationActivity::class.java)
    }

    fun logout(){
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_confirm_delete_fav, null)
        val binding = DialogConfirmDeleteFavBinding.bind(dialogView)

        val alertDialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        binding.txtTitle.text = getString(R.string.Loggout)
        binding.txtContent.text = getString(R.string.Text_confirm_loggout)

        binding.btnCancel.setOnClickListener {
            alertDialog.dismiss()
        }

        binding.btnConfirm.setOnClickListener {
            dataManager.setToken("")
            FSLogger.d("Phuczk", "token: ${dataManager.getToken()}")
            alertDialog.dismiss()
            val intent = Intent(requireContext(), AuthActivity::class.java)
            startActivity(intent)
        }

        alertDialog.show()
    }
}