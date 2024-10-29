package vn.md18.fsquareapplication.features.profileandsetting.ui.fragment

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.core.base.BaseFragment
import vn.md18.fsquareapplication.data.model.DataState
import vn.md18.fsquareapplication.databinding.FragmentLanguageBinding
import vn.md18.fsquareapplication.databinding.FragmentNewAddressBinding
import vn.md18.fsquareapplication.databinding.FragmentProfileBinding
import vn.md18.fsquareapplication.features.main.adapter.FavoriteAdapter
import vn.md18.fsquareapplication.features.profileandsetting.adapter.ProvinceAdapter
import vn.md18.fsquareapplication.features.profileandsetting.viewmodel.LocationViewModel
import vn.md18.fsquareapplication.features.profileandsetting.viewmodel.ProfileViewModel
import vn.md18.fsquareapplication.utils.Constant
import vn.md18.fsquareapplication.utils.extensions.showCustomToast
import javax.inject.Inject

@AndroidEntryPoint
class NewAddressFragment : BaseFragment<FragmentNewAddressBinding, LocationViewModel>() {
    override val viewModel: LocationViewModel by viewModels()

    @Inject
    lateinit var provinceAdapter: ProvinceAdapter


    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentNewAddressBinding =
        FragmentNewAddressBinding.inflate(layoutInflater)

    override fun getTagFragment(): String = NewAddressFragment::class.java.simpleName

    override fun onViewLoaded() {

    }

    override fun addViewListener() {
        viewModel.getProvinceList()
        binding.apply {
            imgProvince.setOnClickListener {
                showDialog(requireContext() )
            }
        }
    }

    override fun addDataObserver() {
        viewModel.listProvince.observe(this@NewAddressFragment) {
            binding.apply {
                provinceAdapter.submitList(it)
            }
        }
    }

    private fun showDialog(context: Context){
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_province, null)
        val recyclerView = dialogView.findViewById<RecyclerView>(R.id.rcv_province)

        provinceAdapter.setOnclick()
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = provinceAdapter

        val alertDialog = AlertDialog.Builder(context)
            .setView(dialogView)
            .create()

        alertDialog.show()
    }
}