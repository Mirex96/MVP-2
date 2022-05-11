package com.example.a11052022

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class EditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val editName = findViewById<EditText>(R.id.editName)
        val editAge = findViewById<EditText>(R.id.editAge)
        val editButton = findViewById<Button>(R.id.editButton)

        val person = intent.getParcelableExtra<Person>(KEY_EDIT) ?: return
        editName.setText(person.name)
        editAge.setText(person.age)

        editButton.setOnClickListener {
            val intent = Intent()
            val editPerson = Person(
                person.id,
                editName.text.toString(),
                editAge.text.toString()
            )
            intent.putExtra(KEY_EDIT, editPerson)
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}