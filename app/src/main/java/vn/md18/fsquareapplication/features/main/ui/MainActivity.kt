package vn.md18.fsquareapplication.features.main.ui

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.core.base.BaseActivity
import vn.md18.fsquareapplication.core.base.BaseViewModel
import vn.md18.fsquareapplication.databinding.ActivityMainBinding
import vn.md18.fsquareapplication.databinding.LayoutTitleTabBinding
import vn.md18.fsquareapplication.features.auth.ui.AuthActivity
import vn.md18.fsquareapplication.features.main.adapter.MainPagerAdapter
import vn.md18.fsquareapplication.features.main.viewmodel.MainViewModel
import vn.md18.fsquareapplication.features.main.viewmodel.MainViewModel.Companion.TAB_CARD_CONTEXT
import vn.md18.fsquareapplication.features.main.viewmodel.MainViewModel.Companion.TAB_DASHBOARD_PAGE
import vn.md18.fsquareapplication.features.main.viewmodel.MainViewModel.Companion.TAB_ORDERS
import vn.md18.fsquareapplication.features.main.viewmodel.MainViewModel.Companion.TAB_PROFILE
import vn.md18.fsquareapplication.features.main.viewmodel.MainViewModel.Companion.TAB_WALLET
import vn.md18.fsquareapplication.utils.Constant
import vn.md18.fsquareapplication.utils.extensions.showCustomToast
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, BaseViewModel>() {

    override val viewModel: MainViewModel  by viewModels()

    private var isBackPressedOnce = false

    @Inject
    lateinit var mPagerAdapter: MainPagerAdapter

    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun onViewLoaded() {
        binding.pagerMain.adapter = mPagerAdapter
        TabLayoutMediator(binding.tabMain, binding.pagerMain) { tab, position ->
            when (position) {
                TAB_DASHBOARD_PAGE -> {
                    val tabHome = LayoutTitleTabBinding.inflate(LayoutInflater.from(applicationContext))
                    tabHome.ivIcon.setImageResource(R.drawable.bg_tab_home)
                    tabHome.tvTitle.text = getString(R.string.tab_home_label)
                    tab.customView = tabHome.root
                }

                TAB_CARD_CONTEXT -> {
                    val tabScene = LayoutTitleTabBinding.inflate(LayoutInflater.from(applicationContext))
                    tabScene.ivIcon.setImageResource(R.drawable.bg_tab_card)
                    tabScene.tvTitle.text = getString(R.string.main_tab_auto_scene_label)
                    tab.customView = tabScene.root
                }

                TAB_ORDERS -> {
                    val tabNotification = LayoutTitleTabBinding.inflate(LayoutInflater.from(applicationContext))
                    tabNotification.ivIcon.setImageResource(R.drawable.bg_tab_order)
                    tabNotification.tvTitle.text = getString(R.string.Orders)
                    tab.customView = tabNotification.root
                }

                TAB_PROFILE -> {
                    val tabNotification = LayoutTitleTabBinding.inflate(LayoutInflater.from(applicationContext))
                    tabNotification.ivIcon.setImageResource(R.drawable.bg_tab_profile)
                    tabNotification.tvTitle.text = getString(R.string.profile)
                    tab.customView = tabNotification.root
                }
            }
        }.attach()

        val selectedTab = intent.getIntExtra("SELECTED_TAB", TAB_DASHBOARD_PAGE)
        when(selectedTab){
            TAB_PROFILE -> {
                viewModel.checkTokenIfNeeded {
                    if (it) {
                        binding.pagerMain.setCurrentItem(TAB_PROFILE, false)
                    } else {
                        openActivity(AuthActivity::class.java)
                    }
                }
            }
            TAB_ORDERS -> binding.pagerMain.setCurrentItem(TAB_ORDERS, false)
            TAB_CARD_CONTEXT -> binding.pagerMain.setCurrentItem(TAB_CARD_CONTEXT, false)
            TAB_DASHBOARD_PAGE -> binding.pagerMain.setCurrentItem(TAB_DASHBOARD_PAGE, false)
        }
    }



    override fun addViewListener() {
    }

    override fun addDataObserver() {
        super.addDataObserver()

    }

    override fun onBackPressed() {
        if (isBackPressedOnce) {
            super.onBackPressed()
            return
        }

        isBackPressedOnce = true
        showCustomToast("Press again to exit") // Show a toast or any message

        // Reset the flag after 2 seconds (you can change this duration)
        Handler(Looper.getMainLooper()).postDelayed({ isBackPressedOnce = false }, 2000)
    }

}