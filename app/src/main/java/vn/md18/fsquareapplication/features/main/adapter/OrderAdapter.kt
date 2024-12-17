package vn.md18.fsquareapplication.features.main.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.data.network.model.response.GetOrderRespose
import vn.md18.fsquareapplication.databinding.ItemProductOrderBinding
import vn.md18.fsquareapplication.utils.OrderStatus
import vn.md18.fsquareapplication.utils.extensions.loadImageURL
import vn.md18.fsquareapplication.core.recyclerview.BaseRecycleAdapter
import vn.vnpt.ONEHome.core.recycleview.BaseViewHolder
import java.text.DecimalFormat
import javax.inject.Inject

class OrderAdapter @Inject constructor() : BaseRecycleAdapter<GetOrderRespose>() {

    interface OnOrderActionListener{
        fun onUpdateOrder(id: String, status: OrderStatus)
        fun showDialog(order: GetOrderRespose)

        fun showDialogConfirm(order: GetOrderRespose)

        fun navigate(id: String, status: String)

        fun review(order: GetOrderRespose)
    }

    private var orderActionListener: OnOrderActionListener? = null
    private var loading: Boolean = false
    fun setLoading(isLoading: Boolean) {
        loading = isLoading
        notifyDataSetChanged()
    }

    fun setOrderActionListener(listener: OnOrderActionListener) {
        orderActionListener = listener
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

                root.setOnClickListener {
                    orderActionListener?.navigate(order.id, order.status)
                }
                when (order.status) {
                    "pending" -> {
                        btnVerify.text = "Hủy hàng"
                        btnVerify.setBackgroundResource(R.drawable.bg_button_cancel)
                        btnVerify.setTextColor(Color.WHITE)
                        btnVerify.visibility = View.VISIBLE
                        btnVerify.setOnClickListener {
                            orderActionListener?.showDialog(order)
                        }
                    }
                    "shipped", "canceled", "processing", "returned" -> {
                        btnVerify.visibility = View.GONE
                    }
                    "delivered" -> {
                        btnVerify.text = "Đã nhận"
                        btnVerify.setBackgroundResource(R.drawable.bg_button_cancel)
                        btnVerify.setTextColor(Color.WHITE)
                        btnVerify.visibility = View.VISIBLE
                        btnVerify.setOnClickListener {
                            orderActionListener?.showDialogConfirm(order)
                        }
                    }
                    "confirmed" -> {
                        if(!order.isReview){
                            btnVerify.text = "Đánh giá"
                            btnVerify.setBackgroundResource(R.drawable.bg_button_cancel)
                            btnVerify.setTextColor(Color.WHITE)
                            btnVerify.visibility = View.VISIBLE
                        }else{
                            btnVerify.visibility = View.GONE
                        }

                        btnVerify.setBackgroundResource(R.drawable.bg_button_cancel)
                        btnVerify.setTextColor(Color.WHITE)
                        btnVerify.visibility = View.VISIBLE
                        btnVerify.setOnClickListener {
                            orderActionListener?.review(order)
                        }
                    }
                    else -> {
                        btnVerify.visibility = View.GONE
                    }
                }

                txtProductNameCart.text = order.firstProduct.name
                val formatter: DecimalFormat = DecimalFormat("#,###")
                txtProductPriceCart.text = formatter.format(order.firstProduct.price)
                imgCart.loadImageURL(order.firstProduct.thumbnail?.url)
            }
        }
    }

    fun clearData() {
        itemList.clear()
        notifyDataSetChanged()
    }

}
