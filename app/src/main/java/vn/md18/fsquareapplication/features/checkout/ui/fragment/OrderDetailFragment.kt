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
import vn.md18.fsquareapplication.data.network.model.response.bag.GetBagResponse
import vn.md18.fsquareapplication.data.network.model.response.location.GetLocationCustomerResponse
import vn.md18.fsquareapplication.data.network.model.response.profile.GetProfileResponse
import vn.md18.fsquareapplication.databinding.FragmentOrderDetailBinding
import vn.md18.fsquareapplication.features.checkout.adapter.CheckoutAdapter
import vn.md18.fsquareapplication.features.checkout.viewmodel.CheckoutViewmodel
import vn.md18.fsquareapplication.features.detail.ui.DetailProductActivity
import vn.md18.fsquareapplication.features.main.ui.DetailOrderActivity
import vn.md18.fsquareapplication.features.main.ui.MainActivity
import vn.md18.fsquareapplication.features.main.viewmodel.MainViewModel
import vn.md18.fsquareapplication.utils.Constant
import vn.md18.fsquareapplication.utils.extensions.showCustomToast
import java.text.DecimalFormat
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
    private var isPayment = true

    override fun onViewLoaded() {
        viewModel.getProfile()
        viewModel.getLocationCustomerList()
        viewModel.getBagList()
        parentFragmentManager.setFragmentResultListener("REQUEST_KEY_LOCATION", this) { _, bundle ->
            val location = bundle.getSerializable("SELECTED_LOCATION") as? GetLocationCustomerResponse
            if (location != null) {
                viewModel.getProfile.value?.let { profileState ->
                    if (profileState is DataState.Success) {
                        fetchOrderFee(location, profileState.data)
                    }
                }
                updateLocationUI(location)
                isLocationSelected = true
                viewModel.defaultLocation.removeObservers(viewLifecycleOwner)
            } else {
                viewModel.defaultLocation.observe(this@OrderDetailFragment) { defaultLocation ->
                    if (!isLocationSelected && defaultLocation != null) {
                        viewModel.getProfile.value?.let { profileState ->
                            if (profileState is DataState.Success) {
                                fetchOrderFee(defaultLocation, profileState.data)
                            }
                        }
                        updateLocationUI(defaultLocation)
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

            rdoCash.setOnClickListener {
                rdoCash.isChecked = true
                rdoBaokim.isChecked = false
                isPayment = false
            }
            rdoBaokim.setOnClickListener {
                rdoBaokim.isChecked = true
                rdoCash.isChecked = false
                isPayment = true
            }

            if(rdoCash.isChecked){
                rdoBaokim.isChecked = false
                isPayment = false
            }else{
                rdoBaokim.isChecked = true
                isPayment = true
            }

            btnContinue.setOnClickListener {
                val location = viewModel.defaultLocation.value
                val orderFeeState = viewModel.getOrderFeeState.value

                if (location != null && orderFeeState is DataState.Success) {
                    val shippingFee = orderFeeState.data.data
                    val totalPrice = viewModel.listBag.value?.sumOf { it.price * it.quantity } ?: 0.0
                    var phone = ""
                    val clientOrderCode = generateOrderCode()

                    if(isPayment){
                        viewModel.getProfile.value?.let { profileState ->
                            if (profileState is DataState.Success) {
                                phone = profileState.data.phone
                            } else {
                                activity?.showCustomToast("Không thể lấy thông tin số điện thoại!")
                            }
                        }
                        navigationToBaokimPayment(generateOrderCode(), totalPrice, phone)

                    }else{
                        viewModel.getProfile.value?.let { profileState ->
                            if (profileState is DataState.Success) {
                                val profile = profileState.data
                                if (shippingFee != null) {
                                    viewModel.createOrder(
                                        toName = profile.firstName,
                                        toPhone = profile.phone,
                                        toAddress = location.address,
                                        toWardName = location.wardName,
                                        toDistrictName = location.districtName,
                                        toProvinceName = location.provinceName,
                                        clientOrderCode = clientOrderCode,
                                        weight = 1500.0,
                                        codAmount = totalPrice,
                                        shippingFee = shippingFee,
                                        content = "Nội dung đơn hàng",
                                        isFreeShip = false,
                                        isPayment = isPayment,
                                        note = "Đơn hàng gấp"
                                    )
                                }else{

                                }
                            } else {
                                activity?.showCustomToast("Không thể lấy thông tin từ hồ sơ khách hàng!")
                            }
                        }
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
                viewModel.getProfile.value?.let { profileState ->
                    if (profileState is DataState.Success) {
                        fetchOrderFee(location, profileState.data)
                    }
                }
                updateLocationUI(location)
            }
        }

        viewModel.getOrderFeeState.observe(this@OrderDetailFragment) { state ->
            when (state) {
                is DataState.Success -> {
                    val formatter: DecimalFormat = DecimalFormat("#,###")
                    val fee = state.data.data
                    binding.txtShippingCheckout.text = formatter.format(fee)
                    viewModel.listBag.value?.let {
                        val totalPrice = it.sumOf { item -> item.price * item.quantity }

                        binding.txtAmuontCheckout.text = formatter.format(totalPrice)
                        binding.txtTotalCheckout.text = formatter.format(totalPrice + fee!!)
                    }
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
                    viewModel.deleteBag()
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

    private fun fetchOrderFee(location: GetLocationCustomerResponse, profileResponse: GetProfileResponse) {
        val clientOrderCode = generateOrderCode()
        viewModel.getOrderFee(
            clientOrderCode = clientOrderCode,
            toName = profileResponse.firstName,
            toPhone = profileResponse.phone,
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

    private fun navigationToBaokimPayment(clientOrderId: String, amount: Double, phone: String) {
        val bundle = Bundle().apply {
            putString("clientOrderId", clientOrderId)
            putDouble("amount", amount)
            putString("phone", phone)
        }
        findNavController().navigate(R.id.action_orderDetailFragment_to_VNPFragment, bundle)
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
        val characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        val randomCode = (1..10)
            .map { characters.random() }
            .joinToString("")
        return "FSORDER$randomCode"
    }

}
