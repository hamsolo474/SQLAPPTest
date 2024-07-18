package com.example.sqltest

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity

public class AddActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        val word_input = findViewById<EditText>(R.id.word_input).toString().trim()
        val wmeaning_input = findViewById<EditText>(R.id.wmeaning_input).toString().trim()
        val antonym = findViewById<EditText>(R.id.antonym_input).toString().trim()
        val ameaning_input = findViewById<EditText>(R.id.ameaning_input).toString().trim()
        val add_button = findViewById<Button>(R.id.add_button)
        add_button.setOnClickListener{
            val myDB = MyDatabaseHelper(this)
            myDB.insertRow(word_input, wmeaning_input, antonym, ameaning_input)
        }
    }

}