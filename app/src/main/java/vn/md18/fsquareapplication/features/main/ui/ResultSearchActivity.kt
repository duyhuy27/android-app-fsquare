package vn.md18.fsquareapplication.features.main.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.core.base.BaseActivity
import vn.md18.fsquareapplication.data.model.DataState
import vn.md18.fsquareapplication.data.network.model.response.ProductResponse
import vn.md18.fsquareapplication.databinding.ActivityResultSearchBinding
import vn.md18.fsquareapplication.features.main.adapter.SearchProductAdapter
import vn.md18.fsquareapplication.features.main.viewmodel.MainViewModel
import vn.md18.fsquareapplication.features.main.viewmodel.SearchViewModel
import vn.md18.fsquareapplication.utils.Constant
import vn.md18.fsquareapplication.utils.extensions.safeClickWithRx
import vn.md18.fsquareapplication.utils.extensions.showCustomToast
import vn.md18.fsquareapplication.utils.fslogger.FSLogger
import javax.inject.Inject

@AndroidEntryPoint
class ResultSearchActivity : BaseActivity<ActivityResultSearchBinding, SearchViewModel>(), SearchProductAdapter.ProductCallback {
    override val viewModel: SearchViewModel by viewModels()
    @Inject
    lateinit var productAdapter: SearchProductAdapter

    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityResultSearchBinding = ActivityResultSearchBinding.inflate(layoutInflater)

    override fun onViewLoaded() {
        //get data from bundle
        val query = intent.getStringExtra(Constant.KEY_SEARCH_QUERY) ?: ""
        FSLogger.e("huynd: $query")
        binding.apply {
            edtSearch.setText(query)
        }
        viewModel.getProductListGridBySearch(query)
        setupRecyclerView()
    }

    override fun addViewListener() {
        binding.edtSearch.safeClickWithRx {
    finish()
        }

        binding.toolbar.onClickBackPress = {
            onBackPressed()
        }

    }

    override fun addDataObserver() {
        super.addDataObserver()
        viewModel.listProductResultSearch.observe(this) { productList ->
            if (productList.isNotEmpty()) {
                FSLogger.e("huynd: ${productList.size}")
                binding.apply {
                    imgNoData.visibility = View.GONE
                    tvNoData.visibility = View.GONE
                }
                showProductGridView(productList)
            } else {
                showEmptyState("Không tìm thấy sản phẩm")
            }
        }

        viewModel.favoriteState.observe(this@ResultSearchActivity) { data ->
            when (data) {
                is DataState.Error -> {
                    showCustomToast("Action Failed", Constant.ToastStatus.FAILURE)
                }
                DataState.Loading -> {  }
                is DataState.Success -> {
                    val action = if (data.data) "added to" else "removed from"
                    showCustomToast("Successfully $action favorite", Constant.ToastStatus.SUCCESS
                    )
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rcvProduct.apply {
            layoutManager = LinearLayoutManager(this@ResultSearchActivity) // Hoặc GridLayoutManager
            adapter = productAdapter
//            setLoadingMoreEnabled(true)
//            setPullRefreshEnabled(true)
        }
        productAdapter.setProductCallback(this)
    }

    private fun showProductGridView(productList: List<ProductResponse>) {
        binding.rcvProduct.apply {
            layoutManager = GridLayoutManager(this@ResultSearchActivity, 2) // 2 cột
            adapter = productAdapter
        }
        productAdapter.submitList(productList)
    }

    private fun showEmptyState(message: String) {
        binding.apply {
            imgNoData.visibility = View.VISIBLE
            tvNoData.visibility = View.VISIBLE
            tvNoData.text = message
            rcvProduct.visibility = View.GONE
        }
    }

    override fun onProductClick(product: ProductResponse) {
        TODO("Not yet implemented")
    }

    override fun createFavorite(id: String, isAdding: Boolean) {
        TODO("Not yet implemented")
    }
}