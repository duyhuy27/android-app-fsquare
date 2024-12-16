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
import java.util.Locale

@AndroidEntryPoint
class EditProfileFragment : BaseFragment<FragmentEditProfileBinding, ProfileViewModel>() {

    override val viewModel: ProfileViewModel by viewModels()

    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentEditProfileBinding =
        FragmentEditProfileBinding.inflate(layoutInflater)

    override fun getTagFragment(): String {
        return "EditProfileFragment"
    }

    override fun onViewLoaded() {
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
                    val formattedDate = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay)
                    edtBirth.setText(formattedDate)
                }, year, month, day)
                datePickerDialog.show()
            }

            btnSubmit.setOnClickListener {
                val firstName = edtFirstName.getText().toString().trim()
                val lastName = edtLastName.getText().toString().trim()
                val birthDate = edtBirth.text.toString().trim()
                val phoneNumber = edtNumberPhone.getText().toString().trim()

                if (firstName.isEmpty()) {
                    activity?.showCustomToast("Chưa nhập họ", Constant.ToastStatus.FAILURE)
                    return@setOnClickListener
                }

                if (lastName.isEmpty()) {
                    activity?.showCustomToast("Chưa nhập tên", Constant.ToastStatus.FAILURE)
                    return@setOnClickListener
                }

                if (birthDate.isEmpty()) {
                    activity?.showCustomToast("Chưa nhập ngày sinh", Constant.ToastStatus.FAILURE)
                    return@setOnClickListener
                }

                if (phoneNumber.isEmpty()) {
                    activity?.showCustomToast("Chưa nhập số điện thoại", Constant.ToastStatus.FAILURE)
                    return@setOnClickListener
                }

                if (!isValidVietnamPhoneNumber(phoneNumber)) {
                    activity?.showCustomToast("Không đúng định dạng số điện thoại Việt Nam", Constant.ToastStatus.FAILURE)
                    return@setOnClickListener
                }
                viewModel.updateProfile(firstName, lastName, birthDate, phoneNumber, "")
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
                if (it is DataState.Success) {
                    it.data?.let { data ->
                        edtFirstName.setText(data.firstName)
                        edtLastName.setText(data.lastName)
                        val formattedBirthDate = formatDateToDisplay(data.birthDay)
                        edtBirth.setText(formattedBirthDate)
                        edtEmail.setText(data.email)
                        edtNumberPhone.setText(data.phone.toString())
                    }
                }
            }
        }

        viewModel.updateProfileState.observe(this@EditProfileFragment) {
            if (it is DataState.Success) {
                activity?.showCustomToast("Cập nhật thông tin cá nhân thành công", Constant.ToastStatus.SUCCESS)
            } else {
                activity?.showCustomToast("Cập nhật thông tin cá nhân thất bại", Constant.ToastStatus.FAILURE)
            }
        }
    }

    private fun isValidVietnamPhoneNumber(phone: String): Boolean {
        val vietnamPhoneRegex = Regex("^(03|05|07|08|09)[0-9]{8}\$")
        return vietnamPhoneRegex.matches(phone)
    }

    private fun formatDateToDisplay(inputDate: String?): String {
        if (inputDate.isNullOrEmpty()) return ""
        return try {
            val inputFormat = java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            inputFormat.timeZone = java.util.TimeZone.getTimeZone("UTC")

            val outputFormat = java.text.SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

            val parsedDate = inputFormat.parse(inputDate)
            outputFormat.format(parsedDate)
        } catch (e: Exception) {
            inputDate
        }
    }

}
