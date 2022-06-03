package com.dimas.sparkle.UI

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dimas.sparkle.Adapter.ListRuteAdapter
import com.dimas.sparkle.Model.Nomor
import com.dimas.sparkle.R
import com.dimas.sparkle.databinding.ActivityRuteExitOutBinding
import java.util.ArrayList

class RuteExitOutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRuteExitOutBinding

    private val listr = ArrayList<Nomor>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRuteExitOutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()

        listr.addAll(listrute)
        showRecyclerList()

        supportActionBar?.title = resources.getString(R.string.rute_exit)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.barcodeButton.setOnClickListener {
            val intent = Intent(applicationContext, ClosedOutActivity::class.java)
            startActivity(intent)
        }
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
                val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
                val savedString = sharedPreferences.getString("STRING_KEY", null)
                if (dataRute[i]=="7"){
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