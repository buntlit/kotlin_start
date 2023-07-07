package com.example.firstapponkotlin.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firstapponkotlin.data.NotesRepository
import com.example.firstapponkotlin.error.NoAuthException
import kotlinx.coroutines.launch

class SplashViewModel(private val repository: NotesRepository) : ViewModel() {

    private val viewStateLiveData = MutableLiveData<SplashViewState>()

    init {
        viewModelScope.launch {
            val user = repository.getCurrentUser()
            viewStateLiveData.value = if (user != null) {
                SplashViewState(isAuth = true)
            } else {
                SplashViewState(error = NoAuthException())
            }
        }
    }

    fun observeViewState(): LiveData<SplashViewState> = viewStateLiveData

}