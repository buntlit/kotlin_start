package com.example.firstapponkotlin.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlin.random.Random

private val idRandom = Random(0)
val noteId: Long
    get() = idRandom.nextLong()

object Repository : NotesRepository {

    private val notes: MutableList<Note> = mutableListOf(
        Note(
            title = "Моя первая заметка",
            note = "Kotlin очень краткий, но при этом выразительный язык",
            color = NoteColor.WHITE
        ),
        Note(
            title = "Моя первая заметка",
            note = "Kotlin очень краткий, но при этом выразительный язык",
            color = NoteColor.BLUE
        ),
        Note(
            title = "Моя первая заметка",
            note = "Kotlin очень краткий, но при этом выразительный язык",
            color = NoteColor.GREEN
        ),
        Note(
            title = "Моя первая заметка",
            note = "Kotlin очень краткий, но при этом выразительный язык",
            color = NoteColor.PINK
        ),
        Note(
            title = "Моя первая заметка",
            note = "Kotlin очень краткий, но при этом выразительный язык",
            color = NoteColor.RED
        ),
        Note(
            title = "Моя первая заметка",
            note = "Kotlin очень краткий, но при этом выразительный язык",
            color = NoteColor.YELLOW
        ),
        Note(
            title = "Моя первая заметка",
            note = "Kotlin очень краткий, но при этом выразительный язык",
            color = NoteColor.VIOLET
        ),

        Note(
            title = "Моя первая заметка",
            note = "Kotlin очень краткий, но при этом выразительный язык",
            color = NoteColor.WHITE
        ),
        Note(
            title = "Моя первая заметка",
            note = "Kotlin очень краткий, но при этом выразительный язык",
            color = NoteColor.BLUE
        ),
        Note(
            title = "Моя первая заметка",
            note = "Kotlin очень краткий, но при этом выразительный язык",
            color = NoteColor.GREEN
        ),
        Note(
            title = "Моя первая заметка",
            note = "Kotlin очень краткий, но при этом выразительный язык",
            color = NoteColor.PINK
        ),
        Note(
            title = "Моя первая заметка",
            note = "Kotlin очень краткий, но при этом выразительный язык",
            color = NoteColor.RED
        ),
        Note(
            title = "Моя первая заметка",
            note = "Kotlin очень краткий, но при этом выразительный язык",
            color = NoteColor.YELLOW
        ),
        Note(
            title = "Моя первая заметка",
            note = "Kotlin очень краткий, но при этом выразительный язык",
            color = NoteColor.VIOLET
        )
    )

    private val allNotes = MutableLiveData(getListToNotify())
    override fun observeNotes(): LiveData<List<Note>> {
        return allNotes
    }

    override fun addOrReplace(newNote: Note) {
        notes.find { it.id == newNote.id }?.let {
            if (it == newNote) return
            notes.remove(it)
        }

        notes.add(newNote)

        allNotes.postValue(getListToNotify())
    }

    private fun getListToNotify(): List<Note> = notes.toMutableList().also { it.reverse() }

}