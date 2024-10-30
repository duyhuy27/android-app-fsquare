package vn.md18.fsquareapplication.features.profileandsetting.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.core.base.BaseFragment
import vn.md18.fsquareapplication.databinding.FragmentAddressBinding
import vn.md18.fsquareapplication.databinding.FragmentHomeBinding
import vn.md18.fsquareapplication.databinding.FragmentNewAddressBinding
import vn.md18.fsquareapplication.features.main.ui.fragment.HomeFragment
import vn.md18.fsquareapplication.features.main.viewmodel.MainViewModel
import vn.md18.fsquareapplication.features.profileandsetting.adapter.LocationCustomerAdapter
import vn.md18.fsquareapplication.features.profileandsetting.adapter.ProvinceAdapter
import vn.md18.fsquareapplication.features.profileandsetting.viewmodel.LocationViewModel
import vn.md18.fsquareapplication.features.profileandsetting.viewmodel.ProfileViewModel
import javax.inject.Inject

@AndroidEntryPoint
class AddressFragment : BaseFragment<FragmentNewAddressBinding, LocationViewModel>() {
    override val viewModel: LocationViewModel by viewModels()

    @Inject
    lateinit var locationCustomerAdapter: LocationCustomerAdapter
    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentNewAddressBinding = FragmentNewAddressBinding.inflate(layoutInflater)

    override fun getTagFragment(): String = NewAddressFragment::class.java.simpleName

    override fun onViewLoaded() {
        viewModel.getLocationCustomerList()

    }

    override fun addViewListener() {
        binding.apply {
            btnAddNewAddress.setOnClickListener{
                findNavController().navigate(R.id.action_addressFragment_to_newAddressFragment)
            }
        }
    }

    override fun addDataObserver() {
        viewModel.listLocationCustomer.observe(this@AddressFragment) {
            binding.apply {
                locationCustomerAdapter.submitList(it)
            }
        }
    }

}