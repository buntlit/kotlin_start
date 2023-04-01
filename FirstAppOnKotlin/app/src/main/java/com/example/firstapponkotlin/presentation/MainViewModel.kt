package com.example.firstapponkotlin.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.firstapponkotlin.data.NotesRepository

class MainViewModel(private val notesRepository: NotesRepository): ViewModel() {

    fun observeViewState(): LiveData<MainViewState> = notesRepository.observeNotes()
        .map {
            MainViewState.Value(it)
        }

}
