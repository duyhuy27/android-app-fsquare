package vn.md18.fsquareapplication.features.profileandsetting.ui.fragment

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.core.base.BaseFragment
import vn.md18.fsquareapplication.data.model.DataState
import vn.md18.fsquareapplication.data.network.model.response.location.GetLocationCustomerResponse
import vn.md18.fsquareapplication.databinding.FragmentNewAddressBinding
import vn.md18.fsquareapplication.features.main.ui.MainActivity
import vn.md18.fsquareapplication.features.main.viewmodel.MainViewModel
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

    private var location: GetLocationCustomerResponse? = null

    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentNewAddressBinding =
        FragmentNewAddressBinding.inflate(layoutInflater)

    override fun getTagFragment(): String = NewAddressFragment::class.java.simpleName
    var provinceIdUpdate = ""
    var districtIdUpdate = ""

    override fun onViewLoaded() {
        location = requireArguments().getSerializable("location") as? GetLocationCustomerResponse

        if(location != null){
            binding.apply {
                btnAddNewAddress.text = "Update Address"
                edtTitle.setText(location?.title ?: "")
                edtStreetAndApartment.setText(location?.address ?: "")
                edtSupDistrict.setText(location?.wardName ?: "")
                edtDistrict.setText(location?.districtName ?: "")
                edtProvince.setText(location?.provinceName ?: "")
                provinceIdUpdate = getProvinceIdByName(edtProvince.text.toString()).toString()
                districtIdUpdate = getDistrictIdByName(edtDistrict.text.toString()).toString()
            }
        }
    }

    override fun addViewListener() {
        binding.apply {
            binding.apply {
                toolbarAddAddress.onClickBackPress = {
                    requireActivity().onBackPressedDispatcher.onBackPressed()
                }
            }
            imgProvince.setOnClickListener {
                showDialog(requireContext(), edtProvince)
            }
            imgDistrict.setOnClickListener {
                showDialogDistrict(requireContext(), edtDistrict)
            }
            imgSupDistrict.setOnClickListener {
                showDialogWard(requireContext(), edtSupDistrict)
            }

            btnAddNewAddress.setOnClickListener {
                if(location != null) {
                    val provinceName = edtProvince.text.toString()
                    val districtName = edtDistrict.text.toString()
                    val wardName = edtSupDistrict.text.toString()

                    viewModel.updateLocationCustomerList(
                        id = location!!.id,
                        title = edtTitle.text.toString(),
                        address = edtStreetAndApartment.text.toString(),
                        wardName = if (wardName.isNotEmpty()) wardName else "",  // Nếu ward không rỗng thì cập nhật
                        districtName = if (districtName.isNotEmpty()) districtName else "",  // Nếu district không rỗng thì cập nhật
                        provinceName = if (provinceName.isNotEmpty()) provinceName else "",  // Nếu province không rỗng thì cập nhật
                        isDefault = location!!.isDefault
                    )
                } else {
                    viewModel.addLocationCustomerList(
                        edtTitle.text.toString(),
                        edtStreetAndApartment.text.toString(),
                        edtSupDistrict.text.toString(),
                        edtDistrict.text.toString(),
                        edtProvince.text.toString()
                    )
                }
            }
        }
    }

    override fun addDataObserver() {
        viewModel.apply {
            listProvince.observe(viewLifecycleOwner) { provinceList ->
                provinceAdapter.submitList(provinceList)
            }
            listDistrict.observe(viewLifecycleOwner) { districtList ->
                districtAdapter.submitList(districtList)
            }
            listWard.observe(viewLifecycleOwner) { wardList ->
                wardAdapter.submitList(wardList)
            }

            addLocationState.observe(viewLifecycleOwner) { dataState ->
                when (dataState) {
                    is DataState.Success -> {
                        val bundle = Bundle().apply {
                            putBoolean("AddAddress", true)
                        }
                        activity?.showCustomToast("Add địa chỉ thành công!", Constant.ToastStatus.SUCCESS)
                        parentFragmentManager.setFragmentResult("AddAddress", bundle)
                        findNavController().navigate(R.id.action_newAddressFragment_to_addressFragment)
                    }
                    is DataState.Error -> {
                        activity?.showCustomToast("Có lỗi xảy ra", Constant.ToastStatus.FAILURE)
                    }
                    DataState.Loading -> {
                    }
                }
            }

            updateLocationState.observe(viewLifecycleOwner) { dataState ->
                when (dataState) {
                    is DataState.Success -> {
                        activity?.showCustomToast("Update địa chỉ thành công!", Constant.ToastStatus.SUCCESS)
                        findNavController().navigate(R.id.action_newAddressFragment_to_addressFragment)
                    }
                    is DataState.Error -> {
                        activity?.showCustomToast("Có lỗi xảy ra", Constant.ToastStatus.FAILURE)
                    }
                    DataState.Loading -> {
                    }
                }
            }
        }
    }


    private fun showDialog(context: Context, editText: EditText) {
        viewModel.getProvinceList()
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_province, null)
        val recyclerView = dialogView.findViewById<RecyclerView>(R.id.rcv_province)
        val alertDialog = AlertDialog.Builder(context)
            .setView(dialogView)
            .create()

        provinceAdapter.setOnItemClickListener { provinceName ->
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

        districtAdapter.setOnItemClickListener { districtName ->
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
        wardAdapter.setOnItemClickListener { wardName ->
            editText.setText(wardName)
            alertDialog.dismiss()
        }
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = wardAdapter
        alertDialog.show()
    }

    fun getProvinceIdByName(provinceName: String): String? {
        val provinceList = viewModel.listProvince.value ?: return null
        return provinceList.find { it.provinceName == provinceName }?.provinceID
    }

    fun getDistrictIdByName(districtName: String): String? {
        val districtList = viewModel.listDistrict.value ?: return null
        return districtList.find { it.districtName == districtName }?.districtID
    }


}