package vn.md18.fsquareapplication.features.auth.ui.fragment

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.core.base.BaseFragment
import vn.md18.fsquareapplication.databinding.FragmentLoginBinding
import vn.md18.fsquareapplication.features.auth.viewmodel.AuthViewModel
import vn.md18.fsquareapplication.features.main.ui.MainActivity

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, AuthViewModel>() {
    override val viewModel: AuthViewModel by viewModels()
    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentLoginBinding = FragmentLoginBinding.inflate(layoutInflater)

    override fun getTagFragment(): String {
        return "LoginFragment"
    }

    override fun onViewLoaded() {
        Log.d("auth", "da vao man auth login")
    }

    override fun addViewListener() {
                binding.btnLogin.setOnClickListener {
                    navigateToLoginWithEmailFragment()
                }

                binding.btnSingup.setOnClickListener {
                    navigateToSignUpFragment()
                }

                binding.btnGuest.setOnClickListener {
                    navigateToHomePage()
                }
    }

    override fun addDataObserver() {

    }

    private fun navigateToLoginWithEmailFragment(){
        findNavController().navigate(R.id.action_loginFragment_to_loginWithEmailFragment)
    }

    private fun navigateToHomePage() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun navigateToSignUpFragment(){
        findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
    }

}