package com.dicoding.picodiploma.myrecyclerview

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.*

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class OpenInMainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var btnSave : Button
    private lateinit var ref : DatabaseReference
    private lateinit var tmpList : MutableList<TempatParkir>

    private lateinit var mainViewModel: MainViewModel

    private lateinit var scanBarcodeOpenViewModel: ScanBarcodeOpenViewModel
    private lateinit var user: UserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_in_main)

        ref = FirebaseDatabase.getInstance().getReference("tempatParkir")

        btnSave = findViewById(R.id.btn_save)
        btnSave.setOnClickListener(this)

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
                //finish()
//                System.exit(0)
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

    private fun setupViewModel() {
        scanBarcodeOpenViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[ScanBarcodeOpenViewModel::class.java]

        scanBarcodeOpenViewModel.getUser().observe(this, { user ->
            this.user = user
        })
    }

    override fun onClick(v: View?) {
        saveData()
    }

    private fun saveData(){
        ref.addValueEventListener(object : ValueEventListener {
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

                                val boolean = "false"
                                val tempat = etTempat.text.toString().trim()

                                val tempatParkir = TempatParkir(tempatParkir.id, boolean, tempat)

                                dbTmp.child(tempatParkir.id!!).setValue(tempatParkir)

                                val bundle = Bundle()
                                val intent = Intent(applicationContext, MainActivity::class.java)

                                bundle.putString("nomor", tempatParkir.tempat)

                                val insertedText = tempatParkir.tempat

                                val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
                                val editor = sharedPreferences.edit()
                                editor.apply {
                                    putString("STRING_KEY", insertedText)
                                }.apply()
                                mainViewModel.login()
                                intent.putExtras(bundle)
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