package com.example.sqltest

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

public class UpdateActivity: AppCompatActivity() {
    var word_input: EditText? = null
    var wmeaning_input: EditText? = null
    var antonym_input: EditText? = null
    var ameaning_input: EditText? = null

    private lateinit var id: String
    private lateinit var word: String
    private lateinit var wmeaning: String
    private lateinit var antonym: String
    private lateinit var ameaning: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        word_input = findViewById<EditText>(R.id.word_input2)
        wmeaning_input = findViewById<EditText>(R.id.wmeaning_input2)
        antonym_input = findViewById<EditText>(R.id.antonym_input2)
        ameaning_input = findViewById<EditText>(R.id.ameaning_input2)
        getAndSetIntentData()

        // I'm redefining the reference here because .updateRow cant take a mutable object
        // the error was
        // "Smart cast to 'EditText!' is impossible, because 'word_input' is a mutable property that could have been changed by this time"
        val update_word_input = findViewById<EditText>(R.id.word_input2)
        val update_wmeaning_input = findViewById<EditText>(R.id.wmeaning_input2)
        val update_antonym_input = findViewById<EditText>(R.id.antonym_input2)
        val update_ameaning_input = findViewById<EditText>(R.id.ameaning_input2)

        val update_button = findViewById<Button>(R.id.update_button)
        update_button.setOnClickListener {
            val myDB = MyDatabaseHelper(this)
            myDB.updateRow(
                id.toString(),
                update_word_input.getText().toString().trim(),
                update_wmeaning_input.getText().toString().trim(),
                update_antonym_input.getText().toString().trim(),
                update_ameaning_input.getText().toString().trim()
            )
        }
        val delete_button = findViewById<Button>(R.id.delete_button)
        delete_button.setOnClickListener { confirmDialog() }
//            val myDB = MyDatabaseHelper(this)
//            myDB.deleteRow(id)
    }

    fun getAndSetIntentData() {
        if (intent.hasExtra("id") &&
            intent.hasExtra("word") &&
            intent.hasExtra("wmeaning") &&
            intent.hasExtra("antonym") &&
            intent.hasExtra("ameaning")
        ) {
            id = intent.getStringExtra("id").toString()
            word = intent.getStringExtra("word").toString()
            wmeaning = intent.getStringExtra("wmeaning").toString()
            antonym = intent.getStringExtra("antonym").toString()
            ameaning = intent.getStringExtra("ameaning").toString()

            word_input?.setText(word)
            wmeaning_input?.setText(wmeaning)
            antonym_input?.setText(antonym)
            ameaning_input?.setText(ameaning)
            Log.d(
                "UpdateActivity",
                "id: $id, word: $word, wmeaning: $wmeaning, antonym: $antonym, ameaning: $ameaning"
            )
        } else {
            // If its missing any fields.
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show()
        }
    }

    fun confirmDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete $word?")
        builder.setMessage("Are you sure you want to delete $word?")
        builder.setPositiveButton("Yes") { dialog, which ->
            val myDB = MyDatabaseHelper(this)
            myDB.deleteRow(id.toString())
            //finish()
        }
        // Do nothing
        builder.setNegativeButton("No") { dialog, which ->}
        builder.create().show()
    }
}