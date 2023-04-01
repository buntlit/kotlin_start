package com.example.firstapponkotlin.di

import com.example.firstapponkotlin.data.NotesRepository
import com.example.firstapponkotlin.data.Repository
import com.example.firstapponkotlin.data.db.DatabaseProvider
import com.example.firstapponkotlin.data.db.FireStoreDatabaseProvider
import com.example.firstapponkotlin.model.Note
import com.example.firstapponkotlin.presentation.MainViewModel
import com.example.firstapponkotlin.presentation.NoteViewModel
import com.example.firstapponkotlin.presentation.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module


object DependencyGraph {

    private val repositoryModule by lazy {
        module {
            single { Repository(get()) } bind NotesRepository::class
            single { FireStoreDatabaseProvider() } bind DatabaseProvider::class
        }
    }

    private val viewModelModule by lazy {
        module {
            viewModel { MainViewModel(get()) }
            viewModel { SplashViewModel(get()) }
            viewModel { (note: Note?) -> NoteViewModel(get(), note) }
        }
    }

    val modules: List<Module> = listOf(repositoryModule, viewModelModule)
}