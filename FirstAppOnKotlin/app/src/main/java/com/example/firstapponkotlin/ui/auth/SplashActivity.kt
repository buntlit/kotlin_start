package com.example.firstapponkotlin.ui.auth

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firstapponkotlin.R
import com.example.firstapponkotlin.error.NoAuthException
import com.example.firstapponkotlin.presentation.SplashViewModel
import com.example.firstapponkotlin.ui.main.MainActivity
import com.firebase.ui.auth.AuthUI
import org.koin.androidx.viewmodel.ext.android.viewModel


private const val RC_SIGN_IN = 458

class SplashActivity : AppCompatActivity() {

    private val viewModel by viewModel<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_splash)

        viewModel.observeViewState().observe(this) {
            if (it.error != null) {
                renderError(it.error)
            } else {
                renderData(it.isAuth)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.observeViewState().observe(this) {
            if (it.error != null) {
                renderError(it.error)
            } else {
                renderData(it.isAuth)
            }
        }
    }

    private fun startMainActivity() {
        startActivity(MainActivity.getStartIntent(this))
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN && resultCode != Activity.RESULT_OK) {
            finish()
        }
    }

    private fun renderData(data: Boolean?) {
        data?.takeIf { it }?.let {
            startMainActivity()
        }
    }

    private fun renderError(error: Throwable) {
        when (error) {
            is NoAuthException -> startLoginActivity()
            else -> error.message?.let { Toast.makeText(this, it, Toast.LENGTH_LONG).show() }
        }
    }

    private fun startLoginActivity() {
        val providers = listOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setLogo(R.drawable.ic_launcher_foreground)
                .setTheme(R.style.Theme_FirstAppOnKotlin)
                .setAvailableProviders(providers)
                .build(), RC_SIGN_IN
        )
    }
}