package com.example.firstapponkotlin.data.db

import androidx.lifecycle.LiveData
import com.example.firstapponkotlin.data.Note

interface DatabaseProvider {
    fun observeNotes(): LiveData<List<Note>>
    fun addOrReplace(newNote: Note): LiveData<Result<Note>>
}