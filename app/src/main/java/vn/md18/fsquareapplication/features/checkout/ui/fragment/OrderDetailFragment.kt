package vn.md18.fsquareapplication.features.checkout.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.core.base.BaseFragment
import vn.md18.fsquareapplication.core.eventbus.CheckPaymentStatus
import vn.md18.fsquareapplication.data.model.DataState
import vn.md18.fsquareapplication.data.network.model.response.GetOrderRespose
import vn.md18.fsquareapplication.data.network.model.response.location.GetLocationCustomerResponse
import vn.md18.fsquareapplication.data.network.model.response.profile.GetProfileResponse
import vn.md18.fsquareapplication.databinding.CustomDialogOrderSuccessfulBinding
import vn.md18.fsquareapplication.databinding.DialogConfirmDeleteFavBinding
import vn.md18.fsquareapplication.databinding.FragmentOrderDetailBinding
import vn.md18.fsquareapplication.features.checkout.adapter.CheckoutAdapter
import vn.md18.fsquareapplication.features.checkout.viewmodel.CheckoutViewmodel
import vn.md18.fsquareapplication.features.main.ui.MainActivity
import vn.md18.fsquareapplication.features.main.viewmodel.MainViewModel
import vn.md18.fsquareapplication.utils.OrderStatus
import vn.md18.fsquareapplication.utils.extensions.delayFunction
import vn.md18.fsquareapplication.utils.extensions.showCustomToast
import java.text.DecimalFormat
import vn.md18.fsquareapplication.utils.fslogger.FSLogger
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
        parentFragmentManager.setFragmentResultListener("PAYMENT_RESULT", viewLifecycleOwner) { _, bundle ->
            val isPaymentSuccess = bundle.getBoolean("PAYMENT_SUCCESS", false)
            FSLogger.e("Huynd: FragmentResultListener - isPaymentSuccess: $isPaymentSuccess")
            if (isPaymentSuccess) {
                viewModel.checkPaymentSuccess { isSuccess ->
                    if (isSuccess) {
                        activity?.showCustomToast("Thanh toán thành công!")
                        viewModel.deleteBag()
                    } else {
                        activity?.showCustomToast("Thanh toán không thành công!")
                    }
                }
            }
        }


        parentFragmentManager.setFragmentResultListener("REQUEST_KEY_LOCATION", this) { _, bundle ->
            val location = bundle.getSerializable("SELECTED_LOCATION") as? GetLocationCustomerResponse
            FSLogger.e("Huynd: dasdsadasdasdasd $location")
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

                FSLogger.e("Huynd location: $location --- $orderFeeState")

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
                        navigationToBaoKimPayment(generateOrderCode(), totalPrice, phone)

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
                    showDialogConfirm()
                }
                is DataState.Error -> {
                    activity?.showCustomToast("Tạo đơn hàng thất bại: ${state.exception.message}")
                }
                DataState.Loading -> {}
            }
        }

        viewModel.paymentSuccess.observe(this@OrderDetailFragment) { isSuccess ->
            if (isSuccess) {
                activity?.showCustomToast("Thanh toán thành công!")
                viewModel.deleteBag()
            } else {
                activity?.showCustomToast("Thanh toán không thành công!")
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onCheckPaymentStatus(event: CheckPaymentStatus) {
        FSLogger.e("Huynd: SubscribeSubscribe onCheckPaymentStatus - status: ${event.status}")
        viewModel.checkPaymentSuccess(){
            FSLogger.e("Huynd: SubscribeSubscribe onCheckPaymentStatus - status: $it")
            if (it) {
                activity?.showCustomToast("Thanh toán thành công!")
                viewModel.deleteBag()
            } else {
                activity?.showCustomToast("Thanh toán không thành công!")
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

    private fun navigationToBaoKimPayment(clientOrderId: String, amount: Double, phone: String) {
        val bundle = Bundle().apply {
            putString("clientOrderId", clientOrderId)
            putDouble("amount", amount)
            putString("phone", "0$phone")
        }
        dataManager.saveOrderClientID(clientOrderId)
        findNavController().navigate(R.id.action_orderDetailFragment_to_VNPFragment, bundle)
    }

    private fun showDialogConfirm() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.custom_dialog_order_successful, null)
        val binding = CustomDialogOrderSuccessfulBinding.bind(dialogView)
        val alertDialog = androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        binding.apply {
            btnViewOrder.setOnClickListener {
                navigateBackToMain()
            }

        }

        alertDialog.show()
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
        val letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
        val digits = "0123456789"

        // Generate a random sequence of letters and digits
        val randomLetters = (1..6) // 6 chữ cái
            .map { letters.random() }
            .joinToString("")

        val randomDigits = (1..6) // 6 chữ số
            .map { digits.random() }
            .joinToString("")

        return "FSORDER$randomLetters$randomDigits"
    }

}
