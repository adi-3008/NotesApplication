package com.example.notesapplication.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.notesapplication.R
import com.example.notesapplication.databinding.FragmentRegisterBinding
import com.example.notesapplication.models.UserRequest
import com.example.notesapplication.storage.SharedPreference
import com.example.notesapplication.utils.ApiResult
import com.example.notesapplication.utils.Constants.USER_TOKEN
import com.example.notesapplication.viewmodels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding : FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val authViewModel by viewModels<AuthViewModel>()

    @Inject
    lateinit var sharedPreferences : SharedPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        if(sharedPreferences.getData(USER_TOKEN) != null)
            findNavController().navigate(R.id.action_registerFragment_to_mainFragment)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSignUp.setOnClickListener {
            val validationResult = validateUserInput()
            if (validationResult.first){
                authViewModel.registerUser(getUserRequest())
            }
            else{
                binding.txtError.text = validationResult.second
            }
        }

        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment3)
        }

        bindObserver()
    }

    private fun validateUserInput(): Pair<Boolean, String> {
        val userRequest = getUserRequest()
        return authViewModel.validateCredentials(userRequest.username, userRequest.email, userRequest.password, false)
    }

    private fun getUserRequest(): UserRequest {
        val username = binding.txtUsername.text.toString()
        val email = binding.txtEmail.text.toString()
        val password = binding.txtPassword.text.toString()
        return UserRequest(email, password, username)
    }

    private fun bindObserver() {
        authViewModel.userResponseLiveData.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = false
            when (it) {
                is ApiResult.Success -> {
                    // TODO :- Token implementation
                    sharedPreferences.setData(USER_TOKEN, it.data!!.token)
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