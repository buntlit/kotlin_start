package com.example.firstapponkotlin.data

import androidx.lifecycle.LiveData
import com.example.firstapponkotlin.data.db.FireStoreDatabaseProvider
import com.example.firstapponkotlin.model.Note
import kotlin.random.Random

private val idRandom = Random(0)
val noteId: Long
    get() = idRandom.nextLong()

class Repository(private val provider: FireStoreDatabaseProvider) : NotesRepository {

//    private val notes: MutableList<Note> = mutableListOf(
//        Note(
//            title = "Моя первая заметка",
//            note = "Kotlin очень краткий, но при этом выразительный язык",
//            color = NoteColor.WHITE
//        ),
//        Note(
//            title = "Моя первая заметка",
//            note = "Kotlin очень краткий, но при этом выразительный язык",
//            color = NoteColor.BLUE
//        ),
//        Note(
//            title = "Моя первая заметка",
//            note = "Kotlin очень краткий, но при этом выразительный язык",
//            color = NoteColor.GREEN
//        ),
//        Note(
//            title = "Моя первая заметка",
//            note = "Kotlin очень краткий, но при этом выразительный язык",
//            color = NoteColor.PINK
//        ),
//        Note(
//            title = "Моя первая заметка",
//            note = "Kotlin очень краткий, но при этом выразительный язык",
//            color = NoteColor.RED
//        ),
//        Note(
//            title = "Моя первая заметка",
//            note = "Kotlin очень краткий, но при этом выразительный язык",
//            color = NoteColor.YELLOW
//        ),
//        Note(
//            title = "Моя первая заметка",
//            note = "Kotlin очень краткий, но при этом выразительный язык",
//            color = NoteColor.VIOLET
//        ),
//
//        Note(
//            title = "Моя первая заметка",
//            note = "Kotlin очень краткий, но при этом выразительный язык",
//            color = NoteColor.WHITE
//        ),
//        Note(
//            title = "Моя первая заметка",
//            note = "Kotlin очень краткий, но при этом выразительный язык",
//            color = NoteColor.BLUE
//        ),
//        Note(
//            title = "Моя первая заметка",
//            note = "Kotlin очень краткий, но при этом выразительный язык",
//            color = NoteColor.GREEN
//        ),
//        Note(
//            title = "Моя первая заметка",
//            note = "Kotlin очень краткий, но при этом выразительный язык",
//            color = NoteColor.PINK
//        ),
//        Note(
//            title = "Моя первая заметка",
//            note = "Kotlin очень краткий, но при этом выразительный язык",
//            color = NoteColor.RED
//        ),
//        Note(
//            title = "Моя первая заметка",
//            note = "Kotlin очень краткий, но при этом выразительный язык",
//            color = NoteColor.YELLOW
//        ),
//        Note(
//            title = "Моя первая заметка",
//            note = "Kotlin очень краткий, но при этом выразительный язык",
//            color = NoteColor.VIOLET
//        )
//    )
//
//    private val allNotes = MutableLiveData(getListToNotify())
    override fun observeNotes(): LiveData<List<Note>> {
        return provider.observeNotes()
    }

    override fun addOrReplace(newNote: Note): LiveData<Result<Note>> {
        return provider.addOrReplace(newNote)
    }

    override fun getCurrentUser() = provider.getCurrentUser()

    override fun deleteNote(note: Note): LiveData<Result<Note>> {
        return provider.deleteNote(note)
    }

    //    private fun getListToNotify(): List<Note> = notes.toMutableList().also { it.reverse() }

}