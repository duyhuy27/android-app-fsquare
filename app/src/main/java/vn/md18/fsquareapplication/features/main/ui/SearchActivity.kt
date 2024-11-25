package vn.md18.fsquareapplication.features.main.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.core.base.BaseActivity
import vn.md18.fsquareapplication.core.base.BaseViewModel
import vn.md18.fsquareapplication.databinding.ActivitySearchBinding
import vn.md18.fsquareapplication.features.main.viewmodel.SearchViewModel

class SearchActivity : BaseActivity<ActivitySearchBinding, SearchViewModel>() {

    override val viewModel: SearchViewModel by viewModels()

    override fun inflateLayout(layoutInflater: LayoutInflater): ActivitySearchBinding = ActivitySearchBinding.inflate(layoutInflater)

    override fun onViewLoaded() {
    }

    override fun addViewListener() {
    }

    override fun addDataObserver() {
    }

}