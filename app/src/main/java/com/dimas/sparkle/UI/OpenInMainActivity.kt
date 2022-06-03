package com.dimas.sparkle.UI

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.dimas.sparkle.Model.Days
import com.dimas.sparkle.Model.TempatParkir
import com.dimas.sparkle.R
import com.dimas.sparkle.UserPreference
import com.dimas.sparkle.ViewModel.MainViewModel
import com.dimas.sparkle.ViewModelFactory
import com.dimas.sparkle.databinding.ActivityOpenInMainBinding
import com.google.firebase.database.*
import java.time.LocalDateTime

class OpenInMainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityOpenInMainBinding
    private lateinit var ref : DatabaseReference
    private lateinit var day : DatabaseReference
    private lateinit var tmpList : MutableList<TempatParkir>
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOpenInMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ref = FirebaseDatabase.getInstance().getReference("tempatParkir")
        day = FirebaseDatabase.getInstance().getReference("days")
        binding.btnSave.setOnClickListener(this)

        tmpList = mutableListOf()

        setupView()
        setupOpen()
    }

    private fun setupOpen() {
        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[MainViewModel::class.java]

        mainViewModel.getUser().observe(this, { user ->
            if (user.isLogin){
                supportActionBar?.hide()
                startActivity(Intent(this, MainActivity::class.java))
            }
        })
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    override fun onClick(v: View?) {
        saveData()
    }

    private fun saveData(){
        ref.addValueEventListener(object : ValueEventListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onDataChange(p0: DataSnapshot) {
                tmpList.clear()
                for(h in p0.children){
                    val tempatParkir = h.getValue(TempatParkir::class.java)
                    if (tempatParkir != null) {
                        if (tempatParkir.boolean == "true") {
                            tmpList.add(tempatParkir)
                            val builder = AlertDialog.Builder(this@OpenInMainActivity)
                            builder.setTitle("Nomor Parkir Anda")

                            val inflater = LayoutInflater.from(this@OpenInMainActivity)
                            val view = inflater.inflate(R.layout.update_dialog, null)
                            val etTempat = view.findViewById<TextView>(R.id.et_tempat)

                            etTempat.setText(tempatParkir.tempat)

                            builder.setView(view)

                            builder.setPositiveButton("Lanjut"){p0,p1 ->
                                val dbTmp = FirebaseDatabase.getInstance().getReference("tempatParkir")
                                val date = LocalDateTime.now()

                                val tmpId = day.push().key

                                val days = date.dayOfWeek.toString()

                                val tmp = Days(tmpId,days)

                                if (tmpId != null){
                                    day.child(tmpId).setValue(tmp).addOnCompleteListener {
                                        Toast.makeText(applicationContext, "Selamat Datang", Toast.LENGTH_SHORT).show()
                                    }
                                }
                                val boolean = "false"
                                val tempat = etTempat.text.toString().trim()
                                val tempatParkir = TempatParkir(tempatParkir.id, boolean, tempat)

                                dbTmp.child(tempatParkir.id!!).setValue(tempatParkir)

                                val insertedText = tempatParkir.tempat
                                val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
                                val editor = sharedPreferences.edit()

                                editor.apply {
                                    putString("STRING_KEY", insertedText)
                                }.apply()
                                mainViewModel.login()
                                startActivity(intent)
                                finish()
                            }
                            val alert = builder.create()
                            alert.show()
                            break
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}