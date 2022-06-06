package com.dimas.sparkle.UI

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.dimas.sparkle.databinding.ActivityWelcomeBinding
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.barcodeButton.setOnClickListener {
            val intent = Intent(applicationContext, ClosedInMainActivity::class.java)
            startActivity(intent)
        }

        setupAction()
    }

    private fun setupAction() {
        binding.areaParkirButton.setOnClickListener {
            startActivity(Intent(this, AreaParkirActivity::class.java))
        }

        binding.hariSibukButton.setOnClickListener {
            startActivity(Intent(this, HariSibukActivity::class.java))
        }
    }
}