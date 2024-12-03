package vn.md18.fsquareapplication.features.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.net.toUri
import dagger.hilt.android.qualifiers.ApplicationContext
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.data.network.model.response.BrandResponse
import vn.md18.fsquareapplication.data.network.model.response.ProductResponse
import vn.md18.fsquareapplication.data.network.model.response.favorite.FavoriteResponse
import vn.md18.fsquareapplication.databinding.ItemBrandBinding
import vn.md18.fsquareapplication.databinding.ItemProductBinding
import vn.md18.fsquareapplication.utils.extensions.loadImageURL
import vn.md18.fsquareapplication.utils.extensions.loadImageUri
import javax.inject.Inject

class BrandAdapter @Inject constructor(
    @ApplicationContext private val context: Context
) : BaseAdapter() {

    private var productBrand: List<BrandResponse> = listOf()

    interface OnBrandActionListener {
        fun onBrandClick(brand: BrandResponse)  // Thêm tham số để gửi brand
    }

    private var brandActionListener: OnBrandActionListener? = null

    fun setBrandActionListener(listener: OnBrandActionListener) {
        brandActionListener = listener
    }

    override fun getCount(): Int = productBrand.size

    override fun getItem(position: Int): BrandResponse = productBrand[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding: ItemBrandBinding
        val view: View

        if (convertView == null) {
            binding = ItemBrandBinding.inflate(
                LayoutInflater.from(context), parent, false
            )
            view = binding.root
            view.tag = binding
        } else {
            binding = convertView.tag as ItemBrandBinding
            view = convertView
        }

        val product = productBrand[position]
        binding.apply {
            txtBrandName.text = product.name
            imgBrandImage.apply {
                val thumbnailUrl = product.thumbnail?.url
                if (!thumbnailUrl.isNullOrEmpty()) {
                    loadImageURL(thumbnailUrl)
                } else {
                    setImageResource(R.drawable.null_shoes)
                }
            }

            root.setOnClickListener {
                brandActionListener?.onBrandClick(product)  // Gọi hàm xử lý click với brand
            }
        }

        return view
    }

    fun updateProducts(newProducts: List<BrandResponse>) {
        productBrand = newProducts
        notifyDataSetChanged()
    }
}
