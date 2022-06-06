package com.example.memeoroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // for testing purposes, go right to the New Activity from Main
        var intent = Intent(this, NewActivity::class.java)
        startActivity(intent)
    }
}