package com.dimas.sparkle.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.dimas.sparkle.databinding.ActivityClosedOutBinding

class ClosedOutActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityClosedOutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClosedOutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnSave.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        saveData()
    }

    private fun saveData(){
        val intent = Intent(applicationContext, ScanBarcodeExitActivity::class.java)
        startActivity(intent)
    }
}