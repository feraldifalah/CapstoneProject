package com.dicoding.picodiploma.myrecyclerview

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.myrecyclerview.R
import com.dicoding.picodiploma.myrecyclerview.adapter.ListRuteAdapter
import com.dicoding.picodiploma.myrecyclerview.databinding.ActivityRuteParkirBinding
import com.dicoding.picodiploma.myrecyclerview.model.Nomor
import java.util.ArrayList

class RuteParkirActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRuteParkirBinding

    private val listr = ArrayList<Nomor>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRuteParkirBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listr.addAll(listrute)
        showRecyclerList()

        supportActionBar?.title = resources.getString(R.string.rute_parkir)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val nomor1 = intent.getStringExtra("nomor")
        binding.exitButton.setOnClickListener {
            val bundle = Bundle()
            val intent = Intent(applicationContext, RuteExitActivity::class.java)
            bundle.putString("nomor", nomor1)
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }

    private fun setupAction() {
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private val listrute: ArrayList<Nomor>
        get() {
            val dataName = resources.getStringArray(R.array.data_name)
            val dataDescription = resources.getStringArray(R.array.data_description)
            val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
            val listrute = ArrayList<Nomor>()
            for (i in dataDescription.indices) {
                val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
                val savedString = sharedPreferences.getString("STRING_KEY", null)
                if (dataDescription[i]==savedString){
                    val hero = Nomor(dataName[i],dataDescription[i], dataPhoto.getResourceId(i, -1))
                    listrute.add(hero)
                    break
                }
            }
            return listrute
        }

    private fun showRecyclerList() {
        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.rvRute.layoutManager = GridLayoutManager(this, 2)
        } else {
            binding.rvRute.layoutManager = LinearLayoutManager(this)
        }
        val listRuteAdapter = ListRuteAdapter(listr)
        binding.rvRute.adapter = listRuteAdapter
        listRuteAdapter.setOnItemClickCallback(object : ListRuteAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Nomor) {
                showSelectedHero(data)
            }
        })
    }

    private fun showSelectedHero(hero: Nomor) {
        Toast.makeText(this, "Kamu memilih " + hero.name, Toast.LENGTH_SHORT).show()
    }
}