package com.androidproject.reelscloneandroid

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.androidproject.reelscloneandroid.databinding.ListVideoBinding
import com.androidproject.reelscloneandroid.model.Video

class VideoAdapterClass(
    val context: Context,
    val videoArrayList: ArrayList<Video>
) : RecyclerView.Adapter<VideoAdapterClass.VideoViewHolder>() {
    class VideoViewHolder(
        val binding: ListVideoBinding,
        var context: Context
    ) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}