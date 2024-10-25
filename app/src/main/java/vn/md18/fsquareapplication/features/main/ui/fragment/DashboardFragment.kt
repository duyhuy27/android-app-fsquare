package vn.md18.fsquareapplication.features.main.ui.fragment

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
import javax.inject.Inject

@AndroidEntryPoint
class DashboardFragment : BaseFragment<FragmentDashboardBinding, MainViewModel>() {
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
        binding.apply {
            rcvBanner.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
                adapter = bannerAdapter
            }
            grdProduct.apply {
                grdProduct.adapter = productAdapter
            }
//            generateDummyProducts()
        }

        startAutoScroll()
    }



    override fun addViewListener() {
        viewModel.getProductBanner()
    }

    override fun addDataObserver() {
        viewModel.listProductBanner.observe(this@DashboardFragment) {
            binding.apply {
                bannerAdapter.submitList(it)
                productAdapter.updateProducts(it)
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
}

