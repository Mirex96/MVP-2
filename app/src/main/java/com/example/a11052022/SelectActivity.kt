package com.example.a11052022

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class SelectActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select)

        val selectTextView = findViewById<TextView>(R.id.selectTextView)
        findViewById<Button>(R.id.selectButton).setOnClickListener { finish() }
        val person = intent.getParcelableExtra<Person>(KEY_SELECT) ?: return
        selectTextView.text = "Name: ${person.name} \nAge:${person.age}"
    }
}