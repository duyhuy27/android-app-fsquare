package vn.md18.fsquareapplication.features.checkout.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import vn.md18.fsquareapplication.data.network.model.response.location.GetLocationCustomerResponse
import vn.md18.fsquareapplication.databinding.ItemShippingAddressBinding
import vn.md18.fsquareapplication.core.recyclerview.BaseRecycleAdapter
import vn.vnpt.ONEHome.core.recycleview.BaseViewHolder
import javax.inject.Inject

class ShippingAddressAdapter @Inject constructor() : BaseRecycleAdapter<GetLocationCustomerResponse>() {
    private var selectedPosition: Int = 0

    private var onItemClickListener: ((GetLocationCustomerResponse) -> Unit)? = null
    fun setOnItemClickListener(listener: (GetLocationCustomerResponse) -> Unit) {
        onItemClickListener = listener
    }

    override fun setLoadingViewHolder(parent: ViewGroup): BaseViewHolder<*>? = null
    override fun setEmptyViewHolder(parent: ViewGroup): BaseViewHolder<*>? = null
    override fun setErrorViewHolder(parent: ViewGroup): BaseViewHolder<*>? = null

    override fun setNormalViewHolder(parent: ViewGroup): BaseViewHolder<*>? {
        return NormalViewHolder(
            ItemShippingAddressBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class NormalViewHolder(binding: ItemShippingAddressBinding) :
        BaseViewHolder<ItemShippingAddressBinding>(binding) {

        @SuppressLint("SetTextI18n")
        override fun bindData(position: Int) {
            val location: GetLocationCustomerResponse = itemList[position]
            binding.apply {
                txtHome.text = location.title
                txtAddress.text = "${location.address}, ${location.wardName}, ${location.districtName}, ${location.provinceName}"
                if (location.isDefault) {
                    txtDefault.text = "mặc định"
                } else {
                    txtDefault.text = null
                }
                chkIsChoose.isChecked = position == selectedPosition
                chkIsChoose.setOnClickListener {
                    if (position != selectedPosition) {
                        val previousPosition = selectedPosition
                        selectedPosition = position
                        notifyItemChanged(previousPosition)
                        notifyItemChanged(selectedPosition)
                        onItemClickListener?.invoke(location)
                    }
                }
            }
        }
    }
}
