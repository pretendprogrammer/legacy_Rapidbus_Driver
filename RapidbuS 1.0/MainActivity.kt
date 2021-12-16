package com.example.rapidbus

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val pref = getSharedPreferences("Settings", Context.MODE_PRIVATE)
        startRoute1.text = pref.getString("Route1Label","Empty")
        startRoute2.text = pref.getString("Route2Label","Empty")
        startRoute3.text = pref.getString("Route3Label","Empty")
        checkReadPermission()

        if (pref.getString("District","").toString().isEmpty()) {
            startRoute1.isEnabled = false
            startRoute2.isEnabled = false
            startRoute3.isEnabled = false
        }
        else {
            if (pref.getString("Route1Path","").toString().isEmpty())
                startRoute1.isEnabled = false
            if (pref.getString("Route2Path","").toString().isEmpty())
                startRoute2.isEnabled = false
            if (pref.getString("Route3Path","").toString().isEmpty())
                startRoute3.isEnabled = false
        }


    }

    private fun checkReadPermission(){
        val check = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (check == PackageManager.PERMISSION_GRANTED) {
            checkWritePermission()
        } else {
            requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1024)
            checkWritePermission()
        }
    }

    private fun checkWritePermission(){
        val check = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        if (check == PackageManager.PERMISSION_GRANTED) {
            checkCourseLocationPermission()
        } else {
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1024)
            checkCourseLocationPermission()
        }
    }

    private fun checkCourseLocationPermission(){
        val check = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        if (check == PackageManager.PERMISSION_GRANTED) {
            checkFineLocationPermission()
        } else {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), 1024)
            checkFineLocationPermission()
        }
    }

    private fun checkFineLocationPermission(){
        val check = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        if (check == PackageManager.PERMISSION_DENIED) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1024)
        }
    }

    fun openSettings(view: View) {
        val intent = Intent(this, Settings::class.java)
        startActivity(intent)
    }

    fun startRoute1(view: View) {
        val intent = Intent(this, InRoute::class.java)
        intent.putExtra("Route", "Route1Path")
        startActivity(intent)
    }

    fun startRoute2(view: View) {
        val intent = Intent(this, InRoute::class.java)
        intent.putExtra("Route", "Route2Path")
        startActivity(intent)
    }

    fun startRoute3(view: View) {
        val intent = Intent(this, InRoute::class.java)
        intent.putExtra("Route", "Route3Path")
        startActivity(intent)
    }
}
