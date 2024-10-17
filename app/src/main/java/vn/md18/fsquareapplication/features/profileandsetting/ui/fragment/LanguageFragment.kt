package vn.md18.fsquareapplication.features.profileandsetting.ui.fragment

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
import vn.md18.fsquareapplication.features.profileandsetting.viewmodel.ProfileViewModel

@AndroidEntryPoint
class LanguageFragment : BaseFragment<FragmentLanguageBinding, ProfileViewModel>() {
    override val viewModel: ProfileViewModel by activityViewModels()
    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentLanguageBinding = FragmentLanguageBinding.inflate(layoutInflater)
    override fun getTagFragment(): String = LanguageFragment::class.java.simpleName

    override fun onViewLoaded() {

    }

    override fun addViewListener() {

    }

}