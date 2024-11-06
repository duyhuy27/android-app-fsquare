package vn.md18.fsquareapplication.features.main.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import vn.md18.fsquareapplication.data.network.model.response.GetOrderRespose
import vn.md18.fsquareapplication.databinding.ItemTransactionHistoryBinding
import vn.md18.fsquareapplication.utils.extensions.loadImageURL
import vn.vnpt.ONEHome.core.recycleview.BaseRecycleAdapter
import vn.vnpt.ONEHome.core.recycleview.BaseViewHolder
import javax.inject.Inject

class OrderAdapter @Inject constructor() : BaseRecycleAdapter<GetOrderRespose>(){
    override fun setLoadingViewHolder(parent: ViewGroup): BaseViewHolder<*>? {
        return null
    }

    override fun setEmptyViewHolder(parent: ViewGroup): BaseViewHolder<*>? {
        return null
    }

    override fun setNormalViewHolder(parent: ViewGroup): BaseViewHolder<*>? {
        return NormalViewHolder(
            ItemTransactionHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun setErrorViewHolder(parent: ViewGroup): BaseViewHolder<*>? {
        return null
    }


    inner class NormalViewHolder(viewBinding: ItemTransactionHistoryBinding) :
        BaseViewHolder<ItemTransactionHistoryBinding>(viewBinding) {
        @SuppressLint("SetTextI18n")
        override fun bindData(position: Int) {
            val order: GetOrderRespose = itemList[position]
            binding.apply {
                imgItemWallet.loadImageURL(order.firstProduct.thumbnail)
                txtProductNameWallet.text= order.firstProduct.name
                txtProductPriceWallet.text= "$"+ order.firstProduct.price.toString()
                txtProductDateWallet.text = order.createdAt
        }

    }
}
}