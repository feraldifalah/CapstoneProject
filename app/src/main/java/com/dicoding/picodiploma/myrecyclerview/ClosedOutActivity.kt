package com.dicoding.picodiploma.myrecyclerview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class ClosedOutActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var btnSave : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_closed_out)
        btnSave = findViewById(R.id.btn_save)
        btnSave.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        saveData()
    }

    private fun saveData(){
        val nomor1 = intent.getStringExtra("nomor")
        val bundle = Bundle()
        val intent = Intent(applicationContext, ScanBarcodeExitActivity::class.java)
        bundle.putString("nomor", nomor1)
        intent.putExtras(bundle)
        startActivity(intent)
    }
}