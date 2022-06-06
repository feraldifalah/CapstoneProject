package com.dimas.sparkle.UI

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dimas.sparkle.Adapter.ListRuteAdapter
import com.dimas.sparkle.Model.Nomor
import com.dimas.sparkle.R
import com.dimas.sparkle.databinding.ActivityRuteExitBinding
import java.util.ArrayList

class RuteExitActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRuteExitBinding
    private val listr = ArrayList<Nomor>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRuteExitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listr.addAll(listrute)
        showRecyclerList()

        supportActionBar?.title = resources.getString(R.string.rute_exit)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.barcodeButton.setOnClickListener {
            val intent = Intent(applicationContext, ClosedOutActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private val listrute: ArrayList<Nomor>
        get() {
            val dataName = resources.getStringArray(R.array.data_name)
            val dataRute = resources.getStringArray(R.array.data_rutes)
            val dataPhoto = resources.obtainTypedArray(R.array.data_exit)
            val listrute = ArrayList<Nomor>()
            for (i in dataRute.indices) {
                val sharedPreferences = getSharedPreferences("tempatPrefs", Context.MODE_PRIVATE)
                val savedString = sharedPreferences.getString("TEMPAT_KEY", null)
                if (dataRute[i]==savedString){
                    val rute = Nomor(dataName[i],dataRute[i], dataPhoto.getResourceId(i, -1))
                    listrute.add(rute)
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
                showSelectedRute(data)
            }
        })
    }

    private fun showSelectedRute(rute: Nomor) {
        Toast.makeText(this, "Kamu dapat" + rute.name, Toast.LENGTH_SHORT).show()
    }
}