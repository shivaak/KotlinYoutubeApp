package com.shiva.learning.kotlinyoutube

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.shiva.learning.kotlinyoutube.databinding.RowLayoutBinding
import com.squareup.picasso.Picasso

class MainAdapter(val homeFeed: HomeFeed) : RecyclerView.Adapter<MainAdapter.CustomViewHolder>() {

    inner class CustomViewHolder(val rowLayoutBinding: RowLayoutBinding) :
        RecyclerView.ViewHolder(rowLayoutBinding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context);
        val cellForRow = RowLayoutBinding.inflate(layoutInflater, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        with(holder) {
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
                    Toast.makeText(
                        holder.itemView.context, "Muruga",
                        Toast.LENGTH_SHORT
                    ).show()
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