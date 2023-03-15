package com.example.firstapponkotlin.presentation

import androidx.lifecycle.ViewModel
import com.example.firstapponkotlin.data.Note
import com.example.firstapponkotlin.data.Repository

class NoteViewModel(var note: Note?):ViewModel() {

    fun updateNote(text: String){
        note = (note ?: generateNote()).copy(note = text)
    }

    fun updateTitle(text: String){
        note = (note ?: generateNote()).copy(title = text)
    }

    override fun onCleared() {
        super.onCleared()
        note?.let { Repository.addOrReplace(it) }
    }

    private fun generateNote(): Note{
        return Note()
    }

}