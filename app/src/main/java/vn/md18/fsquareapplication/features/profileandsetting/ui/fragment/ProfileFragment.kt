package vn.md18.fsquareapplication.features.profileandsetting.ui.fragment
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.hbb20.CountryPickerView
import com.hbb20.countrypicker.models.CPCountry
import dagger.hilt.android.AndroidEntryPoint
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.core.base.BaseFragment
import vn.md18.fsquareapplication.databinding.FragmentProfileBinding
import vn.md18.fsquareapplication.features.profileandsetting.viewmodel.ProfileViewModel
import vn.md18.fsquareapplication.utils.Constant
import vn.md18.fsquareapplication.utils.extensions.showCustomToast

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>() {

    override val viewModel: ProfileViewModel by viewModels()


    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentProfileBinding =
        FragmentProfileBinding.inflate(layoutInflater)

    override fun getTagFragment(): String {
        return "ProfileFragment"
    }

    override fun onViewLoaded() {

    }

    override fun addViewListener() {

    }

    override fun addDataObserver() {
    }
}
