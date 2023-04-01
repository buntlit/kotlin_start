package com.example.firstapponkotlin.data.db

import androidx.lifecycle.LiveData
import com.example.firstapponkotlin.model.Note
import com.example.firstapponkotlin.model.User

interface DatabaseProvider {
    fun observeNotes(): LiveData<List<Note>>
    fun addOrReplace(newNote: Note): LiveData<Result<Note>>
    fun getCurrentUser(): User?
    fun deleteNote(note: Note): LiveData<Result<Note>>
}