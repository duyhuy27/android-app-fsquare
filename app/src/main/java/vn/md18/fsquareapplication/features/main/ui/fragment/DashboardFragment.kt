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
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.data.model.DataState
import vn.md18.fsquareapplication.data.network.model.response.BrandResponse
import vn.md18.fsquareapplication.features.main.adapter.BrandAdapter
import vn.md18.fsquareapplication.data.network.model.response.ProductResponse
import vn.md18.fsquareapplication.features.detail.ui.DetailProductActivity
import vn.md18.fsquareapplication.features.main.ui.FavoriteAndNewestActivity
import vn.md18.fsquareapplication.features.main.viewmodel.FavoriteViewmodel
import vn.md18.fsquareapplication.utils.Constant
import vn.md18.fsquareapplication.utils.extensions.showCustomToast
import vn.md18.fsquareapplication.utils.fslogger.FSLogger
import javax.inject.Inject

@AndroidEntryPoint
class DashboardFragment : BaseFragment<FragmentDashboardBinding, MainViewModel>(), ProductAdapter.ProductCallback, BrandAdapter.OnBrandActionListener {
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
    lateinit var brandAdapter: BrandAdapter

    @Inject
    lateinit var productAdapter: ProductAdapter



    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentDashboardBinding =
        FragmentDashboardBinding.inflate(layoutInflater)

    override fun getTagFragment(): String = DashboardFragment::class.java.simpleName

    override fun onViewLoaded() {
        productAdapter.setProductCallback(this)
        brandAdapter.setBrandActionListener(this)
        productAdapter.setViewModel(viewModel)
        viewModel.getProductBanner()
        viewModel.getBrands()

        if(dataManager.getToken() != null && dataManager.getToken() != ""){
            viewModel.getProductV1()
        }else{
            viewModel.getProduct()
        }


        binding.apply {
            rcvBanner.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
                adapter = bannerAdapter
            }
            grdProduct.apply {
                grdProduct.adapter = productAdapter
            }
            grdBrand.apply {
                grdBrand.adapter = brandAdapter
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
        viewModel.loadingState.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.shimmerLayoutBanner.startShimmer()
                binding.shimmerLayoutBanner.visibility = View.VISIBLE
                binding.shimmerLayout.visibility = View.VISIBLE
                binding.shimmerLayout.startShimmer()
                binding.rcvBanner.visibility = View.GONE
                binding.grdProduct.visibility = View.GONE
            } else {
                binding.shimmerLayout.stopShimmer()
                binding.shimmerLayout.visibility = View.GONE
                binding.shimmerLayoutBanner.visibility = View.GONE
                binding.rcvBanner.visibility = View.VISIBLE
                binding.grdProduct.visibility= View.VISIBLE
            }
        }

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
        viewModel.listBrands.observe(this@DashboardFragment) {
            binding.apply {
                brandAdapter.updateProducts(it)
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

    override fun createFavorite(id: String, isAdding: Boolean) {
        if(dataManager.getToken().isNullOrEmpty()){
            viewModel.createFavorite(id, isAdding)
        }else{
            activity?.showCustomToast("Vui lòng đăng nhập để thực hiện", Constant.ToastStatus.FAILURE)
        }
    }

    override fun onBrandClick(brand: BrandResponse) {
        val intent = Intent(context, FavoriteAndNewestActivity::class.java).apply {
            putExtra("typeFavNew", "Brand")
            putExtra("brandId", brand._id)
        }
        startActivity(intent)
    }
}
