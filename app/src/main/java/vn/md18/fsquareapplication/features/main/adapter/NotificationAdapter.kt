package vn.md18.fsquareapplication.features.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.core.base.EmptyViewHolder
import vn.md18.fsquareapplication.core.recyclerview.BaseRecycleAdapter
import vn.md18.fsquareapplication.data.network.model.response.NotificationResponse
import vn.md18.fsquareapplication.databinding.ItemEmptyBinding
import vn.md18.fsquareapplication.databinding.ItemNotificationBinding
import vn.md18.fsquareapplication.databinding.LayoutListLoadingBinding
import vn.md18.fsquareapplication.utils.extensions.dpToPx
import vn.md18.fsquareapplication.utils.extensions.viewBinding
import vn.vnpt.ONEHome.core.recycleview.BaseViewHolder

class NotificationAdapter : BaseRecycleAdapter<NotificationResponse>() {

    private var notificationCallback: NotificationCallback? = null

    interface NotificationCallback {
        fun onNotificationClick(notification: NotificationResponse)
    }

    fun setNotificationCallback(callback: NotificationCallback) {
        this.notificationCallback = callback
    }
    override fun setLoadingViewHolder(parent: ViewGroup): BaseViewHolder<*>? {
        return LoadingViewHolder(
            LayoutListLoadingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun setEmptyViewHolder(parent: ViewGroup): BaseViewHolder<*> = EmptyViewHolder(
        parent.viewBinding(ItemEmptyBinding::inflate),
        parent.context.getString(R.string.history_adapter_empty_list),
        imageEmpty = R.drawable.ic_no_order,
        sizeIcon = 130F
    )

    override fun setNormalViewHolder(parent: ViewGroup): BaseViewHolder<*>? {
        return NormalViewHolder(
            ItemNotificationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun setErrorViewHolder(parent: ViewGroup): BaseViewHolder<*>? {
        return null
    }

    class LoadingViewHolder(loadingViewHolder: LayoutListLoadingBinding) :
        BaseViewHolder<LayoutListLoadingBinding>(loadingViewHolder) {

        init {
            val params = binding.root.layoutParams as ViewGroup.MarginLayoutParams
            params.topMargin = dpToPx(40f)
            binding.root.layoutParams = params
        }

        override fun bindData(position: Int) {
        }
    }

    /**
     * Normal ViewHolder implementation for displaying search history items
     */
    inner class NormalViewHolder(viewbinding: ItemNotificationBinding) :
        BaseViewHolder<ItemNotificationBinding>(viewbinding) {

        override fun bindData(position: Int) {
            val item = itemList[position] ?: return
            binding.apply {
                tvTitle.text = item.title
                tvDescription.text = item.content
            }
        }
    }
}