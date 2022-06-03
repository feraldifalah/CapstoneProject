package com.dimas.sparkle.UI

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dimas.sparkle.*
import com.dimas.sparkle.Adapter.ListRutesAdapter
import com.dimas.sparkle.Model.Nomor
import com.dimas.sparkle.ViewModel.MainViewModel
import com.dimas.sparkle.databinding.ActivityMainBinding
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    var backPressedTime: Long = 0
    private val list = ArrayList<Nomor>()
    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.nomor_parkir)

        list.addAll(listRutes)
        showRecyclerList()

        val result = intent.getStringExtra(RESULT)

        if (result != null) {
            if (result.contains("https://") || result.contains("http://")) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(result))
                startActivity(intent)
            } else {
                binding.result.text = result.toString()
            }
        }

        binding.parkirButton.setOnClickListener {
            val intent = Intent(this, RuteParkirActivity::class.java)
            startActivity(intent)
        }
        binding.exitButton.setOnClickListener {
            val intent = Intent(this, RuteExitActivity::class.java)
            startActivity(intent)
        }
        binding.exitOutButton.setOnClickListener {
            val intent = Intent(this, RuteExitOutActivity::class.java)
            startActivity(intent)
        }
        setupView()
        setupViewModel()
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

    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[MainViewModel::class.java]

        mainViewModel.getUser().observe(this, { user ->
            if (user.isLogin){

            } else {
                supportActionBar?.hide()
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
        })
    }

    private val listRutes: ArrayList<Nomor>
        get() {
            val dataName = resources.getStringArray(R.array.data_name)
            val dataRute = resources.getStringArray(R.array.data_rutes)
            val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
            val listRute = ArrayList<Nomor>()
            for (i in dataRute.indices) {
                val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
                val savedString = sharedPreferences.getString("STRING_KEY", null)
                if (dataRute[i]==savedString){
                    val rute = Nomor(dataName[i],dataRute[i], dataPhoto.getResourceId(i, -1))
                    listRute.add(rute)
                    break
                }
            }
            return listRute
        }

    private fun showRecyclerList() {
        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.rvRutes.layoutManager = GridLayoutManager(this, 2)
        } else {
            binding.rvRutes.layoutManager = LinearLayoutManager(this)
        }
        val listRutesAdapter = ListRutesAdapter(list)
        binding.rvRutes.adapter = listRutesAdapter
        listRutesAdapter.setOnItemClickCallback(object : ListRutesAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Nomor) {
                showSelectedRute(data)
            }
        })
    }

    override fun onBackPressed() {
        if (backPressedTime + 3000 > System.currentTimeMillis()) {
            super.onBackPressed()
            finish()
            System.exit(0)
        } else {
            Toast.makeText(this, "Press back again to leave the app.", Toast.LENGTH_LONG).show()
        }
        backPressedTime = System.currentTimeMillis()
    }

    private fun showSelectedRute(rute: Nomor) {
        Toast.makeText(this, "Kamu dapat" + rute.name, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val RESULT = "RESULT"
    }
}