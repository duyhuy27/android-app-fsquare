package vn.md18.fsquareapplication.features.profileandsetting.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import vn.md18.fsquareapplication.core.base.BaseFragment
import vn.md18.fsquareapplication.databinding.FragmentAddressBinding
import vn.md18.fsquareapplication.databinding.FragmentContactBinding
import vn.md18.fsquareapplication.features.main.ui.MainActivity
import vn.md18.fsquareapplication.features.main.viewmodel.MainViewModel
import vn.md18.fsquareapplication.features.profileandsetting.viewmodel.LocationViewModel
@AndroidEntryPoint
class ContactFragment : BaseFragment<FragmentContactBinding, LocationViewModel>() {
    override val viewModel: LocationViewModel by viewModels()

    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentContactBinding = FragmentContactBinding.inflate(layoutInflater)


    override fun getTagFragment(): String = ContactFragment::class.java.simpleName

    override fun onViewLoaded() {

    }

    override fun addViewListener() {
        binding.apply {
            toolbarEditProfile.onClickBackPress = {
                val intent = Intent(requireContext(), MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    putExtra("SELECTED_TAB", MainViewModel.TAB_PROFILE)
                }
                startActivity(intent)
                requireActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
            }

            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
                val intent = Intent(requireContext(), MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    putExtra("SELECTED_TAB", MainViewModel.TAB_PROFILE)
                }
                startActivity(intent)
                requireActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
            }
        }
    }

}