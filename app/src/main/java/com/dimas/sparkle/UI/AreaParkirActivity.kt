package com.dimas.sparkle.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.dimas.sparkle.Model.TempatParkir
import com.dimas.sparkle.R
import com.dimas.sparkle.databinding.ActivityAreaParkirBinding
import com.google.firebase.database.*

class AreaParkirActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAreaParkirBinding
    private lateinit var ref : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAreaParkirBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.area_parkir)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        TempatSibuk()
    }

    private fun TempatSibuk() {
        ref = FirebaseDatabase.getInstance().getReference("tempatParkir")
        showLoading(true)
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    for (h in p0.children){
                        val rute = h.getValue(TempatParkir::class.java)
                        if (rute != null){
                            if (rute.tempat == "1")
                                if (rute.boolean == "true")
                                    binding.mobilOne.visibility = View.GONE
                            if (rute.tempat == "2")
                                if (rute.boolean == "true")
                                    binding.mobilTwo.visibility = View.GONE
                            if (rute.tempat == "3")
                                if (rute.boolean == "true")
                                    binding.mobilThree.visibility = View.GONE
                            if (rute.tempat == "4")
                                if (rute.boolean == "true")
                                    binding.mobilFour.visibility = View.GONE
                            if (rute.tempat == "5")
                                if (rute.boolean == "true")
                                    binding.mobilFive.visibility = View.GONE
                            if (rute.tempat == "6")
                                if (rute.boolean == "true")
                                    binding.mobilSix.visibility = View.GONE
                        }
                    }
                    showLoading(false)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun showLoading(state : Boolean){
        binding.progressBar.visibility = if(state) View.VISIBLE else View.GONE
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}