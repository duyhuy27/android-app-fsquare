package vn.md18.fsquareapplication.features.profileandsetting.ui.fragment

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.core.base.BaseFragment
import vn.md18.fsquareapplication.data.model.DataState
import vn.md18.fsquareapplication.databinding.FragmentNewAddressBinding
import vn.md18.fsquareapplication.features.profileandsetting.adapter.DistrictAdapter
import vn.md18.fsquareapplication.features.profileandsetting.adapter.ProvinceAdapter
import vn.md18.fsquareapplication.features.profileandsetting.adapter.WardAdapter
import vn.md18.fsquareapplication.features.profileandsetting.viewmodel.LocationViewModel
import vn.md18.fsquareapplication.utils.Constant
import vn.md18.fsquareapplication.utils.extensions.showCustomToast
import javax.inject.Inject

@AndroidEntryPoint
class NewAddressFragment : BaseFragment<FragmentNewAddressBinding, LocationViewModel>() {
    override val viewModel: LocationViewModel by viewModels()

    @Inject
    lateinit var provinceAdapter: ProvinceAdapter
    @Inject
    lateinit var districtAdapter: DistrictAdapter
    @Inject
    lateinit var wardAdapter: WardAdapter


    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentNewAddressBinding =
        FragmentNewAddressBinding.inflate(layoutInflater)

    override fun getTagFragment(): String = NewAddressFragment::class.java.simpleName

    override fun onViewLoaded() {
        viewModel.getProvinceList()
    }

    override fun addViewListener() {
        binding.apply {
            imgProvince.setOnClickListener {
                showDialog(requireContext(),edtProvince)
            }
            imgDistrict.setOnClickListener {
                showDialogDistrict(requireContext(),edtDistrict)
            }
            imgSupDistrict.setOnClickListener {
                showDialogWard(requireContext(),edtSupDistrict)
            }

            btnAddNewAddress.setOnClickListener {
                viewModel.addLocationCustomerList("",edtDistrict.text.toString(), edtStreetAndApartment.text.toString(), edtSupDistrict.text.toString(), edtProvince.text.toString())
            }
        }
    }

    override fun addDataObserver() {
        viewModel.listProvince.observe(this@NewAddressFragment) {
            binding.apply {
                provinceAdapter.submitList(it)
            }
        }

        viewModel.listDistrict.observe(this@NewAddressFragment) {
            binding.apply {
                districtAdapter.submitList(it)
            }
        }

        viewModel.listWard.observe(this@NewAddressFragment) {
            binding.apply {
                wardAdapter.submitList(it)
            }
        }

        viewModel.addLocationState.observe(this@NewAddressFragment){
            when(it){
                is DataState.Error -> {
                    activity?.showCustomToast("Add to Location Failure", Constant.ToastStatus.FAILURE)
                }
                DataState.Loading -> {

                }
                is DataState.Success -> {
                    activity?.showCustomToast("Add to Location Success", Constant.ToastStatus.SUCCESS)
                }
            }
        }
    }

    private fun showDialog(context: Context, editText: EditText) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_province, null)
        val recyclerView = dialogView.findViewById<RecyclerView>(R.id.rcv_province)
        val alertDialog = AlertDialog.Builder(context)
            .setView(dialogView)
            .create()

        // Thiết lập sự kiện click cho Adapter
        provinceAdapter.setOnItemClickListener { provinceName ->
            // Cập nhật EditText khi chọn một tỉnh
            editText.setText(provinceName.provinceName)
            alertDialog.dismiss()

            viewModel.getDistrictList(provinceName.provinceID)
        }

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = provinceAdapter



        alertDialog.show()
    }

    private fun showDialogDistrict(context: Context, editText: EditText) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_province, null)
        val recyclerView = dialogView.findViewById<RecyclerView>(R.id.rcv_province)
        val alertDialog = AlertDialog.Builder(context)
            .setView(dialogView)
            .create()

        // Thiết lập sự kiện click cho Adapter
        districtAdapter.setOnItemClickListener { districtName ->
            // Cập nhật EditText khi chọn một tỉnh
            editText.setText(districtName.districtName)
            alertDialog.dismiss()

            viewModel.getWardList(districtName.districtID)
        }

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = districtAdapter



        alertDialog.show()
    }

    private fun showDialogWard(context: Context, editText: EditText) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_province, null)
        val recyclerView = dialogView.findViewById<RecyclerView>(R.id.rcv_province)
        val alertDialog = AlertDialog.Builder(context)
            .setView(dialogView)
            .create()

        // Thiết lập sự kiện click cho Adapter
        wardAdapter.setOnItemClickListener { wardName ->
            // Cập nhật EditText khi chọn một tỉnh
            editText.setText(wardName)
            alertDialog.dismiss()

        }

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = wardAdapter



        alertDialog.show()
    }

}