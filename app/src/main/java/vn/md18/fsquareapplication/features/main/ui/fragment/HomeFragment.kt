package vn.md18.fsquareapplication.features.main.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.core.base.BaseFragment
import vn.md18.fsquareapplication.databinding.FragmentHomeBinding
import vn.md18.fsquareapplication.databinding.FragmentOrderBinding
import vn.md18.fsquareapplication.features.main.viewmodel.MainViewModel

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, MainViewModel>() {
    override val viewModel: MainViewModel by activityViewModels()
    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater)

    override fun getTagFragment(): String = HomeFragment::class.java.simpleName

    override fun onViewLoaded() {
        val dashboardFragment = DashboardFragment()

        // Thêm DashboardFragment vào dashboard_fragment_container
        childFragmentManager.beginTransaction()
            .replace(R.id.dashboard_fragment_container, dashboardFragment)
            .commit()
    }

    override fun addViewListener() {

    }

    override fun addDataObserver() {

    }
    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}