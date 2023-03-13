package com.example.firstapponkotlin.presentation

import com.example.firstapponkotlin.data.Note

sealed class MainViewState {
    data class Value(val notes: List<Note>) : MainViewState()
    object EMPTY: MainViewState()
}