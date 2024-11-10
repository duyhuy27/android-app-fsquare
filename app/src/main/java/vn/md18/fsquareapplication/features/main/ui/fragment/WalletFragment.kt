package vn.md18.fsquareapplication.features.main.ui.fragment

import android.content.Intent
import android.view.LayoutInflater
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import vn.md18.fsquareapplication.core.base.BaseFragment
import vn.md18.fsquareapplication.databinding.FragmentWalletBinding
import vn.md18.fsquareapplication.features.main.adapter.OrderAdapter
import vn.md18.fsquareapplication.features.main.viewmodel.MainViewModel
import vn.md18.fsquareapplication.features.main.viewmodel.OrderViewModel
import vn.md18.fsquareapplication.features.payment.ui.PaymentActivity
import vn.md18.fsquareapplication.features.profileandsetting.ui.ProfileAndSettingActivity
import vn.md18.fsquareapplication.utils.extensions.showCustomToast
import javax.inject.Inject


@AndroidEntryPoint
class WalletFragment : BaseFragment<FragmentWalletBinding, OrderViewModel>() {

    override val viewModel: OrderViewModel by viewModels()
    @Inject
    lateinit var orderAdapter: OrderAdapter
    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentWalletBinding = FragmentWalletBinding.inflate(layoutInflater)

    override fun getTagFragment(): String = WalletFragment::class.java.simpleName

    override fun onViewLoaded() {
        viewModel.getOrderList()
        binding.apply {
            rcvProductCart.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                setHasFixedSize(true)
                adapter = orderAdapter
            }
        }
    }

    override fun addViewListener() {
        viewModel.getOrderList()

        binding.apply {
            imgVietQr.setOnClickListener {
                navigation("PAYMENT_METHOD")
            }

            txtSeeall.setOnClickListener {
                navigation("TRANSACTION")
            }


        }
    }

    override fun addDataObserver() {
        viewModel.listOrder.observe(this@WalletFragment) {
            binding.apply {
                if (it.isEmpty()) {
                    rcvProductCart.visibility = android.view.View.GONE
                    imgNoTransaction.visibility = android.view.View.VISIBLE
                } else {
                    rcvProductCart.visibility = android.view.View.VISIBLE
                    imgNoTransaction.visibility = android.view.View.GONE
                    orderAdapter.submitList(it)
                }
            }
        }
    }
    companion object {
        @JvmStatic
        fun newInstance() = WalletFragment()
    }

    private fun navigation(status: String){
        val intent = Intent(requireContext(), PaymentActivity::class.java)
        intent.putExtra("STATUS_KEY", status)
        startActivity(intent)
    }
}