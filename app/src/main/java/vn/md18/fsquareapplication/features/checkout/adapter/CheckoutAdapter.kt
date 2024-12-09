package vn.md18.fsquareapplication.features.checkout.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import dagger.hilt.android.qualifiers.ApplicationContext
import vn.md18.fsquareapplication.data.network.model.response.bag.GetBagResponse
import vn.md18.fsquareapplication.databinding.ItemOrderListCheckoutBinding
import vn.md18.fsquareapplication.utils.extensions.loadImageURL
import vn.md18.fsquareapplication.core.recyclerview.BaseRecycleAdapter
import vn.vnpt.ONEHome.core.recycleview.BaseViewHolder
import java.text.DecimalFormat
import javax.inject.Inject

class CheckoutAdapter @Inject constructor(
    @ApplicationContext private val context: Context,
) : BaseRecycleAdapter<GetBagResponse>(){

    private var productCallback: OrderCallback? = null

    interface OrderCallback {
        fun onOrderClick(product: GetBagResponse)
    }

    fun setProductCallback(callback: OrderCallback) {
        this.productCallback = callback
    }
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
                txtProductNameOrderList.text = product.shoes.name
                txtProductColorOrderList.text = product.color
                val formatter: DecimalFormat = DecimalFormat("#,###")
                txtProductPriceOrderList.text = formatter.format(product.price)
                txtProductSizeOrderList.text = product.size.sizeNumber
                txtProductQuantityOrderList.text = product.quantity.toString()
                imgCart.loadImageURL(product.thumbnail?.url)

                root.setOnClickListener {
                    productCallback?.onOrderClick(product)
                }
            }
        }

    }
}