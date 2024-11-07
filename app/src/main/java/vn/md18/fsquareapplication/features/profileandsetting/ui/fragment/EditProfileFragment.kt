package vn.md18.fsquareapplication.features.profileandsetting.ui.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.hbb20.countrypicker.models.CPCountry
import dagger.hilt.android.AndroidEntryPoint
import vn.md18.fsquareapplication.core.base.BaseFragment
import vn.md18.fsquareapplication.databinding.FragmentEditProfileBinding
import vn.md18.fsquareapplication.databinding.FragmentProfileBinding
import vn.md18.fsquareapplication.features.main.ui.MainActivity
import vn.md18.fsquareapplication.features.main.viewmodel.MainViewModel
import vn.md18.fsquareapplication.features.profileandsetting.viewmodel.ProfileViewModel

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