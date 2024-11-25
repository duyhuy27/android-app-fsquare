package vn.md18.fsquareapplication.features.checkout.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.core.base.BaseFragment
import vn.md18.fsquareapplication.data.model.DataState
import vn.md18.fsquareapplication.data.network.model.response.location.GetLocationCustomerResponse
import vn.md18.fsquareapplication.databinding.FragmentCardBinding
import vn.md18.fsquareapplication.databinding.FragmentOrderDetailBinding
import vn.md18.fsquareapplication.features.checkout.adapter.CheckoutAdapter
import vn.md18.fsquareapplication.features.checkout.viewmodel.CheckoutViewmodel
import vn.md18.fsquareapplication.features.main.adapter.BagAdapter
import vn.md18.fsquareapplication.features.main.ui.MainActivity
import vn.md18.fsquareapplication.features.main.ui.fragment.CardFragment
import vn.md18.fsquareapplication.features.main.viewmodel.BagViewmodel
import vn.md18.fsquareapplication.features.main.viewmodel.MainViewModel
import vn.md18.fsquareapplication.features.profileandsetting.viewmodel.ProfileViewModel
import vn.md18.fsquareapplication.utils.extensions.showCustomToast
import javax.inject.Inject

@AndroidEntryPoint
class OrderDetailFragment : BaseFragment<FragmentOrderDetailBinding, CheckoutViewmodel>() {

    @Inject
    lateinit var checkoutAdapter: CheckoutAdapter

    override val viewModel: CheckoutViewmodel by  activityViewModels()
    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentOrderDetailBinding = FragmentOrderDetailBinding.inflate(layoutInflater)

    override fun getTagFragment(): String = OrderDetailFragment::class.java.simpleName

    override fun onViewLoaded() {
        viewModel.getBagList()
        viewModel.getLocationCustomerList()
        val locationFromIntent = requireActivity().intent.getSerializableExtra("SELECTED_LOCATION") as? GetLocationCustomerResponse

        if (locationFromIntent != null) {
            updateLocationUI(locationFromIntent)
            fetchOrderFee(locationFromIntent)
        } else {
            viewModel.defaultLocation.observe(this@OrderDetailFragment) { location ->
                location?.let {
                    updateLocationUI(it)
                    fetchOrderFee(it)
                }
            }
        }

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

            btnContinue.setOnClickListener {
                val location = viewModel.defaultLocation.value
                val orderFeeState = viewModel.getOrderFeeState.value
                if (location != null && orderFeeState is DataState.Success) {
                    val shippingFee = orderFeeState.data.data
                    val codAmount = 100000.0

                    if (shippingFee != null) {
                        viewModel.createOrder(
                            toName = "phuc",
                            toPhone = "0388474968",
                            toAddress = location.address,
                            toWardName = location.wardName,
                            toDistrictName = location.districtName,
                            toProvinceName = location.provinceName,
                            clientOrderCode = "ORD123456",
                            weight = 1.0,
                            codAmount = codAmount,
                            shippingFee = shippingFee,
                            content = "Nội dung đơn hàng",
                            isFreeShip = false,
                            isPayment = false,
                            note = "Đơn hàng gấp"
                        )
                    }
                } else {

                }
            }

        }

        binding.toolbarCheckout.onClickBackPress = {
            val intent = Intent(requireContext(), MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                putExtra("SELECTED_TAB", MainViewModel.TAB_CARD_CONTEXT)
            }
            startActivity(intent)
            requireActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            val intent = Intent(requireContext(), MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                putExtra("SELECTED_TAB", MainViewModel.TAB_CARD_CONTEXT)
            }
            startActivity(intent)
            requireActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
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

        viewModel.getOrderFeeState.observe(this@OrderDetailFragment) {
            if(it is DataState.Success){
                binding.apply {
                    txtAmuontCheckout.text = it.data.data.toString() + " VND"
                    txtTotalCheckout.text = "${it.data.data} VND"
                }
            }else{

            }
        }

        viewModel.createOrderState.observe(this@OrderDetailFragment) {
            if(it is DataState.Success){
                activity?.showCustomToast("them order thanh cong")
            }else{
                activity?.showCustomToast("them order that bai")
            }
        }
    }

    private fun updateLocationUI(location: GetLocationCustomerResponse) {
        binding.apply {
            itemCheckoutAddress.txtHome.text = location.title
            itemCheckoutAddress.txtAddress.text =
                "${location.address}, ${location.wardName}, ${location.districtName}, ${location.provinceName}"
        }
    }


    private fun fetchOrderFee(location: GetLocationCustomerResponse) {
        viewModel.getOrderFee(
            clientOrderCode = "ORD123456",
            toName = "phuc",
            toPhone = "0388474968",
            toAddress = location.address,
            toWardName = location.wardName,
            toDistrictName = location.districtName,
            toProvinceName = location.provinceName,
            codAmount = 100000.0,
            weight = 1.0,
            content = "Nội dung đơn hàng"
        )
    }

    companion object {
        @JvmStatic
        fun newInstance() = OrderDetailFragment()
    }

    private fun navigationToUpdateAddressFragment() {
        findNavController().navigate(R.id.action_orderDetailFragment_to_shippingAddressFragment)
    }

}