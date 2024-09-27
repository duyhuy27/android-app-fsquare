package vn.md18.fsquareapplication.features.main.ui

import android.view.LayoutInflater
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import vn.md18.fsquareapplication.core.base.BaseActivity
import vn.md18.fsquareapplication.core.base.BaseViewModel
import vn.md18.fsquareapplication.databinding.ActivityMainBinding
import vn.md18.fsquareapplication.features.main.viewmodel.MainViewModel

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, BaseViewModel>() {

    override val viewModel: MainViewModel  by viewModels()

    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun onViewLoaded() {
        viewModel.checkLoading()
    }

    override fun addViewListener() {
    }

    override fun addDataObserver() {
        super.addDataObserver()

    }

}