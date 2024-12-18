package vn.md18.fsquareapplication.features.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.core.base.EmptyViewHolder
import vn.md18.fsquareapplication.core.recyclerview.BaseRecycleAdapter
import vn.md18.fsquareapplication.data.network.model.response.HistorySearchResponse
import vn.md18.fsquareapplication.databinding.ItemDefaultBinding
import vn.md18.fsquareapplication.databinding.ItemEmptyBinding
import vn.md18.fsquareapplication.databinding.ItemSearchProductBinding
import vn.md18.fsquareapplication.databinding.LayoutListLoadingBinding
import vn.md18.fsquareapplication.utils.extensions.dpToPx
import vn.md18.fsquareapplication.utils.extensions.safeClickWithRx
import vn.md18.fsquareapplication.utils.extensions.viewBinding
import vn.vnpt.ONEHome.core.recycleview.BaseViewHolder

class HistorySearchAdapter : BaseRecycleAdapter<HistorySearchResponse>() {

    private var searchHistoryCallback: SearchHistoryCallback? = null

    /**
     * Set callback for handling search history item interactions
     */
    fun setSearchHistoryCallback(callback: SearchHistoryCallback) {
        this.searchHistoryCallback = callback
    }

    /**
     * Loading ViewHolder to display loading spinner
     */
    override fun setLoadingViewHolder(parent: ViewGroup): BaseViewHolder<*> {
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

    /**
     * Normal ViewHolder to display a history search item
     */
    override fun setNormalViewHolder(parent: ViewGroup): BaseViewHolder<*> {
        return NormalViewHolder(
            ItemSearchProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    /**
     * Error ViewHolder is not implemented for this adapter
     */
    override fun setErrorViewHolder(parent: ViewGroup): BaseViewHolder<*>? {
        return null
    }

    /**
     * Loading ViewHolder implementation
     */
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
    inner class NormalViewHolder(viewbinding: ItemSearchProductBinding) :
        BaseViewHolder<ItemSearchProductBinding>(viewbinding) {

        override fun bindData(position: Int) {
            val item = itemList[position] ?: return
            binding.apply {
                txtShoeName.text = item.keyword
                btnCancelSearch.safeClickWithRx {
                    searchHistoryCallback?.onClickSearchHistoryItem(item._id)
                }
                root.safeClickWithRx {
                    searchHistoryCallback?.onClickHomeInfo(item)
                }
            }
        }
    }



    /**
     * Callback interface for handling interactions with search history items
     */
    interface SearchHistoryCallback {
        fun onClickSearchHistoryItem(itemID: String)
        fun onClickHomeInfo(itemHistoryResponse: HistorySearchResponse)
    }
}
