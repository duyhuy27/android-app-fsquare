package vn.md18.fsquareapplication.features.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.core.recyclerview.BaseRecycleAdapter
import vn.md18.fsquareapplication.data.network.model.response.ProductResponse
import vn.md18.fsquareapplication.databinding.ItemDefaultBinding
import vn.md18.fsquareapplication.databinding.ItemProductBinding
import vn.md18.fsquareapplication.utils.extensions.loadImageURL
import vn.vnpt.ONEHome.core.recycleview.BaseViewHolder

class SearchProductAdapter : BaseRecycleAdapter<ProductResponse>() {
    private var productCallback: ProductCallback? = null
    override fun setLoadingViewHolder(parent: ViewGroup): BaseViewHolder<*>? {
        return null
    }

    override fun setEmptyViewHolder(parent: ViewGroup): BaseViewHolder<*>? {
        return DefaultViewHolder(
            ItemDefaultBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun setNormalViewHolder(parent: ViewGroup): BaseViewHolder<*>? {
        return NormalViewHolder(
            ItemProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class NormalViewHolder(viewbinding: ItemProductBinding) :
        BaseViewHolder<ItemProductBinding>(viewbinding) {

        override fun bindData(position: Int) {
            val product = itemList[position] ?: return
            binding.apply {
                txtProductPrice.text = "${product.maxPrice} VND"
                txtProductName.text = product.name
                txtRating.text = "${product.rating}"
                txtSale.text = "${product.sales} sold"
                imgProduct.apply {
                    val thumbnailUrl = product.thumbnail?.url
                    if (!thumbnailUrl.isNullOrEmpty()) {
                        loadImageURL(thumbnailUrl)
                    } else {
                        setImageResource(R.drawable.null_shoes)
                    }
                }
                imgAddToFav.setImageResource(
                    if (product.isFavorite) R.drawable.add_to_fav else R.drawable.add_fav
                )
                imgAddToFav.setOnClickListener {
                    val isAdding = !product.isFavorite
                    product.isFavorite = isAdding
                    imgAddToFav.setImageResource(
                        if (isAdding) R.drawable.add_to_fav else R.drawable.add_fav
                    )
                    productCallback?.createFavorite(product._id, isAdding)
                }

                root.setOnClickListener {
                    productCallback?.onProductClick(product)
                }
            }
        }
    }

    override fun setErrorViewHolder(parent: ViewGroup): BaseViewHolder<*>? {
        return null
    }

    interface ProductCallback {
        fun onProductClick(product: ProductResponse)
        fun createFavorite(id:String, isAdding: Boolean)
    }

    fun setProductCallback(callback: ProductCallback) {
        this.productCallback = callback
    }
}