package vn.md18.fsquareapplication.features.main.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import dagger.hilt.android.qualifiers.ApplicationContext
import vn.md18.fsquareapplication.data.network.model.response.ProductResponse
import vn.md18.fsquareapplication.databinding.ItemProductBinding
import vn.md18.fsquareapplication.features.main.ui.FavoriteAndNewestActivity
import vn.md18.fsquareapplication.features.main.viewmodel.MainViewModel
import vn.md18.fsquareapplication.utils.extensions.loadImageURL
import javax.inject.Inject

class ProductAdapter @Inject constructor(
    @ApplicationContext private val context: Context,

) : BaseAdapter() {

    private lateinit var viewModel: MainViewModel
    private var productList: List<ProductResponse> = listOf()

    fun setViewModel(viewModel: MainViewModel) {
        this.viewModel = viewModel
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
            txtProductPrice.text = "${product.maxPrice} $"
            txtProductName.text = product.name
            imgProduct.loadImageURL(product.thumbnail.url)
            imgAddToFav.setOnClickListener {
                viewModel.createFavorite(product._id)

            }
        }

        return view
    }

    fun updateProducts(newProducts: List<ProductResponse>) {
        productList = newProducts
        notifyDataSetChanged()
    }
}

