package vn.md18.fsquareapplication.features.profileandsetting.ui.fragment

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import com.hbb20.countrypicker.models.CPCountry
import dagger.hilt.android.AndroidEntryPoint
import vn.md18.fsquareapplication.core.base.BaseFragment
import vn.md18.fsquareapplication.data.model.DataState
import vn.md18.fsquareapplication.databinding.FragmentEditProfileBinding
import vn.md18.fsquareapplication.databinding.FragmentProfileBinding
import vn.md18.fsquareapplication.features.main.ui.MainActivity
import vn.md18.fsquareapplication.features.main.viewmodel.MainViewModel
import vn.md18.fsquareapplication.features.profileandsetting.viewmodel.ProfileViewModel
import vn.md18.fsquareapplication.utils.Constant
import vn.md18.fsquareapplication.utils.extensions.showCustomToast
import vn.md18.fsquareapplication.utils.fslogger.FSLogger
import java.util.Calendar

@AndroidEntryPoint
class EditProfileFragment : BaseFragment<FragmentEditProfileBinding, ProfileViewModel>() {

    override val viewModel: ProfileViewModel by viewModels()
//    private val sharedPreferences: SharedPreferences by lazy {
//        requireContext().getSharedPreferences("CountryPrefs", Context.MODE_PRIVATE)
//    }

    private var selectedCountry: CPCountry? = null
    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentEditProfileBinding =
        FragmentEditProfileBinding.inflate(layoutInflater)

    override fun getTagFragment(): String {
        return "EditProfileFragment"
    }

    override fun onViewLoaded() {
        setupCountryPickerView()
        viewModel.getProfile()
    }

    override fun addViewListener() {
        binding.apply {
            btnDatePicker.setOnClickListener {
                val calendar = Calendar.getInstance()
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DAY_OF_MONTH)

                val datePickerDialog = DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
                    val formattedDate = "$selectedYear-${selectedMonth + 1}-$selectedDay"
                    edtBirth.setText(formattedDate)
                }, year, month, day)
                datePickerDialog.show()
            }

            btnSubmit.setOnClickListener {
                viewModel.updateProfile(edtFirstName.getText().toString(), edtLastName.getText(), edtBirth.getText().toString(), edtNumberPhone.getText().toString(), "")
            }
            toolbarEditProfile.onClickBackPress = {
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
        viewModel.getProfile.observe(this@EditProfileFragment) {
            binding.apply {
                if (it is DataState.Success){
                    it.data?.let { it1 -> edtFirstName.setText(it1.firstName) }
                    it.data?.let { it1 -> edtLastName.setText(it1.lastName) }
                    it.data?.let { it1 -> edtBirth.setText(it1.birthDay) }
                    it.data?.let { it1 -> edtEmail.setText(it1.email) }
                    edtNumberPhone.setText(it.data?.phone.toString())
                }
            }
        }

        viewModel.updateProfileState.observe(this@EditProfileFragment) {
            if(it is DataState.Success){
                activity?.showCustomToast("Update Profile Success", Constant.ToastStatus.SUCCESS)
            }else{
                activity?.showCustomToast("Update Profile Failed", Constant.ToastStatus.FAILURE)
            }
        }
    }

    private fun setupCountryPickerView() {
        val countryPicker = binding.countryPicker
        countryPicker.cpViewHelper.cpViewConfig.viewTextGenerator = { cpCountry: CPCountry ->
            "${cpCountry.name}"
        }

//        val savedCountryName = sharedPreferences.getString("selected_country_name", null)
//        if (savedCountryName != null) {
//            countryPicker.cpViewHelper.setCountryForAlphaCode(getCountryCodeFromName(savedCountryName))
//        }
        countryPicker.cpViewHelper.onCountryChangedListener = { selectedCountry ->
            this.selectedCountry = selectedCountry
        }
        countryPicker.cpViewHelper.refreshView()
    }

//    private fun getCountryCodeFromName(countryName: String?): String? {
//        return if (countryName != null) {
//            binding.countryPicker.cpViewHelper.cpDataStore.countryList
//                .firstOrNull { it.name.equals(countryName, ignoreCase = true) }?.alpha2
//        } else {
//            null
//        }
//    }

    private fun saveSelectedCountry() {
//        selectedCountry?.let { country ->
//            sharedPreferences.edit().putString("selected_country_name", country.name).apply()
//        } ?: run {
//
//        }
    }
}