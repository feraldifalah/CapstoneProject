package com.dimas.sparkle.UI

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.dimas.sparkle.Model.TempatParkir
import com.dimas.sparkle.R
import com.dimas.sparkle.databinding.ActivityAreaAactivityBinding
import com.dimas.sparkle.databinding.ActivityAreaBactivityBinding
import com.google.firebase.database.*

class AreaBActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAreaBactivityBinding
    private lateinit var ref : DatabaseReference

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAreaBactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.area_b)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        ref = FirebaseDatabase.getInstance().getReference("ParkingSpaces")
        val addValueEventListener1 = ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    for (h in p0.children) {
                        val spaces = h.value
                        binding.statusB.text = "Area B\n${spaces.toString()} / 69"
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })

        binding.statusB.setOnClickListener {
            val intent = Intent(applicationContext, ClosedInMainActivity::class.java)
            startActivity(intent)
        }
        setupAction()
    }

    private fun setupAction() {

        binding.hariSibukButton.setOnClickListener {
            startActivity(Intent(this, HariSibukActivity::class.java))
        }
    }
}