package vn.md18.fsquareapplication.features.main.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import vn.md18.fsquareapplication.data.network.model.response.ProductResponse
import vn.md18.fsquareapplication.databinding.ItemProductBannerDashboardBinding
import vn.md18.fsquareapplication.utils.extensions.loadImageURL
import vn.md18.fsquareapplication.utils.extensions.loadImageUri
import vn.vnpt.ONEHome.core.recycleview.BaseRecycleAdapter
import vn.vnpt.ONEHome.core.recycleview.BaseViewHolder
import java.text.DecimalFormat
import javax.inject.Inject

class ProductBannerAdapter @Inject constructor() : BaseRecycleAdapter<ProductResponse>() {
    override fun setLoadingViewHolder(parent: ViewGroup): BaseViewHolder<*>? {
        return null
    }

    override fun setEmptyViewHolder(parent: ViewGroup): BaseViewHolder<*>? {
        return null
    }

    override fun setNormalViewHolder(parent: ViewGroup): BaseViewHolder<*>? {
        return NormalViewHolder(
            ItemProductBannerDashboardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun setErrorViewHolder(parent: ViewGroup): BaseViewHolder<*>? {
        return null
    }

    inner class NormalViewHolder(viewBinding: ItemProductBannerDashboardBinding) :
        BaseViewHolder<ItemProductBannerDashboardBinding>(viewBinding) {
        @SuppressLint("SetTextI18n")
        override fun bindData(position: Int) {
            val product: ProductResponse = itemList[position]
            binding.apply {
                val formatter: DecimalFormat = DecimalFormat("#,###")
                tvPrice.text = formatter.format(product.maxPrice) + " VND"
                tvTitle.text = product.name
                imgProduct.loadImageURL(product.thumbnail?.url)
            }
        }

    }

}