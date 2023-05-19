package com.example.notesapplication.note.viewmodel

import androidx.lifecycle.ViewModel
import com.example.notesapplication.note.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapplication.note.models.NoteRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val noteRepository: NoteRepository) : ViewModel(){

    val notesLiveData get () = noteRepository.notesLiveData
    val statusLiveData get () = noteRepository.statusLiveData

    fun getNotes(){
        viewModelScope.launch(Dispatchers.IO){
            noteRepository.getNotes()
        }
    }

    fun updateNote(noteId: String, noteRequest: NoteRequest){
        viewModelScope.launch(Dispatchers.IO){
            noteRepository.updateNote(noteId, noteRequest)
        }
    }

    fun deleteNote(noteId: String){
        viewModelScope.launch(Dispatchers.IO){
            noteRepository.deleteNote(noteId)
        }
    }

    fun createNote(noteRequest: NoteRequest){
        viewModelScope.launch(Dispatchers.IO){
            noteRepository.createNote(noteRequest)
        }
    }
}