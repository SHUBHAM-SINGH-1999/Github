package com.example.github

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide


class myadapter(val list:ArrayList<data>,val listner:btnclick):RecyclerView.Adapter<myadapter.viewholder>() {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
   val inflat = LayoutInflater.from(parent.context)
   val view = inflat.inflate(R.layout.item,parent,false)
   val vi = viewholder(view)
      vi.btn1.setOnClickListener {
          listner.onbtnclick(list[vi.adapterPosition].name)
      }
   return vi
  }


    override fun onBindViewHolder(holder: viewholder, position: Int) {
   holder.name.text = list[position].name.uppercase()
   holder.des.text = "Id-${list[position].id.uppercase()}"
   Glide.with(holder.itemView.context).load(list[position].avatar).circleCrop().into(holder.image)

        var color = "#CCCCCC"

        if(position%2==0){
            color = "#EEEEEE"
        }

        holder.main_layout.setBackgroundColor(Color.parseColor(color))
  }



    override fun getItemCount(): Int {
   return list.size
  }

  class viewholder(itemView: View):ViewHolder(itemView){
   var name = itemView.findViewById<TextView>(R.id.name)
   var des = itemView.findViewById<TextView>(R.id.description)
   var image = itemView.findViewById<ImageView>(R.id.img)
   var btn1 = itemView.findViewById<Button>(R.id.btn)
   var main_layout = itemView.findViewById<LinearLayout>(R.id.container)
  }
 }

interface btnclick{
    fun onbtnclick(btn:String){
    }
}