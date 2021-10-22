package com.example.rapidbus

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_in_route.*
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*


class InRoute : AppCompatActivity() {
    var stopNumber = -1
    var display = listOf<String>()
    var transition = listOf<String>()
    var morning = listOf<String>()
    var afternoon = listOf<String>()
    var skip = listOf<String>()
    var weekDay = listOf<String>()
    var altIndex = listOf<String>()
    var morning2 = listOf<String>()
    var afternoon2 = listOf<String>()
    var skip2 = listOf<String>()
    var altindex2 = listOf<String>()
    val db = FirebaseDatabase.getInstance()
    var log = "RapidBus Log,"
    val rawStartTime = LocalDateTime.now().toString().dropLast(4).replace(oldChar = 'T',newChar = ' ')
    val startTime = rawStartTime.replace(oldChar = ':',newChar = '-')
    var pref: SharedPreferences? = null
    val calendar = Calendar.getInstance()

    val auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_in_route)
        pref = getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val routePath = pref!!.getString(intent.getStringExtra("Route"),null).toString()
        val districtPath = pref!!.getString("District",null).toString()

        log += "$routePath,$startTime\n"



        /*myRef.child("Driver Data").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val pull = dataSnapshot.getValue(String::class.java)!!
                val midPull = pull.split('|')
                display = midPull[0].split(',')
                transition = midPull[1].split(',' )
                morning = midPull[2].split(',')
                afternoon = midPull[3].split(',')
                skip = midPull[4].split(',')
                altIndex = midPull[5].split(',')
                weekDay = midPull[6].split(',')
                morning2 = midPull[7].split(',')
                afternoon2 = midPull[8].split(',')
                skip2 = midPull[9].split(',')
                altindex2 = midPull[10].split(',')
                conditions1()
            }

            override fun onCancelled(error: DatabaseError) {
                textView1.text = getString(R.string.FirebaseError)
                goToMainActivity()
            }*/


    }

    fun goToMainActivity() {
        pref = getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val routePath = pref!!.getString(intent.getStringExtra("Route"),null).toString()
        val districtPath = pref!!.getString("District",null).toString()
        //val myRef = database.getReference(districtPath).child(routePath)
        //myRef.child("Log Data").child(startTime).setValue(log)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun buttonMissedStop(view: View) {
        missedStop()
    }

    fun missedStop() {
        log("Missed")
        conditions1()
    }

    fun buttonSkippedStop(view: View) {
        skippedStop()
    }

    fun skippedStop() {
        log("Skipped")
        conditions1()
    }

    fun buttonNextStop(view: View) {
        nextStop()
    }

    fun nextStop() {
        log("Completed")
        conditions1()
    }

    fun emptyStop() {
        log("EMPTY")
        conditions1()
    }

    fun colorSwitch() {
        textView1.setBackgroundColor(Color.parseColor("#FFB347"))
        conditions2()
    }

    fun conditions3() {
        if (calendar.get(Calendar.DAY_OF_WEEK) == weekDay[stopNumber].toInt()) {
            if (morning2[stopNumber] != "Morning") {
                if (LocalTime.now().toString().substringBefore(':').toInt() < 12) {
                    if (skip2[stopNumber] != "Skip") {
                        skippedStop()
                    } else {
                        textView1.text = display[altindex2[stopNumber].toInt()]
                    }
                } else {
                    textView1.text = display[stopNumber]
                }
            } else if (afternoon2[stopNumber] != "Afternoon") {
                if (LocalTime.now().toString().substringBefore(':').toInt() > 12) {
                    if (skip2[stopNumber] != "Skip") {
                        skippedStop()
                    } else {
                        textView1.text = display[altIndex[stopNumber].toInt()]
                    }
                } else {
                    textView1.text = display[stopNumber]
                }
            } else {
                textView1.text = display[stopNumber]
            }
        } else {
            textView1.text = display[stopNumber]
        }
    }

    fun conditions2() {
        if (morning[stopNumber] != "Morning") {
            if (LocalTime.now().toString().substringBefore(':').toInt() < 12) {
                if (skip[stopNumber] != "Skip") {
                    skippedStop()
                } else {
                    textView1.text = display[altIndex[stopNumber].toInt()]
                }
            } else {
                textView1.text = display[stopNumber]
            }
        } else if (afternoon[stopNumber] != "Afternoon") {
            if (LocalTime.now().toString().substringBefore(':').toInt() > 12) {
                if (skip[stopNumber] != "Skip") {
                    skippedStop()
                } else {
                    if (weekdayInput()) {
                        conditions3()
                    } else {
                        textView1.text = display[altIndex[stopNumber].toInt()]
                    }
                }
            } else {
                if (weekdayInput()) {
                    conditions3()
                } else {
                    textView1.text = display[stopNumber]
                }
            }
        } else {
            if (weekdayInput()) {
                conditions3()
            } else {
                textView1.text = display[stopNumber]
            }
        }
    }

    fun weekdayInput():Boolean {
        if (weekDay[stopNumber].toIntOrNull() != null) {
            if (weekDay[stopNumber].toInt() > 0) {
                return weekDay[stopNumber].toInt() < 8
            } else {
                return false
            }
        } else {
            return false
        }
    }

    fun conditions1() {
        stopNumber += 1
        if(stopNumber > 49) {
            goToMainActivity()
        }
        else {
            if (display[stopNumber] == "END") {
                goToMainActivity()
            }
            else {
                if (display[stopNumber] == "Empty") {
                    emptyStop()
                }
                else {
                    textView1.setBackgroundColor(Color.parseColor("#FFFFFF"))
                    if (transition[stopNumber] != "Flag") {
                        colorSwitch()
                    }
                    else {
                        conditions2()
                    }
                }
            }
        }
    }

    fun log(result: String) {
        val time = LocalTime.now().toString().dropLast(4)
        log += "Stop $stopNumber,$result at,$time\n"
    }
}