package vn.md18.fsquareapplication.features.checkout.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.core.base.BaseFragment
import vn.md18.fsquareapplication.databinding.FragmentCardBinding
import vn.md18.fsquareapplication.databinding.FragmentOrderDetailBinding
import vn.md18.fsquareapplication.features.checkout.adapter.CheckoutAdapter
import vn.md18.fsquareapplication.features.checkout.viewmodel.CheckoutViewmodel
import vn.md18.fsquareapplication.features.main.adapter.BagAdapter
import vn.md18.fsquareapplication.features.main.ui.fragment.CardFragment
import vn.md18.fsquareapplication.features.main.viewmodel.BagViewmodel
import javax.inject.Inject

@AndroidEntryPoint
class OrderDetailFragment : BaseFragment<FragmentOrderDetailBinding, CheckoutViewmodel>() {

    @Inject
    lateinit var checkoutAdapter: CheckoutAdapter

    override val viewModel: CheckoutViewmodel by  activityViewModels()
    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentOrderDetailBinding = FragmentOrderDetailBinding.inflate(layoutInflater)

    override fun getTagFragment(): String = OrderDetailFragment::class.java.simpleName

    override fun onViewLoaded() {
        binding.apply {
            rcvProductCart.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                setHasFixedSize(true)
                adapter = checkoutAdapter
            }
        }
    }

    override fun addViewListener() {
        binding.apply {
            itemCheckoutAddress.txtEditAddressCheckout.setOnClickListener {
                navigationToUpdateAddressFragment()
            }
        }
    }

    override fun addDataObserver() {
        viewModel.listBag.observe(this@OrderDetailFragment) {
            binding.apply {
                checkoutAdapter.submitList(it)
            }
        }

        viewModel.defaultLocation.observe(this@OrderDetailFragment) { location ->
            location?.let {
                binding.apply {
                    itemCheckoutAddress.txtHome.text = it.title
                    itemCheckoutAddress.txtAddress.text = it.address + ", " + it.wardName + ", " + it.districtName + ", " + it.provinceName
                }
            }
        }
    }
    companion object {
        @JvmStatic
        fun newInstance() = OrderDetailFragment()
    }

    private fun navigationToUpdateAddressFragment() {
        findNavController().navigate(R.id.action_orderDetailFragment_to_shippingAddressFragment)
    }

}