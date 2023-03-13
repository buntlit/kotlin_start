package com.example.firstapponkotlin.data

interface NotesRepository {
    fun getAllNotes():List<Note>
}