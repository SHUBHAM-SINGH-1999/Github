package com.example.github

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide


class profile : AppCompatActivity() {

    lateinit var math: String
    lateinit var pro: ImageView
    lateinit var name: TextView
    lateinit var login: TextView
    lateinit var follower: TextView
    lateinit var following: TextView
    lateinit var company: TextView
    lateinit var location: TextView
    lateinit var html_url: String
    lateinit var url_btn: Button

    lateinit var list2: ArrayList<data>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        pro = findViewById<ImageView>(R.id.profile_photo)
        name = findViewById<TextView>(R.id.name1)
        login = findViewById<TextView>(R.id.login)
        follower = findViewById<TextView>(R.id.follower)
        following = findViewById<TextView>(R.id.following)
        company = findViewById<TextView>(R.id.company)
        location = findViewById<TextView>(R.id.location)
        url_btn = findViewById(R.id.url_btn)


        list2 = ArrayList()


        math = intent.getStringExtra("name").toString()
        load1()
        url_btn.setOnClickListener {
            Toast.makeText(this,"Opned--${name.text}",Toast.LENGTH_SHORT).show()
            val builder = CustomTabsIntent.Builder()
            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(this, Uri.parse(html_url))
        }
    }

    fun load1() {
        val queue = Volley.newRequestQueue(this)
        val url = "https://api.github.com/users"

        var new_url = "$url/$math"

        var jsonobj = JsonObjectRequest(Request.Method.GET, new_url, null, { Response ->
            name.text = Response.getString("name")
            login.text = Response.getString("login")
            follower.text = ("  ${Response.getString("followers")} followers")
            following.text = ("  ${Response.getString("following")} following")
            company.text = ("  ${Response.getString("company")}")
            location.text = ("  ${Response.getString("location")}")
            html_url=Response.getString("html_url")
            var pi = Response.getString("avatar_url")

            if (name.text == "null") name.text = "Not Mentioned"
            if (login.text == "null") login.text = "Not Mentioned"
            if (following.text == "null") following.text = "Not Mentioned"
            if (follower.text == "null") follower.text = "Not Mentioned"
            if (company.text == "null") company.text = "Not Mentioned"
            if (location.text == "null") location.text = "Not Mentioned"

            Glide.with(this).load(pi).circleCrop().into(pro)
        }, {
            Toast.makeText(this@profile, "Match not found", Toast.LENGTH_SHORT).show()
        })

        queue.add(jsonobj)

    }
}

