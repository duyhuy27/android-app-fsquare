package vn.md18.fsquareapplication.features.main.ui.fragment

import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import vn.md18.fsquareapplication.core.base.BaseFragment
import vn.md18.fsquareapplication.databinding.FragmentDashboardBinding
import vn.md18.fsquareapplication.features.main.adapter.ProductBannerAdapter
import vn.md18.fsquareapplication.features.main.viewmodel.MainViewModel
import javax.inject.Inject


@AndroidEntryPoint
class DashboardFragment : BaseFragment<FragmentDashboardBinding, MainViewModel>() {
    companion object {
        @JvmStatic
        fun newInstance() = DashboardFragment()
    }

    override val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager



    @Inject
    lateinit var bannerAdapter:  ProductBannerAdapter
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
        }

    }

    override fun addViewListener() {
        viewModel.getProductBanner()
    }

    override fun addDataObserver() {
        viewModel.listProductBanner.observe(this@DashboardFragment) {
            binding.apply {
                bannerAdapter.submitList(it)
            }
        }
    }
}
