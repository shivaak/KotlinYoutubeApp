package com.shiva.learning.kotlinyoutube

data class HomeFeed(val videos: List<Video>)

data class Video(
    val id: Int,
    val name: String,
    val link: String,
    val imageUrl: String,
    val numberOfViews: Int,
    val channel: ChannelData
)

data class ChannelData(
    val name: String,
    val profileimageUrl: String,
    val numberOfSubscribers: Int
)