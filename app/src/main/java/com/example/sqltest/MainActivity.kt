package com.example.sqltest

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.cardview.widget.CardView
import com.example.sqltest.ui.theme.SQLTestTheme

class MainActivity : ComponentActivity() {
    var recyclerView: View? = null
    var add_button: View? = null
    var empty_imageview: View? = null
    var no_data: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        recyclerView = findViewById<View>(R.id.recyclerView)
        add_button = findViewById<View>(R.id.add_button)
        empty_imageview = findViewById<View>(R.id.empty_imageview)
        no_data = findViewById<View>(R.id.no_data)
        add_button.setOnClickListener {
            val intent = Intent(this@MainActivity, AddActivity)
        }

    }
}

