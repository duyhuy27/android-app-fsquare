package vn.md18.fsquareapplication.features.detail.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.core.base.BaseActivity
import vn.md18.fsquareapplication.data.model.DataState
import vn.md18.fsquareapplication.data.network.model.response.Classification
import vn.md18.fsquareapplication.data.network.model.response.ProductResponse
import vn.md18.fsquareapplication.databinding.ActivityDetailProductBinding
import vn.md18.fsquareapplication.features.detail.adapter.ColorDetailAdapter
import vn.md18.fsquareapplication.features.detail.adapter.SizeDetailAdapter
import vn.md18.fsquareapplication.features.detail.viewmodel.DetailProductViewModel
import vn.md18.fsquareapplication.features.main.viewmodel.BagViewmodel
import vn.md18.fsquareapplication.utils.Constant
import vn.md18.fsquareapplication.utils.extensions.loadImageUrlDiskCacheStrategy
import vn.md18.fsquareapplication.utils.fslogger.FSLogger
import javax.inject.Inject

@AndroidEntryPoint
class DetailProductActivity : BaseActivity<ActivityDetailProductBinding, DetailProductViewModel>() {

    override val viewModel: DetailProductViewModel by viewModels()

    @Inject
    lateinit var colorDetailAdapter: ColorDetailAdapter

    @Inject
    lateinit var sizeDetailAdapter: SizeDetailAdapter

    var quantity = 0
    var sizeId = ""
    var productId = ""
    var productPrice: Double = 0.0

    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityDetailProductBinding =
        ActivityDetailProductBinding.inflate(layoutInflater)

    override fun addViewListener() {
        binding.toolbar.onClickBackPress = this::onBackPressed

        colorDetailAdapter.setOnColorClickListener { response ->
            val colorId = response._id
            viewModel.getClassification(colorId)
            viewModel.classification.observe(this) { classification ->
                classification?.id?.let { classificationId ->
                    viewModel.getSizeList(classificationId)
                }
            }
        }

        sizeDetailAdapter.setOnSizeClickListener {
            sizeId = it.id
        }

        binding.apply {
            btnProductPlusCart.setOnClickListener {
                quantity++
                binding.txtProductQuantityCart.text = quantity.toString()
                updateTotalPrice(productPrice)
            }
            btnProductMinusCart.setOnClickListener {
                if (quantity > 0) quantity--
                binding.txtProductQuantityCart.text = quantity.toString()
                updateTotalPrice(productPrice)
            }
            btnAddToCart.setOnClickListener {
                viewModel.createBag(sizeId, quantity)
            }
        }
    }

    override fun onViewLoaded() {

        intent?.getBundleExtra(Constant.KEY_BUNDLE)?.apply {
            getString(Constant.KEY_PRODUCT)?.let {
                productId = it
                if(dataManager.getToken() != null && dataManager.getToken() != ""){
                    viewModel.callApiGetDetailProductV1(it)
                }else{
                    viewModel.callApiGetDetailProduct(it)
                }
                viewModel.getColorList(it)
            }
        }

        binding.apply {
            recyclerColorDetailProduc.apply {
                layoutManager = LinearLayoutManager(this@DetailProductActivity, LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
                adapter = colorDetailAdapter
            }

            recyclerSizeDatailProduc.apply {
                layoutManager = LinearLayoutManager(this@DetailProductActivity, LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
                adapter = sizeDetailAdapter
            }
        }

        binding.txtProductQuantityCart.text = quantity.toString()
    }

    override fun addDataObserver() {
        super.addDataObserver()

        viewModel.product.observe(this@DetailProductActivity) { productResponse ->
            setViewData(productResponse)
        }

        viewModel.listColor.observe(this@DetailProductActivity) {
            colorDetailAdapter.submitList(it)
        }

        viewModel.classification.observe(this@DetailProductActivity) {
            setViewClassificationData(it)
        }

        viewModel.listSize.observe(this@DetailProductActivity) {
            sizeDetailAdapter.submitList(it)
        }

        viewModel.addBagState.observe(this@DetailProductActivity) {
            if (it is DataState.Success) {
                Toast.makeText(this, "Thêm giỏ hàng thành công", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.favoriteState.observe(this@DetailProductActivity){
            if(it is DataState.Success){
                viewModel.callApiGetDetailProductV1(productId)
            }
        }
    }

    private fun setViewData(productResponse: ProductResponse?) {
        productResponse?.let {
            productPrice = it.maxPrice.toDouble()  // Lưu giá sản phẩm
            binding.txtNameDetailProduct.text = it.name
            binding.txtPriceDetailProduct.text = it.maxPrice.toString()
            binding.txtProductDescriptionDetailProduct.text = it.description
            binding.txtDescriptionDetailProduct.text = it.describe
            binding.txtPriceDetailProduct.text = it.maxPrice.toString() + " - " + it.minPrice.toString()
            binding.txtRatingDetailProduct.text = it.rating.toString()
            binding.imgDetailProduct.loadImageUrlDiskCacheStrategy(it.thumbnail?.url, R.drawable.null_shoes)
            var idShoes = it._id
            var isAdding = false
            if(it.isFavorite){
                isAdding = true
                binding.imgFav.setImageResource(R.drawable.add_to_fav)
            }else{
                isAdding = false
                binding.imgFav.setImageResource(R.drawable.add_fav)
            }

            binding.imgFav.setOnClickListener {
                viewModel.createFavorite(idShoes, isAdding)
            }
            updateTotalPrice(productPrice)
        }
    }

    private fun setViewClassificationData(classification: Classification?) {
        classification?.let {
            binding.apply {
                updateTotalPrice(it.price)
                txtPriceDetailProduct.text = "${it.price * quantity} VND"
            }
        }
    }

    private fun updateTotalPrice(price: Double) {
        val totalPrice = quantity * price
        binding.txtPriceDetailProduct.text = "${totalPrice} VND"
    }
}
