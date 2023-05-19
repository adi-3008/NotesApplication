package com.example.notesapplication.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notesapplication.api.UserAPI
import com.example.notesapplication.models.UserRequest
import com.example.notesapplication.models.UserResponse
import com.example.notesapplication.utils.Constants.TAG
import com.example.notesapplication.utils.ApiResult
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(private val userAPI: UserAPI){

    private val _userResponseLivedata = MutableLiveData<ApiResult<UserResponse>>()
    val userResponseLiveData : LiveData<ApiResult<UserResponse>>
        get() = _userResponseLivedata

    suspend fun registerUser(userRequest: UserRequest) {
        _userResponseLivedata.postValue(ApiResult.Loading())
        val response = userAPI.signup(userRequest)
        handleResponse(response)
    }

    suspend fun loginUser(userRequest: UserRequest){
        _userResponseLivedata.postValue(ApiResult.Loading())
        val response = userAPI.signin(userRequest)
        handleResponse(response)
    }

    private fun handleResponse(response: Response<UserResponse>) {
        if (response.isSuccessful && response.body() != null) {
            _userResponseLivedata.postValue(ApiResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _userResponseLivedata.postValue(ApiResult.Error(null, errorObj.getString("message")))
        } else {
            _userResponseLivedata.postValue(ApiResult.Error(null, "Something went wrong"))
        }
        Log.d(TAG, response.body().toString())
    }
}