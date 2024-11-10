package vn.md18.fsquareapplication.features.main.ui.fragment.fragment_enum_order

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import vn.md18.fsquareapplication.core.base.BaseFragment
import vn.md18.fsquareapplication.databinding.FragmentEnumOrderBinding
import vn.md18.fsquareapplication.features.main.adapter.OrderAdapter
import vn.md18.fsquareapplication.features.main.viewmodel.OrderViewModel
import vn.md18.fsquareapplication.utils.OrderStatus
import javax.inject.Inject

@AndroidEntryPoint
class EnumOrderFragment : BaseFragment<FragmentEnumOrderBinding, OrderViewModel>(), OrderAdapter.OnOrderActionListener {
    @Inject
    lateinit var orderAdapter: OrderAdapter
    override val viewModel: OrderViewModel by activityViewModels()

    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentEnumOrderBinding =
        FragmentEnumOrderBinding.inflate(layoutInflater)

    override fun getTagFragment(): String = EnumOrderFragment::class.java.simpleName

    override fun onViewLoaded() {
        val status = arguments?.getSerializable(ARG_STATUS) as? OrderStatus ?: OrderStatus.PENDING
        binding.rcvOrder.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
        }
        fetchOrderList(status)

        orderAdapter.setOrderActionListener(this)
    }

    override fun addViewListener() {

    }

    private fun fetchOrderList(status: OrderStatus) {
        viewModel.getOrderList(status)
    }

    override fun addDataObserver() {
        viewModel.listOrder.observe(viewLifecycleOwner) { orders ->
            if (orders.isEmpty()) {
                binding.rcvOrder.visibility = android.view.View.GONE
                binding.imgNoOrders.visibility = android.view.View.VISIBLE
            } else {
                binding.rcvOrder.visibility = android.view.View.VISIBLE
                binding.imgNoOrders.visibility = android.view.View.GONE
                orderAdapter.submitList(orders)
            }
        }
    }

    companion object {
        private const val ARG_STATUS = "ARG_STATUS"

        @JvmStatic
        fun newInstance(status: OrderStatus) =
            EnumOrderFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_STATUS, status)
                }
            }
    }

    override fun onUpdateOrder(id: String, status: OrderStatus) {
        viewModel.updateOrder(id, status)
    }
}
