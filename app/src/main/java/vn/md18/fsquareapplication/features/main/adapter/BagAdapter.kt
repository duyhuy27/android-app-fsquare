package vn.md18.fsquareapplication.features.main.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import vn.md18.fsquareapplication.data.network.model.response.bag.GetBagResponse
import vn.md18.fsquareapplication.databinding.ItemProductCartBinding
import vn.md18.fsquareapplication.utils.extensions.loadImageURL
import vn.md18.fsquareapplication.core.recyclerview.BaseRecycleAdapter
import vn.vnpt.ONEHome.core.recycleview.BaseViewHolder
import java.text.DecimalFormat
import javax.inject.Inject

class BagAdapter @Inject constructor() : BaseRecycleAdapter<GetBagResponse>(){

    interface OnBagActionListener {
        fun onRemoveBag(productId: String)
        fun onUpdateQuantityBag(productId: String, action: String)
    }

    private var bagActionListener: OnBagActionListener? = null

    fun setBagActionListener(listener: OnBagActionListener) {
        bagActionListener = listener
    }
    override fun setLoadingViewHolder(parent: ViewGroup): BaseViewHolder<*>? {
        return null
    }

    override fun setEmptyViewHolder(parent: ViewGroup): BaseViewHolder<*>? {
        return null
    }

    override fun setNormalViewHolder(parent: ViewGroup): BaseViewHolder<*>? {
        return NormalViewHolder(
            ItemProductCartBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun setErrorViewHolder(parent: ViewGroup): BaseViewHolder<*>? {
        return null
    }

    inner class NormalViewHolder(viewBinding: ItemProductCartBinding) :
        BaseViewHolder<ItemProductCartBinding>(viewBinding) {
        @SuppressLint("SetTextI18n")
        override fun bindData(position: Int) {
            val product: GetBagResponse = itemList[position]
            binding.apply {
                txtProductNameCart.text = product.shoes.name
                txtProductColorCart.text = product.color
                val formatter: DecimalFormat = DecimalFormat("#,###")
                txtProductPriceCart.text = formatter.format(product.price)
                txtProductSizeCart.text = product.size.sizeNumber
                txtProductQuantityCart.text = product.quantity.toString()
                imgCart.loadImageURL(product.thumbnail?.url)

                btnProductPlusCart.setOnClickListener {
                    bagActionListener?.onUpdateQuantityBag(product._id, "increase")
                }
                btnProductMinusCart.setOnClickListener {
                    bagActionListener?.onUpdateQuantityBag(product._id, "decrease")
                }

                btnProductDeleteCart.setOnClickListener {
                    bagActionListener?.onRemoveBag(product._id)
                }
            }
        }
    }
}