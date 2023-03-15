package com.example.firstapponkotlin.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.firstapponkotlin.data.Repository

class MainViewModel: ViewModel() {

    fun observeViewState(): LiveData<MainViewState> = Repository.observeNotes()
        .map {
            if (it.isEmpty()) MainViewState.EMPTY else MainViewState.Value(it)
        }

}
