package vn.md18.fsquareapplication.features.payment.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.core.base.BaseActivity
import vn.md18.fsquareapplication.databinding.ActivityMainBinding
import vn.md18.fsquareapplication.databinding.ActivityPaymentBinding
import vn.md18.fsquareapplication.databinding.LayoutTitleTabBinding
import vn.md18.fsquareapplication.features.main.adapter.MainPagerAdapter
import vn.md18.fsquareapplication.features.main.viewmodel.MainViewModel
import vn.md18.fsquareapplication.utils.Constant
import javax.inject.Inject

@AndroidEntryPoint
class PaymentActivity : BaseActivity<ActivityPaymentBinding, MainViewModel>() {
    override val viewModel: MainViewModel  by viewModels()
    private lateinit var navController: NavController
    private var receivedKey: String? = null

    @Inject
    lateinit var mPagerAdapter: MainPagerAdapter

    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityPaymentBinding = ActivityPaymentBinding.inflate(layoutInflater)

    override fun onViewLoaded() {

        receivedKey = intent.getStringExtra("STATUS_KEY")

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.payment_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        receivedKey?.let { navController(it) }
    }



    override fun addViewListener() {
    }

    override fun addDataObserver() {
        super.addDataObserver()

    }

    private fun navController(screen: String){
        when(screen){
            "PAYMENT_METHOD" -> navController.navigate(R.id.paymentWalletFragment)
            "TRANSACTION" -> navController.navigate(R.id.transactionHistoryPaymentFragment)
        }
    }
}