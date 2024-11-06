package vn.md18.fsquareapplication.features.main.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dagger.hilt.android.AndroidEntryPoint
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.core.base.BaseActivity
import vn.md18.fsquareapplication.data.model.DataState
import vn.md18.fsquareapplication.databinding.ActivityFavoriteBinding
import vn.md18.fsquareapplication.databinding.ActivityMainBinding
import vn.md18.fsquareapplication.features.main.adapter.FavoriteAdapter
import vn.md18.fsquareapplication.features.main.adapter.MainPagerAdapter
import vn.md18.fsquareapplication.features.main.adapter.ProductAdapter
import vn.md18.fsquareapplication.features.main.viewmodel.FavoriteViewmodel
import vn.md18.fsquareapplication.features.main.viewmodel.MainViewModel
import vn.md18.fsquareapplication.utils.Constant
import vn.md18.fsquareapplication.utils.extensions.showCustomToast
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteAndNewestActivity : BaseActivity<ActivityFavoriteBinding, FavoriteViewmodel>() {
    override val viewModel: FavoriteViewmodel  by viewModels()

    @Inject
    lateinit var mPagerAdapter: MainPagerAdapter

    @Inject
    lateinit var favoriteAdapter: FavoriteAdapter

    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityFavoriteBinding = ActivityFavoriteBinding.inflate(layoutInflater)
    override fun onViewLoaded() {
        favoriteAdapter.setViewModel(viewModel)
        binding.apply {
            grdProduct.apply {
                grdProduct.adapter = favoriteAdapter
            }
        }
    }

    override fun addViewListener() {
        viewModel.getFavoriteList()
    }

    override fun addDataObserver() {
        viewModel.listFavorite.observe(this@FavoriteAndNewestActivity) {
            binding.apply {
                favoriteAdapter.updateProducts(it)
            }
        }

        viewModel.deleteFavoriteState.observe(this@FavoriteAndNewestActivity) { data ->
            when (data) {
                is DataState.Error -> {
                    showCustomToast("Delete to Favorite Failure", Constant.ToastStatus.FAILURE)
                }
                DataState.Loading -> {

                }
                is DataState.Success -> {
                    showCustomToast("Delete to favorite Success", Constant.ToastStatus.SUCCESS)
                    viewModel.getFavoriteList()
                }
            }
        }
    }
}