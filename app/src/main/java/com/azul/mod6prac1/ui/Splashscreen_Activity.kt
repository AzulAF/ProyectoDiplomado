package com.azul.mod6prac1.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.azul.mod6prac1.R
import com.bumptech.glide.Glide
import android.os.Handler              // Para manejar retrasos
import android.os.Looper               // Para acceder al Looper de la interfaz de usuario
import com.azul.mod6prac1.databinding.ActivityInitialBinding
import com.azul.mod6prac1.databinding.ActivitySplashscreenBinding

private lateinit var binding: ActivitySplashscreenBinding

@SuppressLint("CustomSplashScreen")
class Splashscreen_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, InitialActivity::class.java))
            finish()
        }, 4500) // 4 segundos


    }
}