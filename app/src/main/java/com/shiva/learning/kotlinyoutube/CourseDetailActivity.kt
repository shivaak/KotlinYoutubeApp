package com.shiva.learning.kotlinyoutube

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.shiva.learning.kotlinyoutube.MainAdapter.Companion.NAVBAR_TITLE_KEY
import com.shiva.learning.kotlinyoutube.databinding.ActivityCourseDetailsBinding
import com.shiva.learning.kotlinyoutube.databinding.CourseDetailLayoutBinding
import com.squareup.picasso.Picasso
import okhttp3.*
import java.io.IOException


class CourseDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCourseDetailsBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            binding = ActivityCourseDetailsBinding.inflate(layoutInflater);
            setContentView(binding.root)
            binding.recyclerViewMain.layoutManager = LinearLayoutManager(this)


            //We'll change the nav bar title
            val navBarTitle = intent.getStringExtra(MainAdapter.NAVBAR_TITLE_KEY)
            val videoId = intent.getIntExtra(MainAdapter.VIDEO_ID_KEY, 0)
            supportActionBar?.title = navBarTitle
            fetchJson(videoId)
        }

    }

    fun fetchJson(id: Int) {
        println("Attempting to fetch course detail JSON");

        val url = "https://api.letsbuildthatapp.com/youtube/course_detail?id=$id"

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
                val courseDetails = gson.fromJson(body, Array<CourseDetail>::class.java)

                println(courseDetails)
                runOnUiThread {
                    binding.recyclerViewMain.adapter = CourseDetailsAdapter(courseDetails)
                }
            }
        })
    }

    private class CourseDetailsAdapter(private val courses: Array<CourseDetail>) :
        RecyclerView.Adapter<CourseDetailsAdapter.CourseLessonViewHolder>() {

        inner class CourseLessonViewHolder(val courseDetailLayoutBinding: CourseDetailLayoutBinding) :
            RecyclerView.ViewHolder(courseDetailLayoutBinding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseLessonViewHolder {
            val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context);
            val cellForRow = CourseDetailLayoutBinding.inflate(layoutInflater, parent, false)
            return CourseLessonViewHolder(cellForRow)
        }

        override fun onBindViewHolder(holder: CourseLessonViewHolder, position: Int) {
            with(holder) {
                with(courses[position]) {
                    courseDetailLayoutBinding.txtTitle.text = name
                    courseDetailLayoutBinding.txtEpisode.text = "Episode ${position + 1}"
                    courseDetailLayoutBinding.txtDuration.text = duration
                    Picasso.get()
                        .load(imageUrl)
                        .into(holder.courseDetailLayoutBinding.imgCourseThumb);

                    holder.itemView.setOnClickListener {
                        var intent =
                            Intent(holder.itemView.context, CourseLessonActivity::class.java)
                        intent.putExtra("COURSE_URL", link)
                        //   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        holder.itemView.context.startActivity(intent)
                    }

                }
            }
        }

        override fun getItemCount(): Int {
            return courses.count();
        }
    }
}