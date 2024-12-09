package vn.md18.fsquareapplication.features.main.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.core.base.BaseActivity
import vn.md18.fsquareapplication.data.network.model.response.NotificationResponse
import vn.md18.fsquareapplication.databinding.ActivityNotificationBinding
import vn.md18.fsquareapplication.features.main.adapter.NotificationAdapter
import vn.md18.fsquareapplication.features.main.viewmodel.NotificationViewmodel
import javax.inject.Inject

@AndroidEntryPoint
class NotificationActivity : BaseActivity<ActivityNotificationBinding, NotificationViewmodel>(), NotificationAdapter.NotificationCallback {


    override val viewModel: NotificationViewmodel by viewModels()

    @Inject
    lateinit var notificationAdapter: NotificationAdapter

    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityNotificationBinding = ActivityNotificationBinding.inflate(layoutInflater)

    override fun onViewLoaded() {
        viewModel.getListNotification()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.rvNotification.apply {
            layoutManager = LinearLayoutManager(this@NotificationActivity) // Hoặc GridLayoutManager
            adapter = notificationAdapter
//            setLoadingMoreEnabled(true)
//            setPullRefreshEnabled(true)
        }
        notificationAdapter.setNotificationCallback(this)
    }

    override fun addViewListener() {
    }

    override fun addDataObserver() {
        viewModel.apply {
            listNotification.observe(this@NotificationActivity) {
                notificationAdapter.submitList(it)
            }
        }
    }

    override fun onNotificationClick(notification: NotificationResponse) {
        TODO("Not yet implemented")
    }

}