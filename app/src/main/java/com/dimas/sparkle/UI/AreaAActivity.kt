package com.dimas.sparkle.UI

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.dimas.sparkle.Model.TempatParkir
import com.dimas.sparkle.R
import com.dimas.sparkle.databinding.ActivityAreaAactivityBinding
import com.google.firebase.database.*

class AreaAActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAreaAactivityBinding
    private lateinit var ref : DatabaseReference

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAreaAactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.area_a)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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
                    binding.statusA.text = "Area A\n${spacesA.toString()} / ${spacesATotal.toString()}"
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        binding.statusA.setOnClickListener {
            val intent = Intent(applicationContext, ClosedInMainActivity::class.java)
            startActivity(intent)
        }
        setupAction()
    }

    private fun setupAction() {
        binding.barcodeButton.setOnClickListener {
            val intent = Intent(applicationContext, ClosedInMainActivity::class.java)
            startActivity(intent)
        }

        binding.areaParkirButton.setOnClickListener {
            startActivity(Intent(this, AreaParkirActivity::class.java))
        }

        binding.hariSibukButton.setOnClickListener {
            startActivity(Intent(this, HariSibukActivity::class.java))
        }
    }
}