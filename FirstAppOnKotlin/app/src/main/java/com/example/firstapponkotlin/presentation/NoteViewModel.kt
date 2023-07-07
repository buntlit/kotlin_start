package com.example.firstapponkotlin.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firstapponkotlin.data.NotesRepository
import com.example.firstapponkotlin.data.SingleLiveEvent
import com.example.firstapponkotlin.model.Note
import kotlinx.coroutines.launch

class NoteViewModel(private val notesRepository: NotesRepository, var note: Note?) : ViewModel() {

//    val exceptionHandler: CoroutineExceptionHandler = object : CoroutineExceptionHandler {
//        override val key: CoroutineContext.Key<*> = CoroutineExceptionHandler.Key
//
//        override fun handleException(context: CoroutineContext, exception: Throwable) {
//            TODO("Not yet implemented")
//        }
//
//    }

    private val showErrorLiveData = SingleLiveEvent<String>()
//    private val lifecycleOwner: LifecycleOwner = object : LifecycleOwner {
//        override val lifecycle: Lifecycle
//            get() = viewModelLifecycle
//    }
//
//    @SuppressLint("StaticFieldLeak")
//    private val viewModelLifecycle = LifecycleRegistry(lifecycleOwner).also {
//        it.currentState = Lifecycle.State.RESUMED
//    }

    fun updateNote(text: String) {
        note = (note ?: generateNote()).copy(note = text)
    }

    fun updateTitle(text: String) {
        note = (note ?: generateNote()).copy(title = text)
    }

    fun saveNote() {

        viewModelScope.launch {
            val noteValue = note ?: return@launch

            try {
                notesRepository.addOrReplace(noteValue)
            } catch (th: Throwable){
                showErrorLiveData.value = Result.toString()
            }

        }
//        note?.let { note ->
//            notesRepository.addOrReplace(note).observe(lifecycleOwner) {
//                it.onFailure {
//                    showErrorLiveData.value = Result.toString()
//                }
//            }
//        }
    }

    fun deleteNote() {
        viewModelScope.launch {
            val noteValue = note ?: return@launch

            try {
                notesRepository.deleteNote(noteValue)
            } catch (th: Throwable){
                showErrorLiveData.value = Result.toString()
            }

        }
//        note?.let { note ->
//            notesRepository.deleteNote(note).observe(lifecycleOwner) {
//                it.onFailure {
//                    showErrorLiveData.value = Result.toString()
//                }
//            }
//        }
    }

    fun showError(): SingleLiveEvent<String> = showErrorLiveData
//
//    override fun onCleared() {
//        super.onCleared()
//        viewModelLifecycle.currentState = Lifecycle.State.DESTROYED
//    }

    private fun generateNote(): Note {
        return Note()
    }
}