package vn.md18.fsquareapplication.features.detail.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.data.network.model.response.ClassificationShoes
import vn.md18.fsquareapplication.data.network.model.response.GetClassificationResponse
import vn.md18.fsquareapplication.data.network.model.response.location.GetLocationCustomerResponse
import vn.md18.fsquareapplication.databinding.ItemColorDatailProduxBinding
import vn.md18.fsquareapplication.databinding.ItemShippingAddressBinding
import vn.vnpt.ONEHome.core.recycleview.BaseRecycleAdapter
import vn.vnpt.ONEHome.core.recycleview.BaseViewHolder
import javax.inject.Inject

fun interface OnColorClickListener {
    fun onColorClick(response: GetClassificationResponse)
}

class ColorDetailAdapter @Inject constructor() : BaseRecycleAdapter<GetClassificationResponse>() {
    private var selectedPosition: Int = -1
    private var onColorClickListener: OnColorClickListener? = null

    fun setOnColorClickListener(listener: OnColorClickListener) {
        onColorClickListener = listener
    }

    override fun setLoadingViewHolder(parent: ViewGroup): BaseViewHolder<*>? {
        return null
    }

    override fun setEmptyViewHolder(parent: ViewGroup): BaseViewHolder<*>? {
        return null
    }

    override fun setNormalViewHolder(parent: ViewGroup): BaseViewHolder<*>? {
        return NormalViewHolder(
            ItemColorDatailProduxBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun setErrorViewHolder(parent: ViewGroup): BaseViewHolder<*>? {
        TODO("Not yet implemented")
    }

    inner class NormalViewHolder(binding: ItemColorDatailProduxBinding) :
        BaseViewHolder<ItemColorDatailProduxBinding>(binding) {

        @SuppressLint("SetTextI18n")
        override fun bindData(position: Int) {
            val response: GetClassificationResponse = itemList[position]
            binding.apply {
                buttonColorDetailProduct.text = response.color

                if (position == selectedPosition) {
                    buttonColorDetailProduct.setBackgroundResource(R.drawable.button_color_selected)
                    buttonColorDetailProduct.setTextColor(
                        buttonColorDetailProduct.context.getColor(R.color.white)
                    )
                } else {
                    buttonColorDetailProduct.setBackgroundResource(R.drawable.button_color_datial_product)
                    buttonColorDetailProduct.setTextColor(
                        buttonColorDetailProduct.context.getColor(R.color.primary)
                    )
                }

                // Xử lý sự kiện click
                buttonColorDetailProduct.setOnClickListener {
                    selectedPosition = position
                    notifyDataSetChanged()
                    onColorClickListener?.onColorClick(response)
                }
            }
        }
    }

}

