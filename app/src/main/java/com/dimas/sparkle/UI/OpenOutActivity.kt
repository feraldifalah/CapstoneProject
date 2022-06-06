package com.dimas.sparkle.UI

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.dimas.sparkle.Model.TempatParkir
import com.dimas.sparkle.R
import com.dimas.sparkle.databinding.ActivityOpenOutBinding
import com.google.firebase.database.*

class OpenOutActivity : AppCompatActivity(){

    private lateinit var binding: ActivityOpenOutBinding
    private lateinit var ref : DatabaseReference
    private lateinit var tmpList : MutableList<TempatParkir>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOpenOutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ref = FirebaseDatabase.getInstance().getReference("tempatParkir")

        tmpList = mutableListOf()

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                tmpList.clear()
                for(h in p0.children){
                    val tempatParkir = h.getValue(TempatParkir::class.java)
                    if (tempatParkir != null) {
                        if (tempatParkir.boolean == "false") {
                            tmpList.add(tempatParkir)
                            break
                        }
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        binding.btnSave.setOnClickListener {
            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {
                    tmpList.clear()
                    for(h in p0.children){
                        val tempatParkir = h.getValue(TempatParkir::class.java)
                        if (tempatParkir != null) {
                            val sharedPreferences = getSharedPreferences("tempatPrefs", Context.MODE_PRIVATE)
                            val savedString = sharedPreferences.getString("TEMPAT_KEY", null)
                            if (tempatParkir.tempat== savedString){
                                tmpList.add(tempatParkir)
                                val builder = AlertDialog.Builder(this@OpenOutActivity)
                                builder.setTitle("Welcome")

                                val inflater = LayoutInflater.from(this@OpenOutActivity)
                                val view = inflater.inflate(R.layout.update_dialog, null)
                                val etTempat = view.findViewById<TextView>(R.id.et_tempat)

                                etTempat.setText(tempatParkir.tempat)

                                builder.setPositiveButton("Lanjut"){p0,p1 ->
                                    val dbTmp = FirebaseDatabase.getInstance().getReference("tempatParkir")

                                    val boolean = "true"
                                    val tempat = etTempat.text.toString().trim()

                                    val tempatParkir = TempatParkir(tempatParkir.id, boolean, tempat)

                                    dbTmp.child(tempatParkir.id!!).setValue(tempatParkir)
                                    val intent = Intent(applicationContext, WelcomeActivity::class.java)
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

        binding.btnExit.setOnClickListener {
            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {
                    tmpList.clear()
                    for(h in p0.children){
                        val tempatParkir = h.getValue(TempatParkir::class.java)
                        if (tempatParkir != null) {
                            val sharedPreferences = getSharedPreferences("tempatPrefs", Context.MODE_PRIVATE)
                            val savedString = sharedPreferences.getString("TEMPAT_KEY", null)
                            if (tempatParkir.tempat == savedString){
                                tmpList.add(tempatParkir)
                                val builder = AlertDialog.Builder(this@OpenOutActivity)
                                builder.setTitle("Terima Kasih sudah mau datang")

                                val inflater = LayoutInflater.from(this@OpenOutActivity)
                                val view = inflater.inflate(R.layout.update_dialog, null)
                                val etTempat = view.findViewById<TextView>(R.id.et_tempat)

                                etTempat.setText(tempatParkir.tempat)

                                builder.setPositiveButton("Exit"){p0,p1 ->
                                    val dbTmp = FirebaseDatabase.getInstance().getReference("tempatParkir")

                                    val boolean = "true"
                                    val tempat = etTempat.text.toString().trim()

                                    val tempatParkir = TempatParkir(tempatParkir.id, boolean, tempat)

                                    dbTmp.child(tempatParkir.id!!).setValue(tempatParkir)
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
}