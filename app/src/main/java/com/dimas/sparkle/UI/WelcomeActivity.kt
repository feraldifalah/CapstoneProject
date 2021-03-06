package com.dimas.sparkle.UI

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.dimas.sparkle.Model.TempatParkir
import com.dimas.sparkle.R
import com.dimas.sparkle.databinding.ActivityWelcomeBinding
import com.google.firebase.database.*

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding
    private lateinit var ref : DatabaseReference
    private lateinit var ref1 : DatabaseReference

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.Welcome)

        ref = FirebaseDatabase.getInstance().getReference("tempatParkir")
        val addValueEventListener = ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    var spacesA = 0
                    var spacesATotal = 0
                    for (h in p0.children) {
                        val tempatParkir = h.getValue(TempatParkir::class.java)
                        if (tempatParkir != null) {
                            if (tempatParkir.boolean == "true") {
                                spacesA = spacesA + 1
                            }
                            spacesATotal = spacesATotal +1
                        }
                    }
                    binding.statusA.text = "Area A : ${spacesA.toString()} / ${spacesATotal.toString()} Kosong"
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        ref1 = FirebaseDatabase.getInstance().getReference("ParkingSpaces")
        val addValueEventListener1 = ref1.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    for (h in p0.children) {
                        val spaces = h.value
                        binding.statusB.text = "Area B : ${spaces.toString()} / 69 Kosong"
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })

        binding.BottonA.setOnClickListener {
            val intent = Intent(applicationContext, AreaAActivity::class.java)
            startActivity(intent)
        }

        binding.BottonB.setOnClickListener {
            val intent = Intent(applicationContext, AreaBActivity::class.java)
            startActivity(intent)
        }
    }
}