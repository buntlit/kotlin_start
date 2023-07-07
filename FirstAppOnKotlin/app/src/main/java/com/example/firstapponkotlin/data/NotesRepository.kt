package com.example.firstapponkotlin.data

import com.example.firstapponkotlin.model.Note
import com.example.firstapponkotlin.model.User
import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    fun observeNotes(): Flow<List<Note>>
    suspend fun addOrReplace(newNote: Note)
    suspend fun getCurrentUser(): User?

    suspend fun deleteNote(note: Note)
}