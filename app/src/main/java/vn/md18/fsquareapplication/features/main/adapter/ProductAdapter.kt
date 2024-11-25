package vn.md18.fsquareapplication.features.main.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import dagger.hilt.android.qualifiers.ApplicationContext
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.data.network.model.response.ProductResponse
import vn.md18.fsquareapplication.databinding.ItemProductBinding
import vn.md18.fsquareapplication.features.main.ui.FavoriteAndNewestActivity
import vn.md18.fsquareapplication.features.main.viewmodel.FavoriteViewmodel
import vn.md18.fsquareapplication.features.main.viewmodel.MainViewModel
import vn.md18.fsquareapplication.utils.extensions.loadImageURL
import javax.inject.Inject

class ProductAdapter @Inject constructor(
    @ApplicationContext private val context: Context,

) : BaseAdapter() {

    private lateinit var viewModel: MainViewModel
    private var productList: List<ProductResponse> = listOf()
    private var productCallback: ProductCallback? = null
    fun setViewModel(viewModel: MainViewModel) {
        this.viewModel = viewModel
    }

    interface ProductCallback {
        fun onProductClick(product: ProductResponse)
    }

    fun setProductCallback(callback: ProductCallback) {
        this.productCallback = callback
    }

    override fun getCount(): Int = productList.size

    override fun getItem(position: Int): ProductResponse = productList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding: ItemProductBinding
        val view: View

        if (convertView == null) {
            binding = ItemProductBinding.inflate(
                LayoutInflater.from(context), parent, false
            )
            view = binding.root
            view.tag = binding
        } else {
            binding = convertView.tag as ItemProductBinding
            view = convertView
        }

        val product = productList[position]
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
                viewModel.createFavorite(product._id, isAdding)
            }

        }


        return view
    }


    fun updateProducts(newProducts: List<ProductResponse>) {
        productList = newProducts
        notifyDataSetChanged()
    }
}

