package com.example.sqltest

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        val word_input = findViewById<EditText>(R.id.word_input)
        val wmeaning_input = findViewById<EditText>(R.id.wmeaning_input)
        val antonym_input = findViewById<EditText>(R.id.antonym_input)
        val ameaning_input = findViewById<EditText>(R.id.ameaning_input)
        val add_button = findViewById<Button>(R.id.add_button)
        add_button.setOnClickListener{
            val myDB = MyDatabaseHelper(this)
            myDB.insertPair(word_input.getText().toString().trim(),
                wmeaning_input.getText().toString().trim(),
                antonym_input.getText().toString().trim(),
                ameaning_input.getText().toString().trim())
        }
    }
}