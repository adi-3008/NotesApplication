package com.example.notesapplication.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.notesapplication.R
import com.example.notesapplication.databinding.FragmentLoginBinding
import com.example.notesapplication.models.UserRequest
import com.example.notesapplication.utils.ApiResult
import com.example.notesapplication.viewmodels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val authViewModel by viewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener{
            val validationResult = validateUserInput()
            if (validationResult.first)
                authViewModel.loginUser(getUserRequest())
            else
                binding.txtError.text = validationResult.second
        }

        binding.btnSignUp.setOnClickListener {
            // TODO
            findNavController().popBackStack()
        }

        bindObserver()
    }

    private fun validateUserInput(): Pair<Boolean, String> {
        val userRequest = getUserRequest()
        return authViewModel.validateCredentials(userRequest.username, userRequest.email, userRequest.password, true)
    }

    private fun getUserRequest(): UserRequest {
        val email = binding.txtEmail.text.toString()
        val password = binding.txtPassword.text.toString()
        return UserRequest(email, password, "")
    }

    private fun bindObserver() {
        authViewModel.userResponseLiveData.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = false
            when (it) {
                is ApiResult.Success -> {
                    // TODO :- Token implementation
                    findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
                }

                is ApiResult.Error -> {
                    binding.txtError.text = it.message
                }

                is ApiResult.Loading -> {
                    binding.progressBar.isVisible = true
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}