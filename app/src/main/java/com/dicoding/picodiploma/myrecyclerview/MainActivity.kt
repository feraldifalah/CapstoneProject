package com.dicoding.picodiploma.myrecyclerview

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.myrecyclerview.adapter.ListRutesAdapter
import com.dicoding.picodiploma.myrecyclerview.databinding.ActivityMainBinding
import com.dicoding.picodiploma.myrecyclerview.model.Nomor
import java.util.*

private lateinit var binding: ActivityMainBinding
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {
    var backPressedTime: Long = 0
    private val list = ArrayList<Nomor>()
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.nomor_parkir)

        list.addAll(listHeroes)
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
        val nomor1 = intent.getStringExtra("nomor")

        binding.parkirButton.setOnClickListener {
            val bundle = Bundle()
            val intent = Intent(applicationContext, RuteParkirActivity::class.java)
            bundle.putString("nomor", nomor1)
            intent.putExtras(bundle)
            startActivity(intent)
        }
        binding.exitButton.setOnClickListener {
            val bundle = Bundle()
            val intent = Intent(applicationContext, RuteExitActivity::class.java)
            bundle.putString("nomor", nomor1)
            intent.putExtras(bundle)
            startActivity(intent)
        }
        binding.exitOutButton.setOnClickListener {
            val bundle = Bundle()
            val intent = Intent(applicationContext, RuteExitOutActivity::class.java)
            bundle.putString("nomor", nomor1)
            intent.putExtras(bundle)
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

    private val listHeroes: ArrayList<Nomor>
        get() {
            val dataName = resources.getStringArray(R.array.data_name)
            val dataDescription = resources.getStringArray(R.array.data_description)
            val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
            val listHero = ArrayList<Nomor>()
            for (i in dataDescription.indices) {
                val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
                val savedString = sharedPreferences.getString("STRING_KEY", null)
                if (dataDescription[i]==savedString){
                    val hero = Nomor(dataName[i],dataDescription[i], dataPhoto.getResourceId(i, -1))
                    listHero.add(hero)
                    break
                }
            }
            return listHero
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
                showSelectedHero(data)
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

    private fun showSelectedHero(hero: Nomor) {
        Toast.makeText(this, "Kamu memilih " + hero.name, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val RESULT = "RESULT"
    }
}