package com.example.sqltest

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter internal constructor(
    private val activity: Activity,
    private val context: Context,
    private val id: ArrayList<*>,
    private val word: ArrayList<*>,
    private val wmeaning: ArrayList<*>,
    private val antonym: ArrayList<*>,
    private val ameaning: ArrayList<*>,
) : RecyclerView.Adapter<CustomAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.my_row, parent, false)
        return MyViewHolder(view)
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.id_txt.text = id[position].toString()
        holder.word_txt.text = word[position].toString()
        holder.wmeaning_txt.text = wmeaning[position].toString()
        holder.antonym_txt.text = antonym[position].toString()
        holder.ameaning_txt.text = ameaning[position].toString()
        //Recyclerview onClickListener
        holder.mainLayout.setOnClickListener {
            val intent = Intent(context, UpdateActivity::class.java)
            intent.putExtra("id", id[position].toString())
            intent.putExtra("word", word[position].toString())
            intent.putExtra("wmeaning", wmeaning[position].toString())
            intent.putExtra("antonym", antonym[position].toString())
            intent.putExtra("ameaning", ameaning[position].toString())
            activity.startActivityForResult(intent, 1)
        }
    }

    override fun getItemCount(): Int {
        return id.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var id_txt: TextView = itemView.findViewById<TextView>(R.id.id_txt)
        var word_txt: TextView = itemView.findViewById<TextView>(R.id.word_txt)
        var wmeaning_txt: TextView = itemView.findViewById<TextView>(R.id.wmeaning_txt)
        var antonym_txt: TextView = itemView.findViewById<TextView>(R.id.antonym_txt)
        var ameaning_txt: TextView = itemView.findViewById<TextView>(R.id.ameaning_txt)
        var mainLayout: LinearLayout = itemView.findViewById(R.id.mainLayout)

        init {
            //Animate Recyclerview
            val translate_anim = AnimationUtils.loadAnimation(
                context, R.anim.translate_anim
            )
            mainLayout.animation = translate_anim
        }
    }
}
