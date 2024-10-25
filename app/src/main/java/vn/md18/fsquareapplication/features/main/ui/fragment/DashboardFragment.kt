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
import vn.md18.fsquareapplication.data.network.model.response.ProductResponse
import javax.inject.Inject

@AndroidEntryPoint
class DashboardFragment : BaseFragment<FragmentDashboardBinding, MainViewModel>() {
    companion object {
        @JvmStatic
        fun newInstance() = DashboardFragment()
    }

    override val viewModel: MainViewModel by viewModels()

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
    }

//    private fun generateDummyProducts() {
//        val dummyProducts = listOf(
//            ProductResponse(
//                _id = "1", name = "Product 1", thumbnail = Thumbnail("url_to_image_1"), minPrice = 10, maxPrice = 15, rating = 4.5, reviewCount = 100, isFavorite = false
//            ),
//            ProductResponse(
//                _id = "2", name = "Product 2", thumbnail = Thumbnail("url_to_image_2"), minPrice = 20, maxPrice = 25, rating = 4.0, reviewCount = 80, isFavorite = true
//            ),
//            ProductResponse(
//                _id = "3", name = "Product 3", thumbnail = Thumbnail("url_to_image_3"), minPrice = 15, maxPrice = 20, rating = 3.5, reviewCount = 50, isFavorite = false
//            ),
//            ProductResponse(
//                _id = "4", name = "Product 4", thumbnail = Thumbnail("url_to_image_4"), minPrice = 5, maxPrice = 10, rating = 5.0, reviewCount = 200, isFavorite = true
//            ),
//            ProductResponse(
//                _id = "5",
//                name = "Product 5",
//                thumbnail = Thumbnail("url_to_image_5"),
//                minPrice = 12,
//                maxPrice = 18,
//                rating = 4.2,
//                reviewCount = 60,
//                isFavorite = false
//            )
//        )
//        productAdapter.submitList(dummyProducts)
//    }



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
}

