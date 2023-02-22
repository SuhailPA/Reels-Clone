package com.androidproject.reelscloneandroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.androidproject.reelscloneandroid.databinding.ActivityMainBinding
import com.androidproject.reelscloneandroid.model.ExoPlayerItem
import com.androidproject.reelscloneandroid.model.Video

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: VideoAdapterClass
    private val videos = ArrayList<Video>()
    private val exoPlayerItems = ArrayList<ExoPlayerItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        videos.add(
            Video(
                "Big Buck Bunny",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
            )
        )

        videos.add(
            Video(
                "Elephant Dream",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4"
            )
        )

        videos.add(
            Video(
                "For Bigger Blazes",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4"
            )
        )
        adapter =
            VideoAdapterClass(this, videos, object : VideoAdapterClass.onVideoPreparedListener {
                override fun onVideoPrepared(exoPlayerItem: ExoPlayerItem) {
                    exoPlayerItems.add(exoPlayerItem)
                }

            })
        binding.viewPager2.adapter = adapter

        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                val previousIndex = exoPlayerItems.indexOfFirst { it.exoPlayer.isPlaying }
                if (previousIndex != -1){
                    val player = exoPlayerItems[position].exoPlayer
                    player.pause()
                    player.playWhenReady = false
                }

                val newIndex = exoPlayerItems.indexOfFirst { it.position == position }
                if (newIndex != -1){
                    val player = exoPlayerItems[position].exoPlayer
                    player.playWhenReady = true
                    player.play()
                }
            }
        })


    }
}