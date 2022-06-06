package com.dimas.sparkle.UI

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.dimas.sparkle.Model.UserModel
import com.dimas.sparkle.UserPreference
import com.dimas.sparkle.ViewModel.ScanBarcodeExitViewModel
import com.dimas.sparkle.ViewModelFactory
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView
import java.time.LocalDateTime
import java.time.LocalTime

class ScanBarcodeExitActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    var scannerView: ZXingScannerView? = null
    private lateinit var scanBarcodeExitViewModel: ScanBarcodeExitViewModel
    private lateinit var user: UserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scannerView = ZXingScannerView(this)
        setContentView(scannerView)

        setupView()
        setupViewModel()
        setPermission()
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
        supportActionBar?.hide()
    }

    private fun setupViewModel() {
        scanBarcodeExitViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[ScanBarcodeExitViewModel::class.java]

        scanBarcodeExitViewModel.getUser().observe(this, { user ->
            this.user = user
        })
    }

    override fun onResume() {
        super.onResume()

        scannerView?.setResultHandler(this)
        scannerView?.startCamera()
    }

    override fun onStop() {
        super.onStop()
        scannerView?.stopCamera()
        onBackPressed()
    }

    private fun setPermission() {
        val permission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest()
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(
            this, arrayOf(android.Manifest.permission.CAMERA),
            1
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            1 -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(
                        applicationContext,
                        "You need camera permission",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun handleResult(p0: Result?) {
        if (p0.toString() == "Just Exit Leh okeeeeeeeee"){
            AlertDialog.Builder(this@ScanBarcodeExitActivity).apply {
                val sharedPreferences = getSharedPreferences("jamPrefs", Context.MODE_PRIVATE)
                val totalSec = sharedPreferences.getString("JAM_KEY", null)

                var todayNew = LocalTime.now()

                var hoursNew = todayNew.hour
                var minuteNew = todayNew.minute
                var secNew = todayNew.second

                val secHoursNew = hoursNew * 3600
                val secMinuteNew = minuteNew * 60

                val totalSecNew = secHoursNew + secMinuteNew + secNew

                var biayaSec = (totalSecNew - totalSec!!.toInt())/3600

                val date = LocalDateTime.now()
                val days = date.dayOfWeek.toString()

                if(days=="SATURDAY"){
                    if (biayaSec<1){
                        biayaSec = 3000
                    } else {
                        biayaSec = biayaSec * 3000
                    }
                } else if(days=="SUNDAY"){
                    if (biayaSec<1){
                        biayaSec = 3000
                    } else {
                        biayaSec = biayaSec * 3000
                    }
                } else{
                    if (biayaSec<1){
                        biayaSec = 2000
                    } else {
                        biayaSec = biayaSec * 2000
                    }
                }

                setTitle("Tagihan Parkir Anda")
                setMessage("Rp. ${biayaSec}")
                setPositiveButton("Bayar") { _, _ ->
                    scanBarcodeExitViewModel.logout()
                    val intent = Intent(applicationContext, OpenOutActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                }
                create()
                show()
            }
        } else{
            AlertDialog.Builder(this@ScanBarcodeExitActivity).apply {
                setTitle("Barcode Anda Salah")
                setMessage("Silakan Scan lagi")
                setPositiveButton("Scan") { _, _ ->
                    val intent = Intent(context, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                }
                create()
                show()
            }
        }
    }
}