package com.dicoding.picodiploma.myrecyclerview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.picodiploma.myrecyclerview.databinding.ActivityWelcomeBinding

private lateinit var binding: ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.welcome)

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