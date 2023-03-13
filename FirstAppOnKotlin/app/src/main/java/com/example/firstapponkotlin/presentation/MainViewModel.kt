package com.example.firstapponkotlin.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.firstapponkotlin.data.Repository

class MainViewModel: ViewModel() {

    private val viewStateLiveData = MutableLiveData<MainViewState>(MainViewState.EMPTY)

    init {
        val notes = Repository.getAllNotes()
        viewStateLiveData.value = MainViewState.Value(notes)
    }

    fun observeViewState(): LiveData<MainViewState> = viewStateLiveData

}