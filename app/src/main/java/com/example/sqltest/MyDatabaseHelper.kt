package com.example.sqltest

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import androidx.annotation.Nullable

class MyDatabaseHelper(context: Context): SQLiteOpenHelper(context, "MyDatabase.db", null, 1) {
    private val context: Context = context
    val TABLE_NAME = "MyTable"
    val COLUMN_ID = "id"
    val COLUMN_WORD = "word"
    val COLUMN_WMEANING = "wmeaning"
    val COLUMN_ANTONYM = "antonym"
    val COLUMN_AMEANING = "ameaning"

    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_WORD TEXT, $COLUMN_WMEANING TEXT, $COLUMN_ANTONYM TEXT, $COLUMN_AMEANING TEXT);"
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val query = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(query)
        onCreate(db)
    }

    fun insertRow(word: String, wmeaning: String, antonym: String, ameaning: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_WORD, word)
        values.put(COLUMN_WMEANING, wmeaning)
        values.put(COLUMN_ANTONYM, antonym)
        values.put(COLUMN_AMEANING, ameaning)

        val dbResult: Long = db.insert(TABLE_NAME, null, values)

        if (dbResult == -1L) {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Failed $dbResult", Toast.LENGTH_SHORT).show()
        }
    }

    fun updateRow(id: String, word: String, wmeaning: String, antonym: String, ameaning: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_ID, id)
        values.put(COLUMN_WORD, word)
        values.put(COLUMN_WMEANING, wmeaning)
        values.put(COLUMN_ANTONYM, antonym)
        values.put(COLUMN_AMEANING, ameaning)

        val dbResult: Int = db.update(TABLE_NAME, values, "_id=?", arrayOf(id.toString()) )

        if (dbResult == 1) {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Failed $dbResult", Toast.LENGTH_SHORT).show()
        }
    }

    fun readAll(): Cursor? {
        val query = "SELECT * FROM $TABLE_NAME"
        val db = this.readableDatabase
        return db.rawQuery(query, null) // This will return a Cursor object
    }

    fun deleteRow(id: String) {
        val query = "DELETE FROM $TABLE_NAME WHERE $COLUMN_ID = $id"
        val db = this.writableDatabase
        db.execSQL(query) // This is notably different to the other approach which uses db.delete
    }

    fun deleteAll(){
        val query = "DELETE FROM $TABLE_NAME"
        val db = this.writableDatabase
        db.execSQL(query)
    }


}