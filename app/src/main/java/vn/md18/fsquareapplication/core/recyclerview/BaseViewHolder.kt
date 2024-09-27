package vn.vnpt.ONEHome.core.recycleview

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<T : ViewBinding>(open val binding: T) : RecyclerView.ViewHolder(binding.root) {
    abstract fun bindData(position: Int)
}