package com.example.notesapplication.login.viewmodels

import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapplication.login.models.UserRequest
import com.example.notesapplication.login.models.UserResponse
import com.example.notesapplication.login.repository.UserRepository
import com.example.notesapplication.utils.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    val userResponseLiveData: LiveData<ApiResult<UserResponse>>
        get() = userRepository.userResponseLiveData

    fun registerUser(userRequest: UserRequest){
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.registerUser(userRequest)

        }
    }

    fun loginUser(userRequest: UserRequest){
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.loginUser(userRequest)
        }
    }

    fun validateCredentials(username: String, emailAddress: String, password: String, isLogin: Boolean) : Pair<Boolean, String>{
        var result = Pair(true, "")
        if (!isLogin && TextUtils.isEmpty(username) || TextUtils.isEmpty(emailAddress) || TextUtils.isEmpty(password)){
            result = Pair(false, "Please provide credentials")
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()){
            result = Pair(false, "Please provide valid email address")
        }
        else if (password.length < 8){
            result = Pair(false, "Password length should be greater than or eqaul to 8")
        }
        return result
    }
    
}