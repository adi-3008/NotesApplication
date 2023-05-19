package com.example.notesapplication.note.repository

import android.net.Network
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notesapplication.note.api.NoteAPI
import com.example.notesapplication.note.models.NoteRequest
import com.example.notesapplication.note.models.NoteResponse
import com.example.notesapplication.utils.ApiResult
import com.example.notesapplication.utils.Constants
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(private val notesAPI : NoteAPI){

    private val _notesLiveData = MutableLiveData<ApiResult<List<NoteResponse>>>()

    val notesLiveData : LiveData<ApiResult<List<NoteResponse>>>
        get() = _notesLiveData

    private val _statusLiveData = MutableLiveData<ApiResult<String>>()
    val statusLiveData: LiveData<ApiResult<String>>
        get() = _statusLiveData

    suspend fun getNotes(){
        _notesLiveData.postValue(ApiResult.Loading())
        val response = notesAPI.getNotes()
        handleResponse(response)
    }

    private fun handleResponse(response: Response<List<NoteResponse>>) {
        if (response.isSuccessful && response.body() != null) {
            _notesLiveData.postValue(ApiResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _notesLiveData.postValue(ApiResult.Error(null, errorObj.getString("message")))
        } else {
            _notesLiveData.postValue(ApiResult.Error(null, "Something went wrong"))
        }
        Log.d(Constants.TAG, response.body().toString())
    }

    suspend fun createNote(noteRequest: NoteRequest){
        _statusLiveData.postValue(ApiResult.Loading())
        val response = notesAPI.createNote(noteRequest)
        handleResponse(response, "Note Created")
    }

    suspend fun deleteNote(noteId: String){
        _statusLiveData.postValue(ApiResult.Loading())
        val response = notesAPI.deleteNote(noteId)
        handleResponse(response, "Note Deleted")
    }

    suspend fun updateNote(noteId: String, noteRequest: NoteRequest){
        _statusLiveData.postValue(ApiResult.Loading())
        val response = notesAPI.updateNote(noteId, noteRequest)
        handleResponse(response, "Note Updated")
    }

    private fun handleResponse(response: Response<NoteResponse>, message: String) {
        if (response.isSuccessful && response.body() != null) {
            _statusLiveData.postValue(ApiResult.Success(message))
        } else {
            _statusLiveData.postValue(ApiResult.Error(null, "Something went wrong"))
        }
    }


}