package com.example.firstapponkotlin.data.db

import com.example.firstapponkotlin.model.Note
import com.example.firstapponkotlin.model.User
import kotlinx.coroutines.flow.Flow

interface DatabaseProvider {
    fun observeNotes(): Flow<List<Note>>
    suspend fun addOrReplace(newNote: Note)
    fun getCurrentUser(): User?
    suspend fun deleteNote(note: Note)
}