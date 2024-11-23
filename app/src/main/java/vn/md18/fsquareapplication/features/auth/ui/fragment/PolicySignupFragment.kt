package vn.md18.fsquareapplication.features.auth.ui.fragment

import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.core.base.BaseFragment
import vn.md18.fsquareapplication.databinding.FragmentPolicySignupBinding
import vn.md18.fsquareapplication.features.auth.viewmodel.AuthViewModel
import vn.md18.fsquareapplication.utils.Constant
import vn.md18.fsquareapplication.utils.extensions.showCustomToast

class PolicySignupFragment : BaseFragment<FragmentPolicySignupBinding, AuthViewModel>() {
    override val viewModel: AuthViewModel by viewModels()

    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentPolicySignupBinding = FragmentPolicySignupBinding.inflate(layoutInflater)

    override fun getTagFragment(): String {
        return "PolicySignupFragment"
    }

    override fun onViewLoaded() {

    }

    override fun addViewListener() {
        binding.apply {
            btnConfirm.setOnClickListener {
                if(chkAgree.isChecked){
                    navigateToSuccessfullyFragment()
                }else{
                    activity?.showCustomToast(getString(R.string.checkbox_policy), Constant.ToastStatus.WARNING)
                }
            }
        }
    }

    private fun navigateToSuccessfullyFragment() {
        findNavController().navigate(R.id.action_policySignupFragment_to_successfullyCreateAccountFragment)
    }

}