package com.example.notesapplication.api

import androidx.room.Update
import com.example.notesapplication.models.NoteResponse
import com.example.notesapplication.models.UserRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface NoteAPI {

    @GET("/note")
    suspend fun getNotes(): Response<List<NoteResponse>>

    @PUT("/note")
    suspend fun createNote(): Response<NoteResponse>

    @PUT("/note/{noteId}")
    suspend fun updateNote(@Path("noteId") noteId: String, @Body userRequest: UserRequest): Response<NoteResponse>

    @DELETE("/note/{noteId}")
    suspend fun deleteNote(@Path("noteId") noteId: String): Response<NoteResponse>

}