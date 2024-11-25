package vn.md18.fsquareapplication.features.main.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import vn.md18.fsquareapplication.core.base.BaseFragment
import vn.md18.fsquareapplication.databinding.FragmentDashboardBinding
import vn.md18.fsquareapplication.features.main.adapter.ProductAdapter
import vn.md18.fsquareapplication.features.main.adapter.ProductBannerAdapter
import vn.md18.fsquareapplication.features.main.viewmodel.MainViewModel
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.data.model.DataState
import vn.md18.fsquareapplication.data.network.model.response.ProductResponse
import vn.md18.fsquareapplication.features.detail.ui.DetailProductActivity
import vn.md18.fsquareapplication.features.main.ui.FavoriteAndNewestActivity
import vn.md18.fsquareapplication.features.main.viewmodel.FavoriteViewmodel
import vn.md18.fsquareapplication.utils.Constant
import vn.md18.fsquareapplication.utils.extensions.showCustomToast
import javax.inject.Inject

@AndroidEntryPoint
class DashboardFragment : BaseFragment<FragmentDashboardBinding, MainViewModel>(), ProductAdapter.ProductCallback {
    companion object {
        @JvmStatic
        fun newInstance() = DashboardFragment()
    }

    override val viewModel: MainViewModel by viewModels()

    private var handler: Handler? = null
    private var autoScrollRunnable: Runnable? = null
    private var currentPosition = 0

    @Inject
    lateinit var bannerAdapter: ProductBannerAdapter

    @Inject
    lateinit var productAdapter: ProductAdapter

    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentDashboardBinding =
        FragmentDashboardBinding.inflate(layoutInflater)

    override fun getTagFragment(): String = DashboardFragment::class.java.simpleName

    override fun onViewLoaded() {
        productAdapter.setProductCallback(this)
        productAdapter.setViewModel(viewModel)
        viewModel.getProductBanner()
        viewModel.getProduct()


        binding.apply {
            rcvBanner.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
                adapter = bannerAdapter
            }
            grdProduct.apply {
                grdProduct.adapter = productAdapter
            }
        }

        startAutoScroll()
    }

    override fun addViewListener() {
        binding.tvSeeMoreNewest.setOnClickListener {
            val intent = Intent(context, FavoriteAndNewestActivity::class.java).apply {
                putExtra("typeFavNew", "New")

            }
            startActivity(intent)
        }
    }

    override fun addDataObserver() {
        viewModel.listProductBanner.observe(this@DashboardFragment) {
            binding.apply {
                bannerAdapter.submitList(it)
            }
        }
        viewModel.listProduct.observe(this@DashboardFragment) {
            binding.apply {
                productAdapter.updateProducts(it)
            }
        }
        viewModel.favoriteState.observe(this@DashboardFragment) { data ->
            when (data) {
                is DataState.Error -> {
                    activity?.showCustomToast("Action Failed", Constant.ToastStatus.FAILURE)
                }
                DataState.Loading -> {  }
                is DataState.Success -> {
                    val action = if (data.data) "added to" else "removed from"
                    activity?.showCustomToast("Successfully $action favorite", Constant.ToastStatus.SUCCESS
                    )
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        stopAutoScroll()
    }

    private fun startAutoScroll() {
        handler = Handler(Looper.getMainLooper())
        autoScrollRunnable = Runnable {
            if (currentPosition == bannerAdapter.itemCount) {
                currentPosition = 0
                binding.rcvBanner.scrollToPosition(0)
            } else {
                binding.rcvBanner.smoothScrollToPosition(currentPosition++)
            }
            handler?.postDelayed(autoScrollRunnable!!, 3000)
        }
        handler?.postDelayed(autoScrollRunnable!!, 3000)
    }

    private fun stopAutoScroll() {
        handler?.removeCallbacks(autoScrollRunnable!!)
    }

    override fun onProductClick(product: ProductResponse) {
        val data = Bundle().apply {
            putString(Constant.KEY_PRODUCT, product._id)
        }
        openActivity(DetailProductActivity::class.java, data)
    }
}
