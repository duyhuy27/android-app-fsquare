package vn.md18.fsquareapplication.features.main.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.data.network.model.response.GetOrderRespose
import vn.md18.fsquareapplication.databinding.ItemProductOrderBinding
import vn.md18.fsquareapplication.features.main.viewmodel.OrderViewModel
import vn.md18.fsquareapplication.utils.OrderStatus
import vn.md18.fsquareapplication.utils.extensions.loadImageURL
import vn.vnpt.ONEHome.core.recycleview.BaseRecycleAdapter
import vn.vnpt.ONEHome.core.recycleview.BaseViewHolder
import javax.inject.Inject

class OrderAdapter @Inject constructor() : BaseRecycleAdapter<GetOrderRespose>() {

    private lateinit var viewModel: OrderViewModel

    fun setViewModel(viewModel: OrderViewModel) {
        this.viewModel = viewModel
    }

    override fun setLoadingViewHolder(parent: ViewGroup): BaseViewHolder<*>? {
        return null
    }

    override fun setEmptyViewHolder(parent: ViewGroup): BaseViewHolder<*>? {
        return null
    }

    override fun setNormalViewHolder(parent: ViewGroup): BaseViewHolder<*>? {
        return NormalViewHolder(
            ItemProductOrderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun setErrorViewHolder(parent: ViewGroup): BaseViewHolder<*>? {
        return null
    }

    inner class NormalViewHolder(viewBinding: ItemProductOrderBinding) :
        BaseViewHolder<ItemProductOrderBinding>(viewBinding) {

        @SuppressLint("SetTextI18n")
        override fun bindData(position: Int) {
            val order: GetOrderRespose = itemList[position]
            binding.apply {

                when (order.status) {
                    "Pending", "Processing" -> {
                        btnVerify.text = "Cancel"
                        btnVerify.setBackgroundResource(R.drawable.bg_button_cancel)
                        btnVerify.setTextColor(Color.WHITE)
                        btnVerify.visibility = View.VISIBLE
                        btnVerify.setOnClickListener {
                            showCancelDialog(order)
                        }
                    }
                    "Shipped", "Canceled" -> {
                        btnVerify.visibility = View.GONE
                    }
                    "Delivered" -> {
                        btnVerify.text = "Review"
                        btnVerify.setBackgroundResource(R.drawable.bg_button_cancel)
                        btnVerify.setTextColor(Color.WHITE)
                        btnVerify.visibility = View.VISIBLE
                        btnVerify.setOnClickListener { }
                    }
                    else -> {
                        btnVerify.visibility = View.GONE
                    }
                }

                txtProductNameCart.text = order.firstProduct.name
                txtProductPriceCart.text = "${order.firstProduct.price} VND"
            }
        }

        private fun showCancelDialog(order: GetOrderRespose) {
            val context = binding.root.context
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Cancel Order")
                .setMessage("Are you sure you want to cancel this order?")
                .setPositiveButton("OK") { _, _ ->
                    viewModel.updateOrder(order.id, OrderStatus.CANCELED)
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()
                .show()
        }
    }
}
