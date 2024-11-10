package vn.md18.fsquareapplication.features.payment.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import vn.md18.fsquareapplication.core.base.BaseFragment
import vn.md18.fsquareapplication.databinding.FragmentTransactionHistoryPaymentBinding
import vn.md18.fsquareapplication.features.main.adapter.OrderAdapter
import vn.md18.fsquareapplication.features.main.ui.MainActivity
import vn.md18.fsquareapplication.features.main.viewmodel.MainViewModel
import vn.md18.fsquareapplication.features.main.viewmodel.OrderViewModel
import vn.md18.fsquareapplication.features.profileandsetting.adapter.ProvinceAdapter
import vn.md18.fsquareapplication.features.profileandsetting.ui.fragment.NewAddressFragment
import vn.md18.fsquareapplication.features.profileandsetting.viewmodel.LocationViewModel
import javax.inject.Inject

@AndroidEntryPoint
class TransactionHistoryPaymentFragment : BaseFragment<FragmentTransactionHistoryPaymentBinding, OrderViewModel>() {
    override val viewModel: OrderViewModel by viewModels()
    @Inject
    lateinit var orderAdapter: OrderAdapter

    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentTransactionHistoryPaymentBinding = FragmentTransactionHistoryPaymentBinding.inflate(layoutInflater)

    override fun getTagFragment(): String = TransactionHistoryPaymentFragment::class.java.simpleName

    override fun onViewLoaded() {
        viewModel.getOrderList()
        binding.apply {
            rcvTransactionHistory.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                setHasFixedSize(true)
                adapter = orderAdapter
            }
        }
    }

    override fun addViewListener() {
        binding.apply {
            toolbarTransactionHistory.onClickBackPress = {
                val intent = Intent(requireContext(), MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    putExtra("SELECTED_TAB", MainViewModel.TAB_WALLET)
                }
                startActivity(intent)
            }

            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
                val intent = Intent(requireContext(), MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    putExtra("SELECTED_TAB", MainViewModel.TAB_WALLET)
                }
                startActivity(intent)
            }
        }
    }

    override fun addDataObserver() {
        viewModel.listOrder.observe(this@TransactionHistoryPaymentFragment) {
            binding.apply {
                binding.apply {
                    if (it.isEmpty()) {
                        rcvTransactionHistory.visibility = android.view.View.GONE
                        imgNoTransaction.visibility = android.view.View.VISIBLE
                    } else {
                        rcvTransactionHistory.visibility = android.view.View.VISIBLE
                        imgNoTransaction.visibility = android.view.View.GONE
                        orderAdapter.submitList(it)
                    }
                }
            }
        }
    }

}