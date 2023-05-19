package com.example.notesapplication.note.models

data class NoteResponse(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val description: String,
    val title: String,
    val updateAt: String,
    val userId: String
)