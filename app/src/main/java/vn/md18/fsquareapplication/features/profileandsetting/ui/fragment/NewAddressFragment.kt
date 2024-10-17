package vn.md18.fsquareapplication.features.profileandsetting.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import vn.md18.fsquareapplication.core.base.BaseFragment
import vn.md18.fsquareapplication.databinding.FragmentLanguageBinding
import vn.md18.fsquareapplication.databinding.FragmentNewAddressBinding
import vn.md18.fsquareapplication.databinding.FragmentProfileBinding
import vn.md18.fsquareapplication.features.profileandsetting.viewmodel.ProfileViewModel

@AndroidEntryPoint
class NewAddressFragment : BaseFragment<FragmentNewAddressBinding, ProfileViewModel>() {
    override val viewModel: ProfileViewModel by viewModels()


    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentNewAddressBinding =
        FragmentNewAddressBinding.inflate(layoutInflater)

    override fun getTagFragment(): String = NewAddressFragment::class.java.simpleName

    override fun onViewLoaded() {
        TODO("Not yet implemented")
    }

    override fun addViewListener() {
        TODO("Not yet implemented")
    }
}