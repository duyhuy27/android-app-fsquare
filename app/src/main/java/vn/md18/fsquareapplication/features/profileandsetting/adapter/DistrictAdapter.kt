package vn.md18.fsquareapplication.features.profileandsetting.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import vn.md18.fsquareapplication.data.network.model.response.GetDistrictsResponse
import vn.md18.fsquareapplication.data.network.model.response.GetProvinceResponse
import vn.md18.fsquareapplication.databinding.ItemProvinceBinding
import vn.vnpt.ONEHome.core.recycleview.BaseRecycleAdapter
import vn.vnpt.ONEHome.core.recycleview.BaseViewHolder
import javax.inject.Inject

class DistrictAdapter @Inject constructor() : BaseRecycleAdapter<GetDistrictsResponse>(){
    override fun setLoadingViewHolder(parent: ViewGroup): BaseViewHolder<*>? {
        return null
    }

    override fun setEmptyViewHolder(parent: ViewGroup): BaseViewHolder<*>? {
        return null
    }

    override fun setNormalViewHolder(parent: ViewGroup): BaseViewHolder<*>? {
        return NormalViewHolder(
            ItemProvinceBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun setErrorViewHolder(parent: ViewGroup): BaseViewHolder<*>? {
       return null
    }

    inner class NormalViewHolder(viewBinding: ItemProvinceBinding) :
        BaseViewHolder<ItemProvinceBinding>(viewBinding) {
        @SuppressLint("SetTextI18n")
        override fun bindData(position: Int) {
            val province: GetDistrictsResponse = itemList[position]
            binding.apply {
                txtProvince.text = province.districtName
            }
            binding.root.setOnClickListener {
                onItemClickListener?.invoke(province)
            }
        }

    }

    private var onItemClickListener: ((GetDistrictsResponse) -> Unit)? = null

    // Setter để thiết lập callback từ bên ngoài
    fun setOnItemClickListener(listener: (GetDistrictsResponse) -> Unit) {
        onItemClickListener = listener
    }
}