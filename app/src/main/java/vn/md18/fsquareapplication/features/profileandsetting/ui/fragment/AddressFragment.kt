package vn.md18.fsquareapplication.features.profileandsetting.ui.fragment

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.core.base.BaseFragment
import vn.md18.fsquareapplication.data.model.DataState
import vn.md18.fsquareapplication.databinding.FragmentAddressBinding
import vn.md18.fsquareapplication.databinding.FragmentHomeBinding
import vn.md18.fsquareapplication.databinding.FragmentNewAddressBinding
import vn.md18.fsquareapplication.features.main.ui.MainActivity
import vn.md18.fsquareapplication.features.main.ui.fragment.HomeFragment
import vn.md18.fsquareapplication.features.main.viewmodel.MainViewModel
import vn.md18.fsquareapplication.features.profileandsetting.adapter.LocationCustomerAdapter
import vn.md18.fsquareapplication.features.profileandsetting.adapter.ProvinceAdapter
import vn.md18.fsquareapplication.features.profileandsetting.viewmodel.LocationViewModel
import vn.md18.fsquareapplication.features.profileandsetting.viewmodel.ProfileViewModel
import vn.md18.fsquareapplication.utils.Constant
import vn.md18.fsquareapplication.utils.extensions.showCustomToast
import vn.md18.fsquareapplication.utils.view.CustomToolbar
import javax.inject.Inject

@AndroidEntryPoint
class AddressFragment : BaseFragment<FragmentAddressBinding, LocationViewModel>() {
    override val viewModel: LocationViewModel by viewModels()

    @Inject
    lateinit var locationCustomerAdapter: LocationCustomerAdapter
    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentAddressBinding = FragmentAddressBinding.inflate(layoutInflater)

    override fun getTagFragment(): String = NewAddressFragment::class.java.simpleName

    override fun onViewLoaded() {
        viewModel.getLocationCustomerList()
        setFragmentResultListener(Constant.KEY_REQUEST) { _, bundle ->
            val updateResult = bundle.getBoolean(Constant.KEY_UPDATE_SUCCESS)
            val addResult = bundle.getBoolean(Constant.KEY_ADD_SUCCESS)
            if (updateResult || addResult) {
                viewModel.getLocationCustomerList()
            }
        }
        binding.apply {
            rcvAddress.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                setHasFixedSize(true)
                adapter = locationCustomerAdapter
            }
        }
    }

    override fun addViewListener() {
        binding.apply {
            btnAddNewAddress.setOnClickListener{
                val bundle = Bundle()
                findNavController().navigate(R.id.action_addressFragment_to_newAddressFragment, bundle)
            }
            locationCustomerAdapter.setOnItemClickListener { location ->
                val bundle = Bundle().apply {
                    putSerializable("location", location)
                }
                findNavController().navigate(R.id.action_addressFragment_to_newAddressFragment, bundle)
            }
            toolbarAddress.onClickBackPress = {
                val intent = Intent(requireContext(), MainActivity::class.java).apply {
                    flags = FLAG_ACTIVITY_CLEAR_TOP or FLAG_ACTIVITY_NEW_TASK
                    putExtra("SELECTED_TAB", MainViewModel.TAB_PROFILE)
                }
                startActivity(intent)
            }

            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
                val intent = Intent(requireContext(), MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    putExtra("SELECTED_TAB", MainViewModel.TAB_PROFILE)
                }
                startActivity(intent)
            }
        }


    }

    override fun addDataObserver() {
        viewModel.listLocationCustomer.observe(this@AddressFragment) {
            binding.apply {
                locationCustomerAdapter.submitList(it)
                locationCustomerAdapter.notifyDataSetChanged()
            }
        }

        viewModel.addLocationState.observe(this@AddressFragment) { state ->
            if (state is DataState.Success) {
                viewModel.getLocationCustomerList()
            }
        }

        viewModel.updateLocationState.observe(this@AddressFragment) { state ->
            if (state is DataState.Success) {
                viewModel.getLocationCustomerList()
            }
        }
    }

}