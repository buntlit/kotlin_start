package com.example.firstapponkotlin.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firstapponkotlin.data.NotesRepository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainViewModel(notesRepository: NotesRepository) : ViewModel() {

    private val notesLiveData = MutableLiveData<MainViewState>()

    init {
        notesRepository.observeNotes()
            .onEach {
                notesLiveData.value = MainViewState.Value(it)
            }
            .launchIn(viewModelScope)
    }

    fun observeViewState(): LiveData<MainViewState> = notesLiveData

}
