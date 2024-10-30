package vn.md18.fsquareapplication.features.profileandsetting.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import vn.md18.fsquareapplication.data.network.model.response.GetProvinceResponse
import vn.md18.fsquareapplication.data.network.model.response.location.GetLocationCustomerResponse
import vn.md18.fsquareapplication.databinding.ItemProvinceBinding
import vn.md18.fsquareapplication.databinding.ItemRecyclerViewAddressBinding
import vn.md18.fsquareapplication.databinding.ItemShippingAddressBinding
import vn.vnpt.ONEHome.core.recycleview.BaseRecycleAdapter
import vn.vnpt.ONEHome.core.recycleview.BaseViewHolder
import javax.inject.Inject

class LocationCustomerAdapter @Inject constructor() : BaseRecycleAdapter<GetLocationCustomerResponse>(){
    private var onItemClickListener: ((GetLocationCustomerResponse) -> Unit)? = null
    fun setOnItemClickListener(listener: (GetLocationCustomerResponse) -> Unit) {
        onItemClickListener = listener
    }

    override fun setLoadingViewHolder(parent: ViewGroup): BaseViewHolder<*>? {
        return null
    }

    override fun setEmptyViewHolder(parent: ViewGroup): BaseViewHolder<*>? {
        return null
    }

    override fun setNormalViewHolder(parent: ViewGroup): BaseViewHolder<*>? {
        return NormalViewHolder(
            ItemRecyclerViewAddressBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun setErrorViewHolder(parent: ViewGroup): BaseViewHolder<*>? {
        return null
    }

    inner class NormalViewHolder(binding: ItemRecyclerViewAddressBinding) :
        BaseViewHolder<ItemRecyclerViewAddressBinding>(binding) {

        @SuppressLint("SetTextI18n")
        override fun bindData(position: Int) {
            val location: GetLocationCustomerResponse = itemList[position]
            binding.apply {
                txtHome.text = location.title
                txtAddress.text = location.address + ", " + location.wardName + ", " + location.districtName + ", " + location.provinceName
                if(location.isDefault){
                    txtDefault.text = "mac dinh"
                }else{
                    txtDefault.text = null
                }
                root.setOnClickListener {
                    onItemClickListener?.invoke(location)
                }
            }
        }
    }
}