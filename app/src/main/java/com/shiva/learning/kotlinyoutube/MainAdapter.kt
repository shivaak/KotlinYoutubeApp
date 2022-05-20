package com.shiva.learning.kotlinyoutube

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.shiva.learning.kotlinyoutube.databinding.RowLayoutBinding
import com.squareup.picasso.Picasso

class MainAdapter(private val homeFeed: HomeFeed) :
    RecyclerView.Adapter<MainAdapter.CustomViewHolder>() {

    companion object {
        val NAVBAR_TITLE_KEY = "NAVBAR_TITLE"
        val VIDEO_ID_KEY = "VIDEO_ID"
    }

    inner class CustomViewHolder(val rowLayoutBinding: RowLayoutBinding) :
        RecyclerView.ViewHolder(rowLayoutBinding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context);
        val cellForRow = RowLayoutBinding.inflate(layoutInflater, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        with(holder) {
            val currentVideo = homeFeed.videos[position]
            with(homeFeed.videos[position]) {
                rowLayoutBinding.txtChannelTitle.text = name
                rowLayoutBinding.txtChannelName.text = "${channel.name} * 20k views \n 4 days ago"
                Picasso.get().load(imageUrl).into(holder.rowLayoutBinding.imgVideoThumbail);
                Picasso.get().load(channel.profileimageUrl)
                    .into(holder.rowLayoutBinding.imgChannelThumnail);
                /*GlideApp.with(holder.itemView.context)
                    .load(badgeUrl)
                    .into(binding.topLearnerImage)*/

                holder.itemView.setOnClickListener {
                    val intent = Intent(holder.itemView.context, CourseDetailActivity::class.java)
                    intent.putExtra(NAVBAR_TITLE_KEY, currentVideo.name)
                    intent.putExtra(VIDEO_ID_KEY, currentVideo.id)
                    holder.itemView.context.startActivity(intent)
                }
            }
        }
        /* val video = homeFeed.videos[position];
         val videoTitle = video.name;
         val channelName = video.channel.name
         val videoImageUrl = video.imageUrl
         val channelImageUrl = video.channel.profileimageUrl
         holder.rowLayoutBinding.txtChannelTitle.text = videoTitle
         holder.rowLayoutBinding.txtChannelName.text =

             Picasso.get().load(videoImageUrl).into(holder.rowLayoutBinding.imgVideoThumbail);
         Picasso.get().load(channelImageUrl).into(holder.rowLayoutBinding.imgChannelThumnail);*/

    }

    override fun getItemCount(): Int {
        return homeFeed.videos.count()
    }
}

/*
class CustomViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val imageView = view.findViewById(R.id.imageView2);

}*/