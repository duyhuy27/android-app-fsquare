package vn.md18.fsquareapplication.features.checkout.ui.fragment

import android.content.Intent
import android.os.Bundle
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
import vn.md18.fsquareapplication.databinding.FragmentOrderDetailBinding
import vn.md18.fsquareapplication.features.checkout.adapter.CheckoutAdapter
import vn.md18.fsquareapplication.features.checkout.viewmodel.CheckoutViewmodel
import vn.md18.fsquareapplication.features.main.ui.MainActivity
import vn.md18.fsquareapplication.features.main.viewmodel.MainViewModel
import vn.md18.fsquareapplication.utils.extensions.showCustomToast
import javax.inject.Inject

@AndroidEntryPoint
class OrderDetailFragment : BaseFragment<FragmentOrderDetailBinding, CheckoutViewmodel>() {

    @Inject
    lateinit var checkoutAdapter: CheckoutAdapter

    override val viewModel: CheckoutViewmodel by activityViewModels()
    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentOrderDetailBinding =
        FragmentOrderDetailBinding.inflate(layoutInflater)

    override fun getTagFragment(): String = OrderDetailFragment::class.java.simpleName

    private var isLocationSelected = false

    override fun onViewLoaded() {
        viewModel.getBagList()
        viewModel.getLocationCustomerList()
        parentFragmentManager.setFragmentResultListener("REQUEST_KEY_LOCATION", this) { _, bundle ->
            val location = bundle.getSerializable("SELECTED_LOCATION") as? GetLocationCustomerResponse
            if (location != null) {
                fetchOrderFee(location)
                updateLocationUI(location)
                isLocationSelected = true
                viewModel.defaultLocation.removeObservers(viewLifecycleOwner)
            } else {
                activity?.showCustomToast("null")
                viewModel.defaultLocation.observe(this@OrderDetailFragment) { defaultLocation ->
                    if (!isLocationSelected && defaultLocation != null) {
                        updateLocationUI(defaultLocation)
                        fetchOrderFee(defaultLocation)
                    }
                }
            }
        }

        binding.rcvProductCart.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = checkoutAdapter
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
                    val codAmount = 150000.0
                    val clientOrderCode = generateOrderCode()
                    if (shippingFee != null) {
                        viewModel.createOrder(
                            toName = "phuc",
                            toPhone = "0388474968",
                            toAddress = location.address,
                            toWardName = location.wardName,
                            toDistrictName = location.districtName,
                            toProvinceName = location.provinceName,
                            clientOrderCode = clientOrderCode,
                            weight = 1500.0,
                            codAmount = codAmount,
                            shippingFee = shippingFee,
                            content = "Nội dung đơn hàng",
                            isFreeShip = false,
                            isPayment = true,
                            note = "Đơn hàng gấp"
                        )
                    }
                } else {
                    activity?.showCustomToast("Vui lòng kiểm tra địa chỉ và phí giao hàng!")
                }
            }
        }

        binding.toolbarCheckout.onClickBackPress = {
            navigateBackToMain()
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            navigateBackToMain()
        }
    }

    override fun addDataObserver() {
        viewModel.listBag.observe(this@OrderDetailFragment) { bagList ->
            checkoutAdapter.submitList(bagList)
        }

        viewModel.defaultLocation.observe(this@OrderDetailFragment) { location ->
            if (!isLocationSelected && location != null) {
                updateLocationUI(location)
                fetchOrderFee(location)
            }
        }

        viewModel.getOrderFeeState.observe(this@OrderDetailFragment) { state ->
            when (state) {
                is DataState.Success -> {
                    val fee = state.data.data
                    binding.txtAmuontCheckout.text = "$fee VND"
                    binding.txtTotalCheckout.text = "$fee VND"
                }
                is DataState.Error -> {
                    activity?.showCustomToast("Lỗi khi lấy phí giao hàng: ${state.exception.message}")
                }
                is DataState.Loading -> {
                    activity?.showCustomToast("Đang tải phí giao hàng...")
                }
            }
        }

        viewModel.createOrderState.observe(this@OrderDetailFragment) { state ->
            when (state) {
                is DataState.Success -> {
                    activity?.showCustomToast("Tạo đơn hàng thành công!")
                }
                is DataState.Error -> {
                    activity?.showCustomToast("Tạo đơn hàng thất bại: ${state.exception.message}")
                }
                DataState.Loading -> {}
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
        val clientOrderCode = generateOrderCode()
        viewModel.getOrderFee(
            clientOrderCode = clientOrderCode,
            toName = "nguyen phuc",
            toPhone = "0388474968",
            toAddress = location.address,
            toWardName = location.wardName,
            toDistrictName = location.districtName,
            toProvinceName = location.provinceName,
            codAmount = 150000.0,
            weight = 1500.0,
            content = "Nội dung đơn hàng"
        )
    }

    private fun navigationToUpdateAddressFragment() {
        findNavController().navigate(R.id.action_orderDetailFragment_to_shippingAddressFragment)
    }

    private fun navigateBackToMain() {
        val intent = Intent(requireContext(), MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            putExtra("SELECTED_TAB", MainViewModel.TAB_CARD_CONTEXT)
        }
        startActivity(intent)
        requireActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
    }

    companion object {
        @JvmStatic
        fun newInstance() = OrderDetailFragment()
    }

    private fun generateOrderCode(): String {
        val timestamp = System.currentTimeMillis()
        return "ORD$timestamp"
    }
}
