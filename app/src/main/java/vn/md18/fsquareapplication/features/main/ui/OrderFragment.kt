package vn.md18.fsquareapplication.features.main.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.core.base.BaseFragment
import vn.md18.fsquareapplication.databinding.FragmentOrderBinding
import vn.md18.fsquareapplication.features.main.viewmodel.MainViewModel

@AndroidEntryPoint
class OrderFragment : BaseFragment<FragmentOrderBinding, MainViewModel>() {
    override val viewModel: MainViewModel by activityViewModels()
    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentOrderBinding = FragmentOrderBinding.inflate(layoutInflater)

    override fun getTagFragment(): String = OrderFragment::class.java.simpleName

    override fun onViewLoaded() {
          
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