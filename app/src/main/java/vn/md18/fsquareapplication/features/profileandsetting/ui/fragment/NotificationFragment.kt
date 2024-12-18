package vn.md18.fsquareapplication.features.profileandsetting.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import vn.md18.fsquareapplication.core.base.BaseFragment
import vn.md18.fsquareapplication.data.network.model.response.NotificationResponse
import vn.md18.fsquareapplication.databinding.FragmentAddressBinding
import vn.md18.fsquareapplication.databinding.FragmentLanguageBinding
import vn.md18.fsquareapplication.databinding.FragmentNotificationBinding
import vn.md18.fsquareapplication.databinding.FragmentProfileBinding
import vn.md18.fsquareapplication.features.main.adapter.NotificationAdapter
import vn.md18.fsquareapplication.features.main.ui.MainActivity
import vn.md18.fsquareapplication.features.main.ui.fragment.HomeFragment
import vn.md18.fsquareapplication.features.main.viewmodel.MainViewModel
import vn.md18.fsquareapplication.features.main.viewmodel.NotificationViewmodel
import vn.md18.fsquareapplication.features.profileandsetting.viewmodel.ProfileViewModel
import javax.inject.Inject

@AndroidEntryPoint
class NotificationFragment : BaseFragment<FragmentNotificationBinding, NotificationViewmodel>(), NotificationAdapter.NotificationCallback {
    override val viewModel: NotificationViewmodel by activityViewModels()

    @Inject
    lateinit var notificationAdapter: NotificationAdapter
    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentNotificationBinding = FragmentNotificationBinding.inflate(layoutInflater)

    override fun getTagFragment(): String = NotificationFragment::class.java.simpleName

    override fun onViewLoaded() {
        viewModel.getListNotification()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.rvNotification.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = notificationAdapter
//            setLoadingMoreEnabled(true)
//            setPullRefreshEnabled(true)
        }
        notificationAdapter.setNotificationCallback(this)
    }

    override fun addViewListener() {
        binding.apply {
            toolbarNotification.onClickBackPress = {
                val intent = Intent(requireContext(), MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    putExtra("SELECTED_TAB", MainViewModel.TAB_PROFILE)
                }
                startActivity(intent)
                requireActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
            }

            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
                val intent = Intent(requireContext(), MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    putExtra("SELECTED_TAB", MainViewModel.TAB_PROFILE)
                }
                startActivity(intent)
                requireActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
            }
        }
    }

    override fun addDataObserver() {
        viewModel.apply {
            listNotification.observe(this@NotificationFragment) {
                notificationAdapter.submitList(it)
            }
        }
    }

    override fun onNotificationClick(notification: NotificationResponse) {
        TODO("Not yet implemented")
    }
}