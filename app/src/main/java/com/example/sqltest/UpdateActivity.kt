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
    var id: String = ""
    var word_input: EditText? = null
    var wmeaning_input: EditText? = null
    var antonym_input: EditText? = null
    var ameaning_input: EditText? = null
    var word: String = ""
    var wmeaning: String = ""
    var antonym: String = ""
    var ameaning: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        word_input = findViewById<EditText>(R.id.word_input2)
        wmeaning_input = findViewById<EditText>(R.id.wmeaning_input2)
        antonym_input = findViewById<EditText>(R.id.antonym_input2)
        ameaning_input = findViewById<EditText>(R.id.ameaning_input2)

        getAndSetIntentData()

        val update_button = findViewById<Button>(R.id.update_button)
        update_button.setOnClickListener {
            val myDB = MyDatabaseHelper(this)
            myDB.updateRow(
                id,
                word_input.toString().trim(),
                wmeaning_input.toString().trim(),
                antonym_input.toString().trim(),
                ameaning_input.toString().trim()
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
            val id = intent.getStringExtra("id")
            val word = intent.getStringExtra("word")
            val wmeaning = intent.getStringExtra("wmeaning")
            val antonym = intent.getStringExtra("antonym")
            val ameaning = intent.getStringExtra("ameaning")

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
            myDB.deleteRow(id)
            //finish()
        }
        // Do nothing
        builder.setNegativeButton("No") { dialog, which ->}
        builder.create().show()
    }
}