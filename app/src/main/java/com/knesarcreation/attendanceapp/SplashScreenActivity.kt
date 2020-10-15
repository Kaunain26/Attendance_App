package com.knesarcreation.attendanceapp;

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashScreenActivity : AppCompatActivity() {

    /* access modifiers changed from: protected */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen_activity)
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainScreenActivity::class.java))
            finish()
        }, 1000)
    }
}

