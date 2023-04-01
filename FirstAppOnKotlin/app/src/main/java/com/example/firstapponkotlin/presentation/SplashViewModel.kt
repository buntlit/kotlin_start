package com.example.firstapponkotlin.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.firstapponkotlin.data.NotesRepository
import com.example.firstapponkotlin.error.NoAuthException

class SplashViewModel(private val repository: NotesRepository) : ViewModel() {

    private val viewStateLiveData = MutableLiveData<SplashViewState>()

    fun observeViewState(): LiveData<SplashViewState> = viewStateLiveData

    fun requestUser(): SplashViewState {
        val user = repository.getCurrentUser()
        return if (user != null) {
            SplashViewState(isAuth = true)
        } else {
            SplashViewState(error = NoAuthException())
        }
    }
}