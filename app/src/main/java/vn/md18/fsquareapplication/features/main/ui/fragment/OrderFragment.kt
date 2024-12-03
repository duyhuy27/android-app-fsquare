package vn.md18.fsquareapplication.features.main.ui.fragment
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.core.base.BaseFragment
import vn.md18.fsquareapplication.data.model.DataState
import vn.md18.fsquareapplication.data.network.model.response.GetOrderRespose
import vn.md18.fsquareapplication.databinding.DialogCancelOrderBinding
import vn.md18.fsquareapplication.databinding.DialogConfirmDeleteFavBinding
import vn.md18.fsquareapplication.databinding.FragmentOrderBinding
import vn.md18.fsquareapplication.features.main.adapter.OrderAdapter
import vn.md18.fsquareapplication.features.main.ui.DetailOrderActivity
import vn.md18.fsquareapplication.features.main.viewmodel.OrderViewModel
import vn.md18.fsquareapplication.utils.Constant
import vn.md18.fsquareapplication.utils.OrderStatus
import vn.md18.fsquareapplication.utils.extensions.showCustomToast

@AndroidEntryPoint
class OrderFragment : BaseFragment<FragmentOrderBinding, OrderViewModel>(), OrderAdapter.OnOrderActionListener {

    override val viewModel: OrderViewModel by activityViewModels()

    private lateinit var orderAdapter: OrderAdapter
    private val tabTitles = listOf("Chờ xác nhận", "Chờ lấy hàng", "Đang vận chuyển", "Đang giao tới", "Đã nhận", "Đã hủy")
    private var selectedStatus: OrderStatus = OrderStatus.PENDING

    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentOrderBinding =
        FragmentOrderBinding.inflate(layoutInflater)

    override fun getTagFragment(): String = OrderFragment::class.java.simpleName

    override fun onViewLoaded() {
        setupTabs()
        setupRecyclerView()
        fetchOrdersByStatus(selectedStatus)
        orderAdapter.setOrderActionListener(this)
    }

    override fun addViewListener() {
        binding.apply {

        }
    }

    private fun setupTabs() {
        val tabsContainer = binding.llTabsContainer

        for ((index, title) in tabTitles.withIndex()) {
            val tabTextView = TextView(context).apply {
                text = title
                setPadding(70, 16, 70, 16)
                gravity = Gravity.CENTER
                textSize = 18f
                setTextColor(Color.BLACK)
                setTypeface(null, Typeface.NORMAL)
                setOnClickListener {
                    updateSelectedTab(index)
                    selectedStatus = OrderStatus.values()[index]
                    fetchOrdersByStatus(selectedStatus)
                }
            }

            val underlineView = View(context).apply {
                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    9
                )
                params.setMargins(0, 10, 0, 0)
                layoutParams = params
                setBackgroundColor(Color.parseColor("#f0f0f0"))
            }

            val tabContainer = LinearLayout(context).apply {
                orientation = LinearLayout.VERTICAL
                gravity = Gravity.CENTER
                addView(tabTextView)
                addView(underlineView)
            }

            tabsContainer.addView(tabContainer)
        }
        updateSelectedTab(0)
    }


    private fun updateSelectedTab(selectedIndex: Int) {
        val tabsContainer = binding.llTabsContainer

        for (i in 0 until tabsContainer.childCount) {
            val tabContainer = tabsContainer.getChildAt(i) as LinearLayout
            val tabTextView = tabContainer.getChildAt(0) as TextView
            val underlineView = tabContainer.getChildAt(1) as View

            tabTextView.setTextColor(Color.BLACK)
            tabTextView.setTypeface(null, Typeface.NORMAL)
            underlineView.setBackgroundColor(Color.parseColor("#ebe6ef"))
            underlineView.layoutParams.height = 9
        }

        val selectedTabContainer = tabsContainer.getChildAt(selectedIndex) as LinearLayout
        val selectedTabTextView = selectedTabContainer.getChildAt(0) as TextView
        val selectedUnderlineView = selectedTabContainer.getChildAt(1) as View

        selectedTabTextView.setTextColor(Color.BLACK)
        selectedTabTextView.setTypeface(null, Typeface.BOLD)
        selectedUnderlineView.setBackgroundColor(Color.parseColor("#FF8C00"))
        selectedUnderlineView.layoutParams.height = 12
    }


    private fun setupRecyclerView() {
        orderAdapter = OrderAdapter()
        binding.rcvOrder.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = orderAdapter
        }
    }

    private fun fetchOrdersByStatus(status: OrderStatus) {
        viewModel.getOrderList(status)
        viewModel.listOrder.observe(this@OrderFragment) { orders ->
            val filteredOrders = orders.filter { it.status == status.status }
            if (filteredOrders.isEmpty()) {
                binding.rcvOrder.visibility = View.GONE
                binding.imgNoOrders.visibility = View.VISIBLE
            } else {
                binding.rcvOrder.visibility = View.VISIBLE
                binding.imgNoOrders.visibility = View.GONE
                orderAdapter.submitList(filteredOrders)
            }
        }
        viewModel.updateOrderState.observe(this@OrderFragment){
            if(it is DataState.Success){
                viewModel.getOrderList(status)
            }else{
                activity?.showCustomToast("huy don hang that bai ", Constant.ToastStatus.FAILURE)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = OrderFragment()
    }

    override fun onUpdateOrder(id: String, status: OrderStatus) {
        viewModel.updateOrder(id, status)
    }

    override fun showDialog(order: GetOrderRespose) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_cancel_order, null)
        val binding = DialogCancelOrderBinding.bind(dialogView)

        val alertDialog = androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        binding.apply {
            btnCancel.setOnClickListener {
                alertDialog.dismiss()
            }
            btnConfirm.setOnClickListener {
                viewModel.updateOrder(order.id, OrderStatus.CANCELED)
                alertDialog.dismiss()
            }

        }

        alertDialog.show()
    }

    override fun showDialogConfirm(order: GetOrderRespose) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_confirm_delete_fav, null)
        val binding = DialogConfirmDeleteFavBinding.bind(dialogView)
        val alertDialog = androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        binding.apply {
            txtTitle.text = "Xác nhận đã nhận hàng"
            txtContent.text = "Bạn đã nhận được hàng giao đến ?"
            btnCancel.setOnClickListener {
                alertDialog.dismiss()
            }
            btnConfirm.setOnClickListener {
                viewModel.updateOrder(order.id, OrderStatus.CONFIRMED)
                alertDialog.dismiss()
            }
        }

        alertDialog.show()
    }

    override fun navigate(id: String, status: String) {
        val data = Bundle().apply {
            putString(Constant.KEY_PRODUCT, id)
            putString("Status", status)
        }
        openActivity(DetailOrderActivity::class.java, data)
    }

    override fun review(order: GetOrderRespose) {
        val orderId = order.id
        val bottomSheet = MyBottomSheetDialog.newInstance(orderId)


        bottomSheet.setOnReviewSubmitListener { rating, comment, files ->

            viewModel.createReviews(files, orderId, rating, comment)
        }

        bottomSheet.show(childFragmentManager, bottomSheet.tag)
    }
}
