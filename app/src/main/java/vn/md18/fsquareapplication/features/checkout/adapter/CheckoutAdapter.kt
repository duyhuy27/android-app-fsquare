package vn.md18.fsquareapplication.features.checkout.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import vn.md18.fsquareapplication.data.network.model.response.GetBagResponse
import vn.md18.fsquareapplication.databinding.ItemOrderListCheckoutBinding
import vn.md18.fsquareapplication.databinding.ItemProductCartBinding
import vn.md18.fsquareapplication.utils.extensions.loadImageURL
import vn.vnpt.ONEHome.core.recycleview.BaseRecycleAdapter
import vn.vnpt.ONEHome.core.recycleview.BaseViewHolder
import javax.inject.Inject

class CheckoutAdapter @Inject constructor() : BaseRecycleAdapter<GetBagResponse>(){
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
            val product: GetBagResponse = itemList[position]
            binding.apply {
                txtProductNameOrderList.text = product.shoes
                txtProductColorOrderList.text = product.color
                txtProductPriceOrderList.text = "$ " + product.price.toString()
                txtProductSizeOrderList.text = product.size
                txtProductQuantityOrderList.text = product.quantity.toString()
                imgCart.loadImageURL(product.thumbnail)
            }
        }

    }
}