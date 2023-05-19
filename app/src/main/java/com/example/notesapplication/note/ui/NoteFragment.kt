package com.example.notesapplication.note.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.notesapplication.R
import com.example.notesapplication.databinding.FragmentNoteBinding
import com.example.notesapplication.note.models.NoteRequest
import com.example.notesapplication.note.models.NoteResponse
import com.example.notesapplication.note.viewmodel.NoteViewModel
import com.example.notesapplication.utils.ApiResult
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NoteFragment : Fragment() {

    private var note: NoteResponse? = null
    private var _binding : FragmentNoteBinding? = null
    private val binding get() = _binding!!
    private val noteViewModel by viewModels<NoteViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentNoteBinding.inflate(inflater, container, false);
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInitialData()
        bindHandlers()
        bindObservers()
    }

    private fun bindHandlers() {
        binding.btnDelete.setOnClickListener {
            note?.let {
                noteViewModel.deleteNote(it._id)
            }
        }

        binding.btnSubmit.setOnClickListener {
            val title = binding.txtTitle.toString()
            val description = binding.txtDescription.toString()
            val noteRequest = NoteRequest(description, title)

            if (note != null){
                noteViewModel.createNote(noteRequest)
            }else
                noteViewModel.updateNote(note!!._id, noteRequest)

        }
    }

    private fun bindObservers() {
        noteViewModel.statusLiveData.observe(viewLifecycleOwner){
            when (it){
                is ApiResult.Success -> {
                    findNavController().popBackStack()
                }
                is ApiResult.Error -> {

                }

                is ApiResult.Loading -> {

                }
            }
        }
    }

    private fun setInitialData() {
        val jsonNote = arguments?.getString("note")
        if (jsonNote != null){
            note = Gson().fromJson(jsonNote, NoteResponse::class.java)
            note?.let{
                binding.txtTitle.setText(it.title)
                binding.txtDescription.setText(it.description)
            }
        }else{
            binding.addEditText.text = "Add Note"
        }
    }
}