package vn.md18.fsquareapplication.features.main.ui.fragment.fragment_enum_order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import vn.md18.fsquareapplication.core.base.BaseFragment
import vn.md18.fsquareapplication.data.model.DataState
import vn.md18.fsquareapplication.data.network.model.response.GetOrderRespose
import vn.md18.fsquareapplication.databinding.FragmentEnumOrderBinding
import vn.md18.fsquareapplication.features.main.adapter.OrderAdapter
import vn.md18.fsquareapplication.features.main.viewmodel.OrderViewModel
import vn.md18.fsquareapplication.utils.OrderStatus
import vn.md18.fsquareapplication.utils.extensions.showCustomToast
import vn.md18.fsquareapplication.utils.fslogger.FSLogger
import javax.inject.Inject

@AndroidEntryPoint
class EnumOrderFragment : BaseFragment<FragmentEnumOrderBinding, OrderViewModel>() {
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

        binding.rcvOrder.adapter = orderAdapter
        fetchOrderList(status)

    }

    override fun addViewListener() {
        val status = arguments?.getSerializable(ARG_STATUS) as? OrderStatus ?: OrderStatus.PENDING
        fetchOrderList(status)
    }

    private fun fetchOrderList(status: OrderStatus) {
        if (viewModel.currentStatus != status) { // Chỉ gọi API nếu trạng thái khác
            viewModel.currentStatus = status
            viewModel.getOrderList(status)
        }
    }

    fun updateStatus(status: OrderStatus) {
        fetchOrderList(status)
    }


    override fun onResume() {
        super.onResume()
        val status = arguments?.getSerializable(ARG_STATUS) as? OrderStatus ?: OrderStatus.PENDING
        fetchOrderList(status)
    }

    override fun addDataObserver() {
        viewModel.loadingState.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.shimmerFrameLayout.visibility = View.VISIBLE
                binding.shimmerFrameLayout.startShimmer()
                binding.rcvOrder.visibility = View.GONE
                binding.imgNoOrders.visibility = View.GONE
            } else {
                binding.shimmerFrameLayout.stopShimmer()
                binding.shimmerFrameLayout.visibility = View.GONE
            }
        }
        viewModel.listOrder.observe(viewLifecycleOwner) { orderList ->
            val currentStatus = viewModel.currentStatus
            if (currentStatus == null) {
                FSLogger.w("Current status is null. Skipping order list update.")
                return@observe
            }

            val filteredList = orderList.filter { it.status == currentStatus.status }

            if (filteredList.isEmpty()) {
                binding.rcvOrder.visibility = View.GONE
                binding.imgNoOrders.visibility = View.VISIBLE
            } else {
                binding.rcvOrder.visibility = View.VISIBLE
                binding.imgNoOrders.visibility = View.GONE
                orderAdapter.submitList(filteredList)
            }
        }



        viewModel.updateOrderState.observe(this@EnumOrderFragment){
            if(it is DataState.Success){
                activity?.showCustomToast("thanh cong")
            }else{
                activity?.showCustomToast("that bai")
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



}
