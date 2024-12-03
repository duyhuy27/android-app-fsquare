package vn.md18.fsquareapplication.features.main.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.Visibility
import dagger.hilt.android.AndroidEntryPoint
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.core.base.BaseActivity
import vn.md18.fsquareapplication.core.base.BaseFragment
import vn.md18.fsquareapplication.data.model.DataState
import vn.md18.fsquareapplication.data.network.model.response.GetOrderRespose
import vn.md18.fsquareapplication.data.network.model.response.order.OrderItem
import vn.md18.fsquareapplication.data.network.model.response.order.ProductDetail
import vn.md18.fsquareapplication.databinding.DialogCancelOrderBinding
import vn.md18.fsquareapplication.databinding.FragmentDetailOrder2Binding
import vn.md18.fsquareapplication.features.checkout.ui.fragment.OrderDetailFragment
import vn.md18.fsquareapplication.features.main.adapter.BagAdapter
import vn.md18.fsquareapplication.features.main.adapter.DetailOrderAdapter
import vn.md18.fsquareapplication.features.main.adapter.OrderAdapter
import vn.md18.fsquareapplication.features.main.viewmodel.MainViewModel
import vn.md18.fsquareapplication.features.main.viewmodel.OrderViewModel
import vn.md18.fsquareapplication.utils.Constant
import vn.md18.fsquareapplication.utils.OrderStatus
import vn.md18.fsquareapplication.utils.extensions.showCustomToast
import vn.md18.fsquareapplication.utils.fslogger.FSLogger
import javax.inject.Inject

@AndroidEntryPoint
class DetailOrderActivity : BaseActivity<FragmentDetailOrder2Binding, OrderViewModel>() {
    override val viewModel: OrderViewModel by viewModels()

    @Inject
    lateinit var orderAdapter: DetailOrderAdapter

    private var orderId = ""
    private var status = ""
    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentDetailOrder2Binding =
        FragmentDetailOrder2Binding.inflate(layoutInflater)

    override fun onViewLoaded() {
        intent?.getBundleExtra(Constant.KEY_BUNDLE)?.apply {
            getString(Constant.KEY_PRODUCT)?.let {
                viewModel.getOrderById(it)
                orderId = it
            }
            getString("Status")?.let{
                status = it
            }

        }

        if(status == OrderStatus.DELIVERED.status){
            binding.apply {
                txtStatusShipping.text = "Đơn hàng đang giao đến chỗ bạn"
            }
        }else if(status == OrderStatus.SHIPPED.status){
            binding.apply {
                txtStatusShipping.text = "Đơn hàng đang vận chuyển"
            }
        }else if(status == OrderStatus.PENDING.status){
            binding.apply {
                txtStatusShipping.text = "Đơn hàng đang chờ xác nhận"
            }
        }else if(status == OrderStatus.PROCESSING.status){
            binding.apply {
                txtStatusShipping.text = "Đơn hàng đang chuẩn bị"
            }
        }else if(status == OrderStatus.CONFIRMED.status){
            binding.apply {
                txtStatusOrder.text = "Đơn hàng đã hoàn thành"
                txtStatusShipping.text = "Đơn hàng đã được giao"
                btnReturn.text = "Trả hàng"
            }
        }else if(status == OrderStatus.CANCELED.status){
            binding.apply {
                txtStatusShipping.text = "Đơn hàng đã bị hủy"
            }
        }

        binding.apply {
            rcvDetailOrder.apply {
                layoutManager = LinearLayoutManager(this@DetailOrderActivity, LinearLayoutManager.VERTICAL, false)
                setHasFixedSize(true)
                adapter = orderAdapter
            }
        }

    }

    override fun addViewListener() {
        binding.apply {
            btnCacel.setOnClickListener {
                showDialog(orderId)
            }
            toolbarDetailOrder.onClickBackPress = {
                val intent = Intent(this@DetailOrderActivity, MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    putExtra("SELECTED_TAB", MainViewModel.TAB_ORDERS)
                }
                startActivity(intent)
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
            }
        }
    }

    override fun addDataObserver() {
        viewModel.getDetailOrderState.observe(this@DetailOrderActivity) {
                binding.apply {
                    txtClientOrderCode.text = "GHN ${it.orderID}"
                    val statusTimestamps = it.statusTimestamps
                    val currentStatusTime = when {
                        !statusTimestamps.pending.isNullOrEmpty() -> "${formatTime(statusTimestamps.pending)}"
                        !statusTimestamps.processing.isNullOrEmpty() -> "${formatTime(statusTimestamps.processing)}"
                        !statusTimestamps.shipped.isNullOrEmpty() -> "${formatTime(statusTimestamps.shipped)}"
                        !statusTimestamps.delivered.isNullOrEmpty() -> "${formatTime(statusTimestamps.delivered)}"
                        !statusTimestamps.confirmed.isNullOrEmpty() -> "${formatTime(statusTimestamps.confirmed)}"
                        !statusTimestamps.cancelled.isNullOrEmpty() -> "${formatTime(statusTimestamps.cancelled)}"
                        !statusTimestamps.returned.isNullOrEmpty() -> "${formatTime(statusTimestamps.returned)}"
                        else -> "Không có trạng thái nào"
                    }

                    txtTime.text = currentStatusTime

                    txtNameShipping.text = "${it.shippingAddress.name} (${it.shippingAddress.tel})"
                    txtAddress.text = "${it.shippingAddress.ward}, ${it.shippingAddress.district}, ${it.shippingAddress.province}"
                    val orderItems = it.products.map { product ->
                        ProductDetail(
                            size = product.size,
                            shoes = product.shoes,
                            color = product.color,
                            price = product.price,
                            quantity = product.quantity,
                            thumbnail = product.thumbnail
                        )
                    }
                    orderAdapter.submitList(orderItems)
                }
            }

        viewModel.updateOrderState.observe(this@DetailOrderActivity){
            if(it is DataState.Success){
                showCustomToast("Hủy đơn hàng thành công", Constant.ToastStatus.SUCCESS)
                binding.btnCacel.visibility = View.GONE
            }
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            putExtra("SELECTED_TAB", MainViewModel.TAB_ORDERS)
        }
        startActivity(intent)
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
    }

    private fun formatTime(timestamp: String): String {
        return try {
            val time = java.time.ZonedDateTime.parse(timestamp)
            time.toLocalTime().toString()
        } catch (e: Exception) {
            "Không hợp lệ"
        }
    }

    fun showDialog(id: String) {
        val dialogView = LayoutInflater.from(this@DetailOrderActivity).inflate(R.layout.dialog_cancel_order, null)
        val binding = DialogCancelOrderBinding.bind(dialogView)

        val alertDialog = androidx.appcompat.app.AlertDialog.Builder(this@DetailOrderActivity)
            .setView(dialogView)
            .create()

        binding.apply {
            btnCancel.setOnClickListener {
                alertDialog.dismiss()
            }
            btnConfirm.setOnClickListener {
                viewModel.updateOrder(id, OrderStatus.CANCELED)
                alertDialog.dismiss()
            }

        }

        alertDialog.show()
    }
}