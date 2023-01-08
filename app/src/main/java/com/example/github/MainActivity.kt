package com.example.github

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import java.net.URL
import androidx.appcompat.widget.SearchView

class MainActivity : AppCompatActivity(),btnclick {

    lateinit var list:ArrayList<data>
    lateinit var recycle:RecyclerView
    lateinit var searchbar: SearchView
    lateinit var pgbar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()


        pgbar = findViewById(R.id.pgbar)
        recycle = findViewById<RecyclerView>(R.id.recycle)
        searchbar = findViewById(R.id.searchView)
        searchbar.clearFocus()
        pgbar.visibility=View.GONE
        list = ArrayList()

        load()
        recycle.layoutManager = LinearLayoutManager(this)
        recycle.adapter = myadapter(list,this)
        search()
    }


    fun load(){
        val queue = Volley.newRequestQueue(this)
        val url = "https://api.github.com/users"
         var jsonobj= JsonArrayRequest(Request.Method.GET,url,null,
             {
                 Response->
                 for(i in 0 until Response.length()){
                     var obj = Response.getJSONObject(i)
                     var name = obj.getString("login")
                     var image = obj.getString("avatar_url")
                     var des = obj.getString("node_id")
                     var html_url = obj.getString("html_url")
                     var d = obj.getString("id")
                     list.add(data(name,image,des,html_url,d))
                 }
                    recycle.adapter?.notifyDataSetChanged()
             },
             {
                Toast.makeText(this,"ERROR",Toast.LENGTH_SHORT).show()
             })

        queue.add(jsonobj)
    }

    override fun onbtnclick(btn: String) {
        val intent = Intent(this,profile::class.java)
        intent.putExtra("name",btn)
        startActivity(intent)
    }

    fun search(){

        searchbar.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                pgbar.visibility = View.VISIBLE
                list.clear()
                if (p0 != null) {
                    reload(p0)
                }
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                list.clear()
                load()
                return false
            }

        })
    }

    fun reload(name:String){
        val queue = Volley.newRequestQueue(this)
        val url = "https://api.github.com/users"

        var new_url = "$url/$name"

        var jsonobj = JsonObjectRequest(Request.Method.GET,new_url,null,{
            Response->

            var name = Response.getString("login")
            var image = Response.getString("avatar_url")
            var des = Response.getString("node_id")
            var html_url = Response.getString("html_url")
            var d = Response.getString("id")
            list.add(data(name,image,des,html_url,d))
            pgbar.visibility=View.GONE
            recycle.adapter?.notifyDataSetChanged()

        },{
            Toast.makeText(this@MainActivity,"Match not found",Toast.LENGTH_SHORT).show()
            pgbar.visibility = View.GONE
        })

        queue.add(jsonobj)
    }
}