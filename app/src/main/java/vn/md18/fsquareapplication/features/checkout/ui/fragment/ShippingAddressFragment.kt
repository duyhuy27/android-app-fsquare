package vn.md18.fsquareapplication.features.checkout.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.core.base.BaseFragment
import vn.md18.fsquareapplication.data.model.DataState
import vn.md18.fsquareapplication.databinding.FragmentAddressBinding
import vn.md18.fsquareapplication.databinding.FragmentShippingAddressBinding
import vn.md18.fsquareapplication.features.profileandsetting.adapter.LocationCustomerAdapter
import vn.md18.fsquareapplication.features.profileandsetting.ui.ProfileAndSettingActivity
import vn.md18.fsquareapplication.features.profileandsetting.ui.fragment.NewAddressFragment
import vn.md18.fsquareapplication.features.profileandsetting.viewmodel.LocationViewModel
import vn.md18.fsquareapplication.utils.Constant
import javax.inject.Inject


@AndroidEntryPoint
class ShippingAddressFragment : BaseFragment<FragmentShippingAddressBinding, LocationViewModel>() {
    override val viewModel: LocationViewModel by viewModels()

    @Inject
    lateinit var locationCustomerAdapter: LocationCustomerAdapter
    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentShippingAddressBinding = FragmentShippingAddressBinding.inflate(layoutInflater)

    override fun getTagFragment(): String = ShippingAddressFragment::class.java.simpleName

    override fun onViewLoaded() {
        viewModel.getLocationCustomerList()
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
            btnAddAddress.setOnClickListener{
                navigation(Constant.KEY_NEW_ADDRESS)
            }
            locationCustomerAdapter.setOnItemClickListener { location ->
                val bundle = Bundle().apply {
                    putSerializable("location", location)
                }
                findNavController().navigate(R.id.action_addressFragment_to_newAddressFragment, bundle)
            }

        }
    }

    override fun addDataObserver() {
        viewModel.listLocationCustomer.observe(this@ShippingAddressFragment) {
            binding.apply {
                locationCustomerAdapter.submitList(it)
                locationCustomerAdapter.notifyDataSetChanged()
            }
        }

        viewModel.addLocationState.observe(this@ShippingAddressFragment) { state ->
            if (state is DataState.Success) {
                viewModel.getLocationCustomerList()
            }
        }
    }

    private fun navigation(status: String){
        val intent = Intent(requireContext(), ProfileAndSettingActivity::class.java)
        intent.putExtra("STATUS_KEY", status)
        startActivity(intent)
    }
}