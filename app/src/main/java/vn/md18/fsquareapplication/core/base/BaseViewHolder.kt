package vn.md18.fsquareapplication.core.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import vn.md18.fsquareapplication.databinding.ItemEmptyBinding
import vn.md18.fsquareapplication.utils.extensions.dpToPx
import vn.md18.fsquareapplication.utils.extensions.getScreenHeight
import vn.md18.fsquareapplication.utils.extensions.hide
import vn.md18.fsquareapplication.utils.extensions.loadImageDrawable
import vn.md18.fsquareapplication.utils.extensions.setHeight
import vn.md18.fsquareapplication.utils.extensions.setWidthHeightToView
import vn.md18.fsquareapplication.utils.extensions.show
import vn.vnpt.ONEHome.core.recycleview.BaseViewHolder

abstract class BaseViewHolder<T : ViewBinding>(open val binding: T) : RecyclerView.ViewHolder(binding.root) {
    abstract fun bindData(position: Int)
}

class EmptyViewHolder : BaseViewHolder<ItemEmptyBinding> {

    constructor(itemEmptyBinding: ItemEmptyBinding) : super(itemEmptyBinding)

    constructor(itemEmptyBinding: ItemEmptyBinding, contentEmpty: String, imageEmpty: Int) : super(itemEmptyBinding) {
        binding.tvErrorContent.text = contentEmpty
        if (imageEmpty == 0) {
            binding.ivEmpty.hide()
        } else {
            binding.ivEmpty.visibility= View.INVISIBLE
            binding.ivEmpty.loadImageDrawable(imageEmpty)
        }
    }
    constructor(itemEmptyBinding: ItemEmptyBinding, contentEmpty: String) : super(itemEmptyBinding) {
        binding.tvErrorContent.text = contentEmpty
        binding.root.setHeight(getScreenHeight()- dpToPx(56F))
    }


    constructor(itemEmptyBinding: ItemEmptyBinding, contentEmpty: String, imageEmpty: Int, sizeIcon: Float) : super(itemEmptyBinding) {
        binding.tvErrorContent.text = contentEmpty
        binding.ivEmpty.apply {
            loadImageDrawable(imageEmpty)
            setWidthHeightToView(dpToPx(sizeIcon), dpToPx(sizeIcon))
        }
    }

    constructor(itemEmptyBinding: ItemEmptyBinding, contentEmpty: String, imageEmpty: Int, sizeIcon: Float, textButton: String) : super(itemEmptyBinding) {
        binding.tvErrorContent.text = contentEmpty
        binding.ivEmpty.apply {
            loadImageDrawable(imageEmpty)
            setWidthHeightToView(dpToPx(sizeIcon), dpToPx(sizeIcon))
        }
        binding.cvAction.show()
        binding.tvAddDevice.text = textButton
    }

    override fun bindData(position: Int) {

    }

}