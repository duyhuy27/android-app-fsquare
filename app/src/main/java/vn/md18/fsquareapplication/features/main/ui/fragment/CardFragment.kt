package vn.md18.fsquareapplication.features.main.ui.fragment

import android.view.LayoutInflater
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import vn.md18.fsquareapplication.core.base.BaseFragment
import vn.md18.fsquareapplication.databinding.FragmentCardBinding
import vn.md18.fsquareapplication.features.main.viewmodel.MainViewModel

@AndroidEntryPoint
class CardFragment : BaseFragment<FragmentCardBinding, MainViewModel>() {
    override val viewModel: MainViewModel by  activityViewModels()
    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentCardBinding  = FragmentCardBinding.inflate(layoutInflater)

    override fun getTagFragment(): String = CardFragment::class.java.simpleName

    override fun onViewLoaded() {
    }

    override fun addViewListener() {
    }

    override fun addDataObserver() {
    }
    companion object {
        @JvmStatic
        fun newInstance() = CardFragment()
    }

}