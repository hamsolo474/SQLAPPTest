package com.example.sqltest

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import android.database.Cursor
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.example.sqltest.databinding.DbInterfaceBinding

class DBinterface : Fragment() {
    var recyclerView: RecyclerView? = null
    var add_button: FloatingActionButton? = null
    var empty_imageview: ImageView? = null
    var no_data: TextView? = null
    private var _binding: DbInterfaceBinding? = null
    private val binding get() = _binding!!

    var myDB: MyDatabaseHelper? = null
    var id: ArrayList<String>? = null
    var word: ArrayList<String>? = null
    var wmeaning: ArrayList<String>? = null
    var antonym: ArrayList<String>? = null
    var ameaning: ArrayList<String>? = null
    var customAdapter: CustomAdapter? = null

    fun storeDataInArrays() {
        val cursor: Cursor? = myDB?.readAll()
        if (cursor?.count == 0) {
            empty_imageview!!.visibility = View.VISIBLE
            no_data!!.visibility = View.VISIBLE
        } else {
            while (cursor!!.moveToNext()) {
                id!!.add(cursor.getString(0))
                word!!.add(cursor.getString(1))
                wmeaning!!.add(cursor.getString(2))
                antonym!!.add(cursor.getString(3))
                ameaning!!.add(cursor.getString(4))
            }
            empty_imageview!!.visibility = View.GONE
            no_data!!.visibility = View.GONE
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = DbInterfaceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        enableEdgeToEdge() // Why is this here?
//        setContentView(R.layout.db_interface)
        recyclerView = binding.recyclerView
        add_button = binding.addButton
        empty_imageview = binding.emptyImageview
        no_data = binding.noData
        add_button?.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@DBinterface, AddActivity::class.java) // Apparently you do this even if the class is in koltin
            startActivity(intent)
            findNavController().navigate(R.id.action_dbinterface_to_add_activity)
        })
        myDB = MyDatabaseHelper(binding.root.context)
        id = ArrayList<String>()
        word = ArrayList<String>()
        wmeaning = ArrayList<String>()
        antonym = ArrayList<String>()
        ameaning = ArrayList<String>()
        storeDataInArrays()
        val activity = requireActivity()
        customAdapter = CustomAdapter(activity, binding.root.context,
            id!!,
            word!!,
            wmeaning!!,
            antonym!!,
            ameaning!!)
        recyclerView?.setAdapter(customAdapter)
        recyclerView?.layoutManager = LinearLayoutManager(binding.root.context)
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == 1) {
//            recreate()
//        }
//    }

//TODO: Restore Delete all functionality
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        val inflater = menuInflater
//        inflater.inflate(R.menu.my_menu, menu)
//        return super.onCreateOptionsMenu(menu)
//    }
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if (item.itemId == R.id.delete_all) {
//            confirmDialog()
//        }
//        return super.onOptionsItemSelected(item)
//    }
//    fun confirmDialog() {
//        val builder = AlertDialog.Builder(this)
//        builder.setTitle("Delete All?")
//        builder.setMessage("Are you sure you want to delete all?")
//        builder.setPositiveButton("Yes") {
//            dialogInterface, i ->
//            val myDB = MyDatabaseHelper(this)
//            myDB.deleteAll()
//            // Refresh Activity
//            val intent = Intent(this, DBinterface::class.java)
//            startActivity(intent)
//            finish()
//        }
//        builder.setNegativeButton("No") { dialog, which -> }
//        builder.create().show()
//    }
}

