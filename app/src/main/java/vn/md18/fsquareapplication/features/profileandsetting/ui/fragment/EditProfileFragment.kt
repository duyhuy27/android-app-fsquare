package vn.md18.fsquareapplication.features.profileandsetting.ui.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import vn.md18.fsquareapplication.utils.fslogger.FSLogger

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
            toolbarEditProfile.onClickBackPress = {
                val intent = Intent(requireContext(), MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    putExtra("SELECTED_TAB", MainViewModel.TAB_PROFILE)
                }
                startActivity(intent)
            }
        }
    }

    override fun addDataObserver() {
        viewModel.getProfile.observe(this@EditProfileFragment) {
            binding.apply {
                if (it is DataState.Success){
                    it.data?.let { it1 -> edtFullname.setText(it1.firstName.toString() + it1.lastName.toString()) }
                    it.data?.let { it1 -> FSLogger.e(it1.firstName)}
                    it.data?.let { it1 -> edtBirthDate.setText(it1.birthDay.toString()) }
                    it.data?.let { it1 -> edtEmail.setText(it1.email.toString()) }
                    edtNumberPhone.setText(it.data?.phone.toString())
                }
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

    private fun getCountryCodeFromName(countryName: String?): String? {
        return if (countryName != null) {
            binding.countryPicker.cpViewHelper.cpDataStore.countryList
                .firstOrNull { it.name.equals(countryName, ignoreCase = true) }?.alpha2
        } else {
            null
        }
    }

    private fun saveSelectedCountry() {
//        selectedCountry?.let { country ->
//            sharedPreferences.edit().putString("selected_country_name", country.name).apply()
//        } ?: run {
//
//        }
    }
}