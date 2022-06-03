package com.dimas.sparkle.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.dimas.sparkle.Model.Days
import com.dimas.sparkle.R
import com.dimas.sparkle.databinding.ActivityHariSibukBinding
import com.google.firebase.database.*

class HariSibukActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHariSibukBinding
    private lateinit var ref : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHariSibukBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.hari_sibuk)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        ref = FirebaseDatabase.getInstance().getReference("days")

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                var Total = 0
                var MONDAY = 0
                var TUESDAY = 0
                var WEDNESDAY = 0
                var THURSDAY = 0
                var FRIDAY = 0
                var SATURDAY = 0
                var SUNDAY = 0
                if(p0.exists()){
                    for (h in p0.children){
                        val days = h.getValue(Days::class.java)
                        if (days != null){
                            if (days.day == "MONDAY") {
                                MONDAY = MONDAY + 1
                            }
                            if (days.day == "TUESDAY") {
                                TUESDAY = TUESDAY + 1
                            }
                            if (days.day == "WEDNESDAY") {
                                WEDNESDAY = WEDNESDAY + 1
                            }
                            if (days.day == "THURSDAY") {
                                THURSDAY = THURSDAY + 1
                            }
                            if (days.day == "FRIDAY") {
                                FRIDAY = FRIDAY + 1
                            }
                            if (days.day == "SATURDAY") {
                                SATURDAY = SATURDAY + 1
                            }
                            if (days.day == "SUNDAY") {
                                SUNDAY = SUNDAY + 1
                            }
                            Total = Total + 1
                        }
                    }
                    var mondaypersen:Double?
                    var tuesdaypersen:Double?
                    var wednesdaypersen:Double?
                    var thursdaypersen:Double?
                    var fridaypersen:Double?
                    var saturdaypersen:Double?
                    var sundaypersen:Double?

                    mondaypersen = (MONDAY.toDouble()/Total.toDouble())*100.toDouble()
                    tuesdaypersen = (TUESDAY.toDouble()/Total.toDouble())*100.toDouble()
                    wednesdaypersen = (WEDNESDAY.toDouble()/Total.toDouble())*100.toDouble()
                    thursdaypersen = (THURSDAY.toDouble()/Total.toDouble())*100.toDouble()
                    fridaypersen = (FRIDAY.toDouble()/Total.toDouble())*100.toDouble()
                    saturdaypersen = (SATURDAY.toDouble()/Total.toDouble())*100.toDouble()
                    sundaypersen = (SUNDAY.toDouble()/Total.toDouble())*100.toDouble()

                    //monday
                    if (mondaypersen<25.0){
                        binding.green1.visibility = View.VISIBLE
                        binding.yellow1.visibility = View.GONE
                        binding.red1.visibility = View.GONE
                    } else if (mondaypersen<50.0){
                        binding.green1.visibility = View.GONE
                        binding.yellow1.visibility = View.VISIBLE
                        binding.red1.visibility = View.GONE
                    } else if (50.0<=mondaypersen){
                        binding.green1.visibility = View.GONE
                        binding.yellow1.visibility = View.GONE
                        binding.red1.visibility = View.VISIBLE
                    }
                    //tuesday
                    if (tuesdaypersen<25.0){
                        binding.green2.visibility = View.VISIBLE
                        binding.yellow2.visibility = View.GONE
                        binding.red2.visibility = View.GONE
                    } else if (tuesdaypersen<50.0){
                        binding.green2.visibility = View.GONE
                        binding.yellow2.visibility = View.VISIBLE
                        binding.red2.visibility = View.GONE
                    } else if (50.0<=tuesdaypersen){
                        binding.green2.visibility = View.GONE
                        binding.yellow2.visibility = View.GONE
                        binding.red2.visibility = View.VISIBLE
                    }
                    //wednesday
                    if (wednesdaypersen<25.0){
                        binding.green3.visibility = View.VISIBLE
                        binding.yellow3.visibility = View.GONE
                        binding.red3.visibility = View.GONE
                    } else if (wednesdaypersen<50.0){
                        binding.green3.visibility = View.GONE
                        binding.yellow3.visibility = View.VISIBLE
                        binding.red3.visibility = View.GONE
                    } else if (50.0<=wednesdaypersen){
                        binding.green3.visibility = View.GONE
                        binding.yellow3.visibility = View.GONE
                        binding.red3.visibility = View.VISIBLE
                    }
                    //thursday
                    if (thursdaypersen<25.0){
                        binding.green4.visibility = View.VISIBLE
                        binding.yellow4.visibility = View.GONE
                        binding.red4.visibility = View.GONE
                    } else if (thursdaypersen<50.0){
                        binding.green4.visibility = View.GONE
                        binding.yellow4.visibility = View.VISIBLE
                        binding.red4.visibility = View.GONE
                    } else if (50.0<=thursdaypersen){
                        binding.green4.visibility = View.GONE
                        binding.yellow4.visibility = View.GONE
                        binding.red4.visibility = View.VISIBLE
                    }
                    //friday
                    if (fridaypersen<25.0){
                        binding.green5.visibility = View.VISIBLE
                        binding.yellow5.visibility = View.GONE
                        binding.red5.visibility = View.GONE
                    } else if (fridaypersen<50.0){
                        binding.green5.visibility = View.GONE
                        binding.yellow5.visibility = View.VISIBLE
                        binding.red5.visibility = View.GONE
                    } else if (50.0<=fridaypersen){
                        binding.green5.visibility = View.GONE
                        binding.yellow5.visibility = View.GONE
                        binding.red5.visibility = View.VISIBLE
                    }
                    //saturday
                    if (saturdaypersen<25.0){
                        binding.green6.visibility = View.VISIBLE
                        binding.yellow6.visibility = View.GONE
                        binding.red6.visibility = View.GONE
                    } else if (saturdaypersen<50.0){
                        binding.green6.visibility = View.GONE
                        binding.yellow6.visibility = View.VISIBLE
                        binding.red6.visibility = View.GONE
                    } else if (50.0<=saturdaypersen){
                        binding.green6.visibility = View.GONE
                        binding.yellow6.visibility = View.GONE
                        binding.red6.visibility = View.VISIBLE
                    }
                    //sunday
                    if (sundaypersen<25.0){
                        binding.green7.visibility = View.VISIBLE
                        binding.yellow7.visibility = View.GONE
                        binding.red7.visibility = View.GONE
                    } else if (sundaypersen<50.0){
                        binding.green7.visibility = View.GONE
                        binding.yellow7.visibility = View.VISIBLE
                        binding.red7.visibility = View.GONE
                    } else if (50.0<=sundaypersen){
                        binding.green7.visibility = View.GONE
                        binding.yellow7.visibility = View.GONE
                        binding.red7.visibility = View.VISIBLE
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}