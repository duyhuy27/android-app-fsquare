package vn.md18.fsquareapplication.features.profileandsetting.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import vn.md18.fsquareapplication.data.network.model.response.GetProvinceResponse
import vn.md18.fsquareapplication.databinding.ItemProvinceBinding
import vn.vnpt.ONEHome.core.recycleview.BaseRecycleAdapter
import vn.vnpt.ONEHome.core.recycleview.BaseViewHolder
import javax.inject.Inject

class ProvinceAdapter @Inject constructor() : BaseRecycleAdapter<GetProvinceResponse>() {

    private var onItemClickListener: ((GetProvinceResponse) -> Unit)? = null

    // Hàm thiết lập callback từ bên ngoài cho sự kiện click
    fun setOnItemClickListener(listener: (GetProvinceResponse) -> Unit) {
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

    inner class NormalViewHolder(binding: ItemProvinceBinding) :
        BaseViewHolder<ItemProvinceBinding>(binding) {

        @SuppressLint("SetTextI18n")
        override fun bindData(position: Int) {
            val province: GetProvinceResponse = itemList[position]
            binding.apply {
                txtProvince.text = province.provinceName

                // Thiết lập sự kiện click và truyền toàn bộ đối tượng `GetProvinceResponse`
                root.setOnClickListener {
                    onItemClickListener?.invoke(province)
                }
            }
        }
    }
}
