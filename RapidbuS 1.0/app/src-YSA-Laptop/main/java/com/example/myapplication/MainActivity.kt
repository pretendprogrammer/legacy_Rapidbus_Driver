package com.example.myapplication

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.github.kittinunf.fuel.httpGet
import kotlinx.android.synthetic.main.activity_main.*
import java.net.URL


class MainActivity : AppCompatActivity() {

    val headStartLink = "https://docs.google.com/spreadsheets/d/1sZVTn6aZB0rIEkKGpG0bIYr6Mp3gWSEmGz0oexbCV1E/export?format=csv"

    fun checkReadPermission(){
        val check = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (check == PackageManager.PERMISSION_GRANTED) { //Do something
            checkWritePermission()
        } else {
            requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1024)
            checkWritePermission()
        }
    }

    fun checkWritePermission(){
        val check = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        if (check == PackageManager.PERMISSION_GRANTED) { //Do something
        } else {
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1024)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkReadPermission()

        headStartLink.httpGet().response {
            request, response, result ->
            val bel = listOf(result.get().toString())
            textviewtemp.setText(bel.elementAt(3))
        }

    }

    fun startHeadstartRun(view: View) {
        val intent = Intent(this, InRoute::class.java)
        intent.putExtra("Sheet Link",headStartLink)
        intent.putExtra("Route", "Headstart")
        startActivity(intent)
    }

    fun startElementaryRun(view: View) {
        val intent = Intent(this, InRoute::class.java)
        intent.putExtra("Route", "Elementary")
        startActivity(intent)
    }
}
