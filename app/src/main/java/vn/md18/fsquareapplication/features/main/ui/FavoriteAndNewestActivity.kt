package vn.md18.fsquareapplication.features.main.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dagger.hilt.android.AndroidEntryPoint
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.core.base.BaseActivity
import vn.md18.fsquareapplication.data.model.DataState
import vn.md18.fsquareapplication.databinding.ActivityFavoriteBinding
import vn.md18.fsquareapplication.databinding.ActivityMainBinding
import vn.md18.fsquareapplication.databinding.DialogConfirmDeleteFavBinding
import vn.md18.fsquareapplication.features.main.adapter.FavoriteAdapter
import vn.md18.fsquareapplication.features.main.adapter.MainPagerAdapter
import vn.md18.fsquareapplication.features.main.adapter.ProductAdapter
import vn.md18.fsquareapplication.features.main.viewmodel.FavoriteViewmodel
import vn.md18.fsquareapplication.features.main.viewmodel.MainViewModel
import vn.md18.fsquareapplication.utils.Constant
import vn.md18.fsquareapplication.utils.extensions.showCustomToast
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteAndNewestActivity : BaseActivity<ActivityFavoriteBinding, FavoriteViewmodel>(), FavoriteAdapter.OnFavoriteActionListener {

    override val viewModel: FavoriteViewmodel by viewModels()

    @Inject
    lateinit var mPagerAdapter: MainPagerAdapter

    @Inject
    lateinit var favoriteAdapter: FavoriteAdapter

    @Inject
    lateinit var productAdapter: ProductAdapter

    private lateinit var type: String

    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityFavoriteBinding = ActivityFavoriteBinding.inflate(layoutInflater)

    override fun onViewLoaded() {
        type = intent.getStringExtra("typeFavNew").toString()
        val brandName = intent.getStringExtra("brandName").toString()
        when(type){
            "New" -> {
                viewModel.getProductV1()
                binding.apply {
                    txtTitle.text = getString(R.string.Newest)
                    grdProduct.apply {
                        adapter = productAdapter
                    }
                }
            }
            "Fav" -> {
                viewModel.getFavoriteList()
                binding.apply {
                    txtTitle.text = getString(R.string.Favorite)
                    grdProduct.apply {
                        adapter = favoriteAdapter
                    }
                }
            }
            "Brand" -> {
                viewModel.getProductByBrandV1(idBrand = intent.getStringExtra("brandId").toString())
                binding.apply {
                    txtTitle.text = brandName
                    grdProduct.apply {
                        adapter = productAdapter
                    }
                }
            }
            null -> {
                viewModel.getFavoriteList()
                binding.apply {
                    txtTitle.text = getString(R.string.Favorite)
                    grdProduct.apply {
                        adapter = favoriteAdapter
                    }
                }
            }
        }

        favoriteAdapter.setFavoriteActionListener(this)


    }

    override fun addViewListener() {
        binding.apply {
            btnBack.setOnClickListener {

                    val intent = Intent(this@FavoriteAndNewestActivity, MainActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                        putExtra("SELECTED_TAB", MainViewModel.TAB_DASHBOARD_PAGE)
                    }
                    startActivity(intent)
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)

            }
        }
    }

    override fun addDataObserver() {
        viewModel.listProductBrand.observe(this@FavoriteAndNewestActivity) {
            binding.apply {
                productAdapter.updateProducts(it)
            }
        }
        viewModel.listFavorite.observe(this@FavoriteAndNewestActivity) {
            binding.apply {
                favoriteAdapter.updateProducts(it)
            }
        }

        viewModel.listProduct.observe(this@FavoriteAndNewestActivity) {
            binding.apply {
                productAdapter.updateProducts(it)
            }
        }

        viewModel.deleteFavoriteState.observe(this@FavoriteAndNewestActivity) { data ->
            when (data) {
                is DataState.Error -> {
                    showCustomToast("Delete from Favorite Failed", Constant.ToastStatus.FAILURE)
                }
                DataState.Loading -> {
                }
                is DataState.Success -> {
                    showCustomToast("Deleted from Favorite Successfully", Constant.ToastStatus.SUCCESS)
                    viewModel.getFavoriteList()
                }
            }
        }
    }
    override fun onRemoveFavorite(productId: String) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_confirm_delete_fav, null)
        val binding = DialogConfirmDeleteFavBinding.bind(dialogView)

        val alertDialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        binding.btnCancel.setOnClickListener {
            alertDialog.dismiss()
        }

        binding.btnConfirm.setOnClickListener {
            viewModel.deleteFavorite(productId)
            alertDialog.dismiss()
        }

        alertDialog.show()
    }
}
