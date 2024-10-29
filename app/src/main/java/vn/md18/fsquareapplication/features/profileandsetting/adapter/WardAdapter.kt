package vn.md18.fsquareapplication.features.profileandsetting.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import vn.md18.fsquareapplication.data.network.model.response.GetDistrictsResponse
import vn.md18.fsquareapplication.data.network.model.response.GetWardsRepose
import vn.md18.fsquareapplication.databinding.ItemProvinceBinding
import vn.vnpt.ONEHome.core.recycleview.BaseRecycleAdapter
import vn.vnpt.ONEHome.core.recycleview.BaseViewHolder
import javax.inject.Inject

class WardAdapter @Inject constructor() : BaseRecycleAdapter<GetWardsRepose>() {
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
            val province: GetWardsRepose = itemList[position]
            binding.apply {
                txtProvince.text = province.wardName
            }
            binding.root.setOnClickListener {
                onItemClickListener?.invoke(province.wardName)
            }
        }

    }

    private var onItemClickListener: ((String) -> Unit)? = null

    // Setter để thiết lập callback từ bên ngoài
    fun setOnItemClickListener(listener: (String) -> Unit) {
        onItemClickListener = listener
    }
}