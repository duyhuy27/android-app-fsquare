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

@AndroidEntryPoint
class VNPFragment : BaseFragment<FragmentVNPBinding, CheckoutViewmodel>() {
    override val viewModel: CheckoutViewmodel by viewModels()

    private var clientOrderId: String? = ""
    private var amount: Double = 0.0
    private var phone: String? = ""

    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentVNPBinding =
        FragmentVNPBinding.inflate(layoutInflater)

    override fun getTagFragment(): String = VNPFragment::class.java.simpleName

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
                result?.paymentUrl?.let { url ->
                    shimmerFrameLayout.visibility = View.VISIBLE
                    webview.visibility = View.GONE

                    webview.webViewClient = object : WebViewClient() {
                        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                            view?.loadUrl(request?.url.toString())
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
}
