package com.example.rapidbus

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_settings.*

class Settings : AppCompatActivity() {

    val auth = FirebaseAuth.getInstance()
    var currentUser = auth.currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        updateUI(currentUser.toString())
        populate()
}

    fun populate(){
        val pref = getSharedPreferences("Settings",Context.MODE_PRIVATE)
        val editor = pref.edit()
        val email = pref.getString("E-mail","").toString()
        val password = pref.getString("Password","").toString()
        val districtPath = pref.getString("District","").toString()
        val r1Label = pref.getString("Route1Label","").toString()
        val r2Label = pref.getString("Route2Label","").toString()
        val r3Label = pref.getString("Route3Label","").toString()
        val r1Path = pref.getString("Route1Path","").toString()
        val r2Path = pref.getString("Route2Path","").toString()
        val r3Path = pref.getString("Route3Path","").toString()
        emailEntry.setText(email)
        passwordEntry.setText(password)
        districtEntry.setText(districtPath)
        route1Label.setText(r1Label)
        route2Label.setText(r2Label)
        route3Label.setText(r3Label)
        route1Path.setText(r1Path)
        route2Path.setText(r2Path)
        route3Path.setText(r3Path)
        editor.apply()
    }

    fun updateUI(X:String){
        if (X == "null")
            statusCondition.text = getString(R.string.NotLoggedIn)
        else
            statusCondition.text = getString(R.string.LoggedIn)
    }

    fun login(view: View){
        val pref = getSharedPreferences("Settings",Context.MODE_PRIVATE)
        val editor = pref.edit()
        val email = emailEntry.text.toString()
        val password = passwordEntry.text.toString()
        val user = auth.currentUser
        if (email.isEmpty() and password.isEmpty()){
            Toast.makeText(baseContext, "Authentication failed - Missing parameters.",
                Toast.LENGTH_SHORT).show()
            updateUI(user.toString())
        }
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    editor.putString("E-mail",email)
                    editor.putString("Password",password)
                    editor.apply()

                    updateUI(user.toString())
                } else {
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(user.toString())
                }
                        // ...
            }
    }

    fun logout(view: View) {
        FirebaseAuth.getInstance().signOut()
        val user = auth.currentUser
        updateUI(user.toString())
        val pref = getSharedPreferences("Settings",Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.remove("Password")
        editor.apply()
        passwordEntry.text.clear()
    }



    fun openMainMenu(view: View) {
        val pref = getSharedPreferences("Settings",Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString("Route1Label",route1Label.text.toString())
        editor.putString("Route2Label",route2Label.text.toString())
        editor.putString("Route3Label",route3Label.text.toString())
        editor.putString("Route1Path",route1Path.text.toString())
        editor.putString("Route2Path",route2Path.text.toString())
        editor.putString("Route3Path",route3Path.text.toString())
        editor.putString("District",districtEntry.text.toString())
        editor.apply()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}


