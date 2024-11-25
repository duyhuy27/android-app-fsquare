package vn.md18.fsquareapplication.features.detail.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dagger.hilt.android.AndroidEntryPoint
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.core.base.BaseActivity
import vn.md18.fsquareapplication.data.network.model.response.ProductResponse
import vn.md18.fsquareapplication.databinding.ActivityDetailProductBinding
import vn.md18.fsquareapplication.features.detail.viewmodel.DetailProductViewModel
import vn.md18.fsquareapplication.utils.Constant
import vn.md18.fsquareapplication.utils.extensions.loadImageUrlDiskCacheStrategy
import vn.md18.fsquareapplication.utils.fslogger.FSLogger

@AndroidEntryPoint
class DetailProductActivity : BaseActivity<ActivityDetailProductBinding, DetailProductViewModel>() {

    override val viewModel: DetailProductViewModel by viewModels()

    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityDetailProductBinding =
        ActivityDetailProductBinding.inflate(layoutInflater)

    override fun addViewListener() {
        binding.toolbar.onClickBackPress = this::onBackPressed
    }

    override fun onViewLoaded() {
        intent?.getBundleExtra(Constant.KEY_BUNDLE)?.apply {
            getString(Constant.KEY_PRODUCT)?.let {
                FSLogger.e("Huynd: $it")
                viewModel.callApiGetDetailProduct(it)
            }
        }
    }

    override fun addDataObserver() {
        super.addDataObserver()
        viewModel.product.observe(this@DetailProductActivity) {
            productResponse ->
            setViewData(productResponse)
        }
    }

    private fun setViewData(productResponse: ProductResponse?): ProductResponse? {
        productResponse?.let {
            binding.txtNameDetailProduct.text = it.name
            binding.txtPriceDetailProduct.text = it.maxPrice.toString()
            binding.txtProductDescriptionDetailProduct.text = it.description
            binding.txtDescriptionDetailProduct.text = it.describe
            binding.txtPriceDetailProduct.text = it.maxPrice.toString() + " - " + it.minPrice.toString()
            binding.txtRatingDetailProduct.text = it.rating.toString()
            binding.imgDetailProduct.loadImageUrlDiskCacheStrategy(it.thumbnail.url, R.drawable.ic_noti_failure_24)

        }
        return productResponse
    }


}