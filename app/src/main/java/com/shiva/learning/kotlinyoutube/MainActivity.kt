package com.shiva.learning.kotlinyoutube

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import com.shiva.learning.kotlinyoutube.databinding.ActivityMainBinding
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater);

        setContentView(binding.root)
        binding.recyclerViewMain.layoutManager = LinearLayoutManager(this)
        //  binding.recyclerViewMain.adapter = MainAdapter()
        fetchJson()
    }


    fun fetchJson() {
        println("Attempting to fetch JSON");

        val url = "https://api.letsbuildthatapp.com/youtube/home_feed"

        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                println("Failed to execute request")
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string();
                println(body)

                val gson = GsonBuilder().create()
                val homeFeed: HomeFeed = gson.fromJson(body, HomeFeed::class.java)

                runOnUiThread {
                    binding.recyclerViewMain.adapter = MainAdapter(homeFeed)
                }
            }
        })
    }

}


