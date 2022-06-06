package com.dimas.sparkle.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dimas.sparkle.R
import com.dimas.sparkle.databinding.ActivityClosedInMainBinding

class ClosedInMainActivity : AppCompatActivity(){
    private lateinit var binding: ActivityClosedInMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClosedInMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.area_parkir)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.btnSave.setOnClickListener{
            val intent = Intent(applicationContext, ScanBarcodeOpenActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}