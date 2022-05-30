package com.dicoding.picodiploma.myrecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ListView
import com.dicoding.picodiploma.myrecyclerview.databinding.ActivityAreaParkirBinding
import com.google.firebase.database.*

class AreaParkirActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAreaParkirBinding
    //private lateinit var listMhs : ListView
    private lateinit var ref :DatabaseReference
    //private lateinit var mhsList: MutableList<TempatParkir>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAreaParkirBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.area_parkir)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //listMhs = findViewById(R.id.lv_mhs)
        //mhsList = mutableListOf()

        ref = FirebaseDatabase.getInstance().getReference("tempatParkir")
        showLoading(true)
        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    for (h in p0.children){
                        val mahasiswa = h.getValue(TempatParkir::class.java)
                        if (mahasiswa != null){
                            if (mahasiswa.tempat == "1")
                                if (mahasiswa.boolean == "true")
                                    binding.mobilOne.visibility = View.GONE
                            if (mahasiswa.tempat == "2")
                                if (mahasiswa.boolean == "true")
                                    binding.mobilTwo.visibility = View.GONE
                            if (mahasiswa.tempat == "3")
                                if (mahasiswa.boolean == "true")
                                    binding.mobilThree.visibility = View.GONE
                            if (mahasiswa.tempat == "4")
                                if (mahasiswa.boolean == "true")
                                    binding.mobilFour.visibility = View.GONE
                            if (mahasiswa.tempat == "5")
                                if (mahasiswa.boolean == "true")
                                    binding.mobilFive.visibility = View.GONE
                            if (mahasiswa.tempat == "6")
                                if (mahasiswa.boolean == "true")
                                    binding.mobilSix.visibility = View.GONE
                            //mhsList.add(mahasiswa)
                        }
                    }
                    showLoading(false)
                    //val adapter = TempatParkirAdapter(applicationContext, R.layout.item_mhs, mhsList)
                    //listMhs.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
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