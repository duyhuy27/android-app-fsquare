package vn.md18.fsquareapplication.features.profileandsetting.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import vn.md18.fsquareapplication.core.base.BaseFragment
import vn.md18.fsquareapplication.databinding.FragmentAddressBinding
import vn.md18.fsquareapplication.databinding.FragmentLanguageBinding
import vn.md18.fsquareapplication.databinding.FragmentSecurityBinding
import vn.md18.fsquareapplication.features.main.ui.MainActivity
import vn.md18.fsquareapplication.features.main.ui.fragment.HomeFragment
import vn.md18.fsquareapplication.features.main.viewmodel.MainViewModel
import vn.md18.fsquareapplication.features.profileandsetting.viewmodel.ProfileViewModel

@AndroidEntryPoint
class SecurityFragment : BaseFragment<FragmentSecurityBinding, ProfileViewModel>() {
    override val viewModel: ProfileViewModel by activityViewModels()
    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentSecurityBinding = FragmentSecurityBinding.inflate(layoutInflater)

    override fun getTagFragment(): String = SecurityFragment::class.java.simpleName

    override fun onViewLoaded() {

    }

    override fun addViewListener() {
        binding.apply {
            toolbarSecurity.onClickBackPress = {
                val intent = Intent(requireContext(), MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    putExtra("SELECTED_TAB", MainViewModel.TAB_PROFILE)
                }
                startActivity(intent)
            }
        }
    }
}