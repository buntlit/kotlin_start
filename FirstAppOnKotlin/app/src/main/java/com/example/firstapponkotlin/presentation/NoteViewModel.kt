package com.example.firstapponkotlin.presentation

import android.annotation.SuppressLint
import androidx.lifecycle.*
import com.example.firstapponkotlin.data.NotesRepository
import com.example.firstapponkotlin.data.SingleLiveEvent
import com.example.firstapponkotlin.model.Note

class NoteViewModel(private val notesRepository: NotesRepository, var note: Note?) : ViewModel() {

    private val showErrorLiveData = SingleLiveEvent<String>()
    private val lifecycleOwner: LifecycleOwner = object : LifecycleOwner {
        override val lifecycle: Lifecycle
            get() = viewModelLifecycle
    }

    @SuppressLint("StaticFieldLeak")
    private val viewModelLifecycle = LifecycleRegistry(lifecycleOwner).also {
        it.currentState = Lifecycle.State.RESUMED
    }

    fun updateNote(text: String) {
        note = (note ?: generateNote()).copy(note = text)
    }

    fun updateTitle(text: String) {
        note = (note ?: generateNote()).copy(title = text)
    }

    fun saveNote() {
        note?.let { note ->
            notesRepository.addOrReplace(note).observe(lifecycleOwner) {
                it.onFailure {
                    showErrorLiveData.value = Result.toString()
                }
            }
        }
    }

    fun deleteNote() {
        note?.let { note ->
            notesRepository.deleteNote(note)
        }
    }

    fun showError(): SingleLiveEvent<String> = showErrorLiveData

    override fun onCleared() {
        super.onCleared()
        viewModelLifecycle.currentState = Lifecycle.State.DESTROYED
    }

    private fun generateNote(): Note {
        return Note()
    }
}