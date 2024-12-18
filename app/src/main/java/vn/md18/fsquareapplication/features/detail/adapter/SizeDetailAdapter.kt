package vn.md18.fsquareapplication.features.detail.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.data.network.model.response.ClassificationShoes
import vn.md18.fsquareapplication.databinding.ItemSizeDatailProduxBinding
import vn.md18.fsquareapplication.core.recyclerview.BaseRecycleAdapter
import vn.vnpt.ONEHome.core.recycleview.BaseViewHolder
import javax.inject.Inject

fun interface OnSizeClickListener {
    fun onSizeClick(response: ClassificationShoes)
}
class SizeDetailAdapter @Inject constructor() : BaseRecycleAdapter<ClassificationShoes>() {
    private var selectedPosition: Int = -1
    private var onSizeClickListener: OnSizeClickListener? = null

    fun setOnSizeClickListener(listener: OnSizeClickListener) {
        onSizeClickListener = listener
    }

    override fun setLoadingViewHolder(parent: ViewGroup): BaseViewHolder<*>? {
        return null
    }

    override fun setEmptyViewHolder(parent: ViewGroup): BaseViewHolder<*>? {
        return null
    }

    override fun setNormalViewHolder(parent: ViewGroup): BaseViewHolder<*>? {
        return NormalViewHolder(
            ItemSizeDatailProduxBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun setErrorViewHolder(parent: ViewGroup): BaseViewHolder<*>? {
        TODO("Not yet implemented")
    }

    inner class NormalViewHolder(binding: ItemSizeDatailProduxBinding) :
        BaseViewHolder<ItemSizeDatailProduxBinding>(binding) {

        @SuppressLint("SetTextI18n")
        override fun bindData(position: Int) {
            val response: ClassificationShoes = itemList[position]
            binding.apply {
                buttonSizeDetailProduct.text = response.sizeNumber

                if (position == selectedPosition) {
                    buttonSizeDetailProduct.setBackgroundResource(R.drawable.size_selected)
                    buttonSizeDetailProduct.setTextColor(
                        buttonSizeDetailProduct.context.getColor(R.color.white)
                    )
                } else {
                    buttonSizeDetailProduct.setBackgroundResource(R.drawable.button_border_size)
                    buttonSizeDetailProduct.setTextColor(
                        buttonSizeDetailProduct.context.getColor(R.color.primary)
                    )
                }
                buttonSizeDetailProduct.setOnClickListener {
                    selectedPosition = position
                    notifyDataSetChanged()
                    onSizeClickListener?.onSizeClick(response)
                }
            }
        }
    }

}

