package com.example.firstapponkotlin

import android.support.multidex.MultiDexApplication
import com.example.firstapponkotlin.di.DependencyGraph
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NotesApp : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@NotesApp)
            modules(DependencyGraph.modules)
        }
    }
}