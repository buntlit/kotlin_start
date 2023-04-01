package com.example.firstapponkotlin.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.firstapponkotlin.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun navigateTo(fragment: Fragment){

        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragment_container_view, fragment)
            .addToBackStack("notes")
            .commit()

    }

    companion object{
        fun getStartIntent(context: Context) = Intent(context, MainActivity::class.java)
    }
}