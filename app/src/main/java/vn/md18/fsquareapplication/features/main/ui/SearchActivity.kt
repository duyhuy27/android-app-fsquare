package vn.md18.fsquareapplication.features.main.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import vn.md18.fsquareapplication.core.base.BaseActivity
import vn.md18.fsquareapplication.data.model.DataState
import vn.md18.fsquareapplication.data.network.model.response.HistorySearchResponse
import vn.md18.fsquareapplication.data.network.model.response.ProductResponse
import vn.md18.fsquareapplication.databinding.ActivitySearchBinding
import vn.md18.fsquareapplication.features.detail.ui.DetailProductActivity
import vn.md18.fsquareapplication.features.main.adapter.HistorySearchAdapter
import vn.md18.fsquareapplication.features.main.adapter.SearchProductAdapter
import vn.md18.fsquareapplication.features.main.viewmodel.SearchViewModel
import vn.md18.fsquareapplication.utils.Constant
import vn.md18.fsquareapplication.utils.extensions.disable
import vn.md18.fsquareapplication.utils.extensions.showCustomToast
import vn.md18.fsquareapplication.utils.fslogger.FSLogger
import vn.md18.fsquareapplication.utils.view.pulltorefreshview.PullToRefreshListener
import javax.inject.Inject

@AndroidEntryPoint
class SearchActivity : BaseActivity<ActivitySearchBinding, SearchViewModel>(),
    HistorySearchAdapter.SearchHistoryCallback, PullToRefreshListener, SearchProductAdapter.ProductCallback {

    override val viewModel: SearchViewModel by viewModels()

    @Inject
    lateinit var historySearchAdapter: HistorySearchAdapter

    override fun inflateLayout(layoutInflater: LayoutInflater): ActivitySearchBinding =
        ActivitySearchBinding.inflate(layoutInflater)

    override fun onViewLoaded() {
//        setupRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        // Focus vào edtSearch khi quay lại màn hình
        binding.edtSearch.requestFocus()
//        viewModel.getHistoryKeyWordSearchByUserId()
    }

    override fun addViewListener() {
        setupListeners()
    }

    private fun setupRecyclerView() {
        binding.rcvProduct.apply {
            layoutManager = LinearLayoutManager(this@SearchActivity) // Hoặc GridLayoutManager
            adapter = historySearchAdapter
        }
        historySearchAdapter.setSearchHistoryCallback(this)
    }

    private fun observeViewModel() {
//        viewModel.listKeywordSearch.observe(this) { keywordList ->
//            if (keywordList.isNotEmpty()) {
//                showHistoryRecyclerView(keywordList)
//            } else {
//                showEmptyState("Không có lịch sử tìm kiếm")
//            }
//        }
    }

    private fun setupListeners() {
        binding.edtSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = binding.edtSearch.text.toString().trim()
                if (query.isNotEmpty()) {
                    // Truyền query trực tiếp qua Intent
                    val intent = Intent(this, ResultSearchActivity::class.java).apply {
                        putExtra(Constant.KEY_SEARCH_QUERY, query)
                    }
                    startActivity(intent)
                }
                true
            } else {
                false
            }
        }
    }


    private fun showHistoryRecyclerView(keywordList: List<HistorySearchResponse>) {
        binding.apply {
            imgNoData.visibility = View.GONE
            tvNoData.visibility = View.GONE
            rcvProduct.visibility = View.VISIBLE
            rcvProduct.adapter = historySearchAdapter
            rcvProduct.layoutManager = LinearLayoutManager(this@SearchActivity)
            historySearchAdapter.submitList(keywordList)
        }
    }

    override fun addDataObserver() {
        super.addDataObserver()
        observeViewModel()
    }

    private fun showEmptyState(message: String) {
        binding.apply {
            imgNoData.visibility = View.VISIBLE
            tvNoData.visibility = View.VISIBLE
            tvNoData.text = message
            rcvProduct.visibility = View.GONE
        }
    }

    override fun onClickSearchHistoryItem(itemID: String) {
        // Handle history item click
    }

    override fun onClickHomeInfo(itemHistoryResponse: HistorySearchResponse) {
        // Handle item details click
    }

    override fun onRefresh() {
        viewModel.getHistoryKeyWordSearchByUserId()
    }

    override fun onLoadMore() {
        // Handle load more for products
    }

    override fun onProductClick(product: ProductResponse) {
        val data = Bundle().apply {
            putString(Constant.KEY_PRODUCT, product._id)
        }
        openActivity(DetailProductActivity::class.java, data)
    }

    override fun createFavorite(id: String, isAdding: Boolean) {
        if (dataManager.getToken().isNullOrEmpty()) {
            showCustomToast("Vui lòng đăng nhập để thực hiện", Constant.ToastStatus.FAILURE)
        } else {
            viewModel.createFavorite(id, isAdding)
        }
    }
}


