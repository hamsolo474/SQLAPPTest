package com.example.sqltest

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteException
import android.util.Log
import android.widget.Toast
import androidx.annotation.Nullable
import android.os.StrictMode
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


fun fetchHtml(urlString: String): String?{
    // For demonstration purposes, allow network operations on the main thread.
    // In a real app, you should perform network operations on a background thread.
    val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
    StrictMode.setThreadPolicy(policy)

    var result: String? = null
    var connection: HttpURLConnection? = null
    try {
        val url = URL(urlString)
        connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"

        val inputStream = connection.inputStream
        val reader = BufferedReader(InputStreamReader(inputStream))
        val stringBuilder = StringBuilder()
        var line: String?
        while (reader.readLine().also { line = it } != null) {
            stringBuilder.append(line).append('\n')
        }
        result = stringBuilder.toString()
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        connection?.disconnect()
    }
    return result
}


class MyDatabaseHelper(private val context: Context): SQLiteOpenHelper(context, "MyDatabase.db", null, 1) {
    val TABLE_NAME = "Antonyms"
    val COLUMN_ID = "id"
    val COLUMN_WORD = "word"
    val COLUMN_WMEANING = "wmeaning"
    val COLUMN_ANTONYM = "antonym"
    val COLUMN_AMEANING = "ameaning"
    private val url = "https://pastebin.com/raw/YEQ2euTM"

    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_WORD TEXT, $COLUMN_WMEANING TEXT, $COLUMN_ANTONYM TEXT, $COLUMN_AMEANING TEXT);"
        db?.execSQL(query)
        Log.d("MyDatabaseHelper", "Database created")
        dl_db(db)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val query = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(query)
        onCreate(db)
    }
    fun dl_db(db: SQLiteDatabase?){
        Log.d("MyDatabaseHelper", "Downloading database...")
        val query = fetchHtml(url)
//        val query = "INSERT INTO \"main\".\"Antonyms\" (\"word\", \"wmeaning\", \"antonym\", \"ameaning\") VALUES ('一起', 'Together', '分开', 'Apart');"
        Log.d("MyDatabaseHelper", "Fetched HTML: ${query?.length}, CONTENT: ${query?.take(600)}")
        for (line in query?.lines() ?: emptyList()) {
            try {
                db?.execSQL(line)
            } catch (e: SQLiteException) {  }
        }
    }

    fun insertPair(word: String, wmeaning: String, antonym: String, ameaning: String) {
        insertRow(word, wmeaning, antonym, ameaning)
        insertRow(antonym, ameaning, word, wmeaning)
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
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
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

        val dbResult: Int = db.update(TABLE_NAME, values, "id=?", arrayOf(id.toString()) )

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
        Log.d("MyDatabaseHelper", "Deleted row with id: $id")
        val query = "DELETE FROM $TABLE_NAME WHERE $COLUMN_ID = $id"
        val db = this.writableDatabase
        //db.delete(TABLE_NAME, "id=?", arrayOf(id.toString()))
        db.execSQL(query) // This is notably different to the other approach which uses db.delete
        Log.d("MyDatabaseHelper", "Deleted row with id: $id")
    }

    fun deleteAll(){
        val query = "DELETE FROM $TABLE_NAME"
        val db = this.writableDatabase
        db.execSQL(query)
    }


}