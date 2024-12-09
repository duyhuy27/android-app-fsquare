package vn.md18.fsquareapplication.features.main.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import vn.md18.fsquareapplication.data.network.model.response.bag.GetBagResponse
import vn.md18.fsquareapplication.data.network.model.response.order.GetOrderDetailResponse
import vn.md18.fsquareapplication.data.network.model.response.order.OrderItem
import vn.md18.fsquareapplication.data.network.model.response.order.ProductDetail
import vn.md18.fsquareapplication.databinding.ItemOrderListCheckoutBinding
import vn.md18.fsquareapplication.utils.extensions.loadImageURL
import vn.vnpt.ONEHome.core.recycleview.BaseRecycleAdapter
import vn.vnpt.ONEHome.core.recycleview.BaseViewHolder
import java.text.DecimalFormat
import javax.inject.Inject

class DetailOrderAdapter @Inject constructor() : BaseRecycleAdapter<ProductDetail>(){
    override fun setLoadingViewHolder(parent: ViewGroup): BaseViewHolder<*>? {
        return null
    }

    override fun setEmptyViewHolder(parent: ViewGroup): BaseViewHolder<*>? {
        return null
    }

    override fun setNormalViewHolder(parent: ViewGroup): BaseViewHolder<*>? {
        return NormalViewHolder(
            ItemOrderListCheckoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun setErrorViewHolder(parent: ViewGroup): BaseViewHolder<*>? {
        return null
    }

    inner class NormalViewHolder(viewBinding: ItemOrderListCheckoutBinding) :
        BaseViewHolder<ItemOrderListCheckoutBinding>(viewBinding) {
        @SuppressLint("SetTextI18n")
        override fun bindData(position: Int) {
            val product: ProductDetail = itemList[position]
            binding.apply {
                txtProductNameOrderList.text = product.shoes
                txtProductColorOrderList.text = product.color
                val formatter: DecimalFormat = DecimalFormat("#,###")
                txtProductPriceOrderList.text = formatter.format(product.price)
                txtProductSizeOrderList.text = product.size.toString()
                txtProductQuantityOrderList.text = product.quantity.toString()
                imgCart.loadImageURL(product.thumbnail?.url)
            }
        }

    }
}