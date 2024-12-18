package vn.md18.fsquareapplication.features.checkout.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import vn.md18.fsquareapplication.core.base.BaseFragment
import vn.md18.fsquareapplication.data.model.DataState
import vn.md18.fsquareapplication.databinding.FragmentVNPBinding
import vn.md18.fsquareapplication.features.checkout.viewmodel.CheckoutViewmodel
import vn.md18.fsquareapplication.utils.extensions.showCustomToast
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.navigation.fragment.findNavController
import org.greenrobot.eventbus.EventBus
import vn.md18.fsquareapplication.core.eventbus.CheckPaymentStatus
import vn.md18.fsquareapplication.utils.fslogger.FSLogger

@AndroidEntryPoint
class VNPFragment : BaseFragment<FragmentVNPBinding, CheckoutViewmodel>() {
    override val viewModel: CheckoutViewmodel by viewModels()

    private var clientOrderId: String? = ""
    private var amount: Double = 0.0
    private var phone: String? = ""

    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentVNPBinding =
        FragmentVNPBinding.inflate(layoutInflater)

    override fun getTagFragment(): String = VNPFragment::class.java.simpleName
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
    private fun createOrder() {
        val location = viewModel.defaultLocation.value
        val orderFeeState = viewModel.getOrderFeeState.value
        val totalPrice = viewModel.listBag.value?.sumOf { it.price * it.quantity } ?: 0.0

        if (location != null && orderFeeState is DataState.Success) {
            val shippingFee = orderFeeState.data.data ?: 0.0
            val clientOrderCode = generateOrderCode()

            viewModel.getProfile.value?.let { profileState ->
                if (profileState is DataState.Success) {
                    val profile = profileState.data
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
                        isPayment = true,
                        note = "Đơn hàng gấp"
                    )
                } else {
                    activity?.showCustomToast("Không thể lấy thông tin hồ sơ khách hàng!")
                }
            }
        } else {
            activity?.showCustomToast("Vui lòng kiểm tra địa chỉ hoặc phí giao hàng!")
        }
    }

    override fun onViewLoaded() {
        arguments?.let {
            clientOrderId = it.getString("clientOrderId")
            amount = it.getDouble("amount")
            phone = it.getString("phone")
        }
        clientOrderId?.let { phone?.let { it1 -> viewModel.createPayment(it, amount, it1) } }
    }

    override fun addViewListener() {
    }

    override fun addDataObserver() {
        viewModel.createPaymentState.observe(viewLifecycleOwner) { result ->
            binding.apply {
                FSLogger.e("Huynd: createPaymentState: $result")
                dataManager.saveOrderId(result?.orderId ?: "")
                result?.paymentUrl?.let { url ->
                    shimmerFrameLayout.visibility = View.VISIBLE
                    webview.visibility = View.GONE

                    webview.webViewClient = object : WebViewClient() {
                        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                            val url = request?.url.toString()
                            view?.loadUrl(url)
                            checkForPaymentSuccess(url)
                            return true
                        }
                    }

                    webview.settings.javaScriptEnabled = true
                    webview.loadUrl(url)

                    webview.webChromeClient = object : WebChromeClient() {
                        override fun onProgressChanged(view: WebView?, newProgress: Int) {
                            if (newProgress == 100) {
                                shimmerFrameLayout.stopShimmer()
                                shimmerFrameLayout.visibility = View.GONE
                                webview.visibility = View.VISIBLE
                            }
                        }
                    }
                }
            }
        }
    }

    private fun checkForPaymentSuccess(url: String) {
        if (url.startsWith("https://www.baokim.vn/?created_at=")) {
            // Gửi kết quả thanh toán thành công về OrderDetailFragment
            val resultBundle = Bundle().apply {
                putBoolean("PAYMENT_SUCCESS", true)
            }
            parentFragmentManager.setFragmentResult("PAYMENT_RESULT", resultBundle)

            // Quay lại OrderDetailFragment
            parentFragmentManager.popBackStack()
        }
    }

}

