package vn.md18.fsquareapplication.core.recyclerview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import vn.md18.fsquareapplication.databinding.ItemDefaultBinding
import vn.vnpt.ONEHome.core.recycleview.BaseViewHolder
import vn.vnpt.ONEHome.core.recycleview.StateRecycleView

@SuppressLint("NotifyDataSetChanged")
abstract class BaseRecycleAdapter<T : Any> : RecyclerView.Adapter<BaseViewHolder<*>>() {

    /**
     * Companion obj
     * VIEW_TYPE RecycleView
     * ITEM_COUNT RecycleView
     */
    companion object {
        const val VIEW_TYPE_LOADING = 0
        const val VIEW_TYPE_ERROR = 1
        const val VIEW_TYPE_SUCCESS = 2
        const val VIEW_TYPE_DEFAULT = 3
        const val VIEW_TYPE_EMPTY = 4

        const val ITEM_COUNT_TYPE_LOADING = 1
        const val ITEM_COUNT_TYPE_ERROR = 1
        const val ITEM_COUNT_TYPE_DEFAULT = 1
        const val ITEM_COUNT_TYPE_EMPTY = 1
    }

    /**
     * List Items Data
     */
    private var _itemList: MutableList<T> = ArrayList()

    var itemList: MutableList<T>
        get() = _itemList
        set(value) {
            _itemList = value
        }

    /**
     * Variable type recycle view
     * @see BaseViewHolder::class
     */
    private var _typeRecycleView: StateRecycleView = StateRecycleView.STATE_LOADING


    /**
     * Function calling submit new list items and change typeRecycleView -> TYPE_SUCCESS
     */
    open fun submitList(list: List<T>) {
        _itemList.apply {
            clear()
            _itemList.addAll(list)
        }
        this._typeRecycleView = StateRecycleView.STATE_SUCCESS
        notifyDataSetChanged()
    }

    /**
     * Function Calling Delete all item in list and change typeRecycleView -> TYPE_ERROR_OR_EMPTY
     * Not return
     */
    fun clearList() {
        _itemList.clear()
        this._typeRecycleView = StateRecycleView.STATE_EMPTY
        notifyDataSetChanged()

    }

    /**
     *Function setType RecycleView
     * @param stateRecycleView: StateRecycleView
     * Not return
     */
    fun setTypeRecycleView(stateRecycleView: StateRecycleView) {
        this._typeRecycleView = stateRecycleView
        notifyDataSetChanged()
    }

    /**
     * Function set loading to RecycleView
     */

    fun setLoading() {
        this._typeRecycleView = StateRecycleView.STATE_LOADING
        notifyDataSetChanged()
    }

    /**
     * Function set empty view to RecycleView
     */
    fun setEmpty() {
        this._typeRecycleView = StateRecycleView.STATE_EMPTY
        notifyDataSetChanged()
    }

    fun setError() {
        this._typeRecycleView = StateRecycleView.STATE_ERROR
        notifyDataSetChanged()
    }


    /**
     * Function getDefaultViewHolder
     */
    private fun getDefaultViewHolder(parent: ItemDefaultBinding): BaseViewHolder<ItemDefaultBinding> {
        return DefaultViewHolder(parent)
    }

    /**
     * Abstract Function
     * Set Loading custom ViewHolder
     * @param parent: ViewGroup
     * @return @BaseViewHolder
     */
    abstract fun setLoadingViewHolder(parent: ViewGroup): BaseViewHolder<*>?

    /**
     * Abstract Function
     * Set Empty View custom ViewHolder
     * @param parent: ViewGroup
     * @return @BaseViewHolder
     */
    abstract fun setEmptyViewHolder(parent: ViewGroup): BaseViewHolder<*>?

    /**
     * Abstract Function
     * Set SuccessViewHolder View custom ViewHolder
     * @param parent: ViewGroup
     * @return @BaseViewHolder
     */
    abstract fun setNormalViewHolder(parent: ViewGroup): BaseViewHolder<*>?

    /**
     * Abstract Function
     * Set SuccessViewHolder View custom ViewHolder
     * @param parent: ViewGroup
     * @return @BaseViewHolder
     */
    abstract fun setErrorViewHolder(parent: ViewGroup): BaseViewHolder<*>?

    /**
     * Create view holder adapter
     * @params override RecycleViewAdapter
     * @see 4 View type -> LOADING, SUCCESS , ERROR - EMPTY , DEFAULT)
     * @return BaseViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        when (viewType) {
            VIEW_TYPE_LOADING -> {
                return setLoadingViewHolder(parent = parent) ?: getDefaultViewHolder(parent = ItemDefaultBinding.inflate(LayoutInflater.from(parent.context)))
            }
            VIEW_TYPE_SUCCESS -> {
                return setNormalViewHolder(parent = parent) ?: getDefaultViewHolder(parent = ItemDefaultBinding.inflate(LayoutInflater.from(parent.context)))
            }
            VIEW_TYPE_EMPTY -> {
                return setEmptyViewHolder(parent = parent) ?: getDefaultViewHolder(parent = ItemDefaultBinding.inflate(LayoutInflater.from(parent.context)))
            }
            VIEW_TYPE_ERROR -> {
                return setErrorViewHolder(parent = parent) ?: getDefaultViewHolder(parent = ItemDefaultBinding.inflate(LayoutInflater.from(parent.context)))
            }
            VIEW_TYPE_DEFAULT -> {
                return getDefaultViewHolder(parent = ItemDefaultBinding.inflate(LayoutInflater.from(parent.context)))
            }
        }
        return getDefaultViewHolder(parent = ItemDefaultBinding.inflate(LayoutInflater.from(parent.context)))
    }

    /**
     * All action set view calling inside onBind in ViewHolder
     */
    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        holder.bindData(position)
    }


    override fun getItemViewType(position: Int): Int {
        return when (this._typeRecycleView) {
            StateRecycleView.STATE_LOADING -> {
                VIEW_TYPE_LOADING
            }
            StateRecycleView.STATE_SUCCESS -> {
                VIEW_TYPE_SUCCESS
            }
            StateRecycleView.STATE_EMPTY -> {
                VIEW_TYPE_EMPTY
            }
            StateRecycleView.STATE_ERROR -> {
                VIEW_TYPE_ERROR
            }
        }
    }

    override fun getItemCount(): Int {
        return when (this._typeRecycleView) {
            StateRecycleView.STATE_LOADING -> {
                ITEM_COUNT_TYPE_LOADING
            }
            StateRecycleView.STATE_SUCCESS -> {
                _itemList.size
            }
            StateRecycleView.STATE_EMPTY -> {
                ITEM_COUNT_TYPE_EMPTY
            }
            StateRecycleView.STATE_ERROR -> {
                ITEM_COUNT_TYPE_ERROR
            }
        }
    }

    class DefaultViewHolder(viewHolderDefaultBinding: ItemDefaultBinding) : BaseViewHolder<ItemDefaultBinding>(viewHolderDefaultBinding) {

        override fun bindData(position: Int) {

        }

    }

}