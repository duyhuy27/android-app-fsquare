package vn.md18.fsquareapplication.features.main.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ListAdapter
import vn.md18.fsquareapplication.data.network.model.response.ProductResponse
import vn.md18.fsquareapplication.databinding.ItemProductBannerDashboardBinding
import vn.md18.fsquareapplication.databinding.ItemProductBinding
import vn.md18.fsquareapplication.utils.extensions.loadImageURL
import vn.vnpt.ONEHome.core.recycleview.BaseRecycleAdapter
import vn.vnpt.ONEHome.core.recycleview.BaseViewHolder
import javax.inject.Inject

class ProductAdapter @Inject constructor() : BaseRecycleAdapter<ProductResponse>(){
    override fun setLoadingViewHolder(parent: ViewGroup): BaseViewHolder<*>? {
        return null
    }

    override fun setEmptyViewHolder(parent: ViewGroup): BaseViewHolder<*>? {
        return null
    }

    override fun setNormalViewHolder(parent: ViewGroup): BaseViewHolder<*>? {
        return NormalViewHolder(
            ItemProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun setErrorViewHolder(parent: ViewGroup): BaseViewHolder<*>? {
        return null
    }

    inner class NormalViewHolder(viewBinding: ItemProductBinding) :
        BaseViewHolder<ItemProductBinding>(viewBinding) {
        @SuppressLint("SetTextI18n")
        override fun bindData(position: Int) {
            val product: ProductResponse = itemList[position]
            binding.apply {
                txtProductPrice.text = product.maxPrice.toString() + " $"
                txtProductName.text = product.name
                imgProduct.loadImageURL(product.thumbnail.url)
            }
        }

    }

}