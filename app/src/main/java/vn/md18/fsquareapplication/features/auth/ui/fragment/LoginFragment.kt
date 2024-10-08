package vn.md18.fsquareapplication.features.auth.ui.fragment

import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.core.base.BaseFragment
import vn.md18.fsquareapplication.databinding.FragmentLoginBinding
import vn.md18.fsquareapplication.features.auth.viewmodel.AuthViewModel

class LoginFragment : BaseFragment<FragmentLoginBinding, AuthViewModel>() {
    override val viewModel: AuthViewModel by viewModels()
    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentLoginBinding = FragmentLoginBinding.inflate(layoutInflater)

    override fun getTagFragment(): String {
        TODO("Not yet implemented")
    }

    override fun onViewLoaded() {
        TODO("Not yet implemented")
    }

    override fun addViewListener() {
        //        binding.btnSubmit.setOnClickListener {
        //            navigateToLoginWithEmailFragment()
        //        }

        //        binding.signUpText.setOnClickListener {
        //            navigateToSignUpFragment()
        //        }

        //        binding.btnGuestLogin.setOnClickListener {
        //            navigateToHomeScreen()
        //        }
    }

    override fun addDataObserver() {

    }

    private fun navigateToLoginWithEmailFragment(){
        findNavController().navigate(R.id.action_loginFragment_to_loginWithEmailFragment)
    }

    private fun navigateToHomeScreen(){

    }

    private fun navigateToSignUpFragment(){
        findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
    }

}