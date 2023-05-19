package com.example.notesapplication.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.notesapplication.R
import com.example.notesapplication.note.api.NoteAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainFragment : Fragment() {

    @Inject
    lateinit var notesApi : NoteAPI

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        CoroutineScope(Dispatchers.IO).launch{
            val response = notesApi.getNotes()
        }

        return inflater.inflate(R.layout.fragment_main, container, false)
    }
}