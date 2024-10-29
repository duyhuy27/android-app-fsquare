package vn.md18.fsquareapplication.features.main.ui.fragment

import android.view.LayoutInflater
import androidx.fragment.app.activityViewModels
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import vn.md18.fsquareapplication.core.base.BaseFragment
import vn.md18.fsquareapplication.databinding.FragmentOrderBinding
import vn.md18.fsquareapplication.features.main.adapter.OrderPagerAdapter
import vn.md18.fsquareapplication.features.main.viewmodel.MainViewModel
import vn.md18.fsquareapplication.utils.Constant

@AndroidEntryPoint
class OrderFragment : BaseFragment<FragmentOrderBinding, MainViewModel>() {
    override val viewModel: MainViewModel by activityViewModels()

    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentOrderBinding = FragmentOrderBinding.inflate(layoutInflater)

    override fun getTagFragment(): String = OrderFragment::class.java.simpleName

    override fun onViewLoaded() {
        binding.viewPager.adapter = OrderPagerAdapter(this)

        val tabTitles = listOf(Constant.PENDING, Constant.PROCESSING, Constant.SHIPPED, Constant.DELIVERED, Constant.CANCELED)

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }

    override fun addViewListener() {

    }

    override fun addDataObserver() {

    }

    companion object {
        @JvmStatic
        fun newInstance() = OrderFragment()
    }
}