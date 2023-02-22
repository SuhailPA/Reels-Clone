package com.androidproject.reelscloneandroid

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.androidproject.reelscloneandroid.databinding.ListVideoBinding
import com.androidproject.reelscloneandroid.model.ExoPlayerItem
import com.androidproject.reelscloneandroid.model.Video
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSource

class VideoAdapterClass(
    val context: Context,
    val videoArrayList: ArrayList<Video>,
    var videoPreparedListener: onVideoPreparedListener
) : RecyclerView.Adapter<VideoAdapterClass.VideoViewHolder>() {
    class VideoViewHolder(
        val binding: ListVideoBinding,
        var context: Context,
        var videoPreparedListener: onVideoPreparedListener
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var exoPlayer: ExoPlayer
        private lateinit var mediaSource: MediaSource

        fun setVideoPath(url: String) {
            exoPlayer = ExoPlayer.Builder(context).build()
            exoPlayer.addListener(object : Player.Listener {
                override fun onPlayerError(error: PlaybackException) {
                    super.onPlayerError(error)
                    Toast.makeText(context, "Can't play this video", Toast.LENGTH_SHORT).show()
                }

                override fun onPlaybackStateChanged(playbackState: Int) {
                    if (playbackState == Player.STATE_BUFFERING) {
                        binding.progressBarPlayerView.visibility = View.VISIBLE
                    } else if (playbackState == Player.STATE_READY) {
                        binding.progressBarPlayerView.visibility = View.GONE
                    }
                }

                override fun onPlayWhenReadyChanged(playWhenReady: Boolean, reason: Int) {
                    Log.i("ExoplayerCancelReason", reason.toString())
                    super.onPlayWhenReadyChanged(playWhenReady, reason)
                }
            })

            binding.playerView.player = exoPlayer

            exoPlayer.seekTo(0)
            exoPlayer.repeatMode = Player.REPEAT_MODE_ONE

            val dataSourceFactory = DefaultDataSource.Factory(context)

            mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(
                MediaItem.fromUri(
                    Uri.parse(url)
                )
            )

            exoPlayer.setMediaSource(mediaSource)
            exoPlayer.prepare()
            if (absoluteAdapterPosition == 0) {
                exoPlayer.playWhenReady = true
                exoPlayer.play()
            }


            videoPreparedListener.onVideoPrepared(ExoPlayerItem(exoPlayer,absoluteAdapterPosition))
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = ListVideoBinding.inflate(LayoutInflater.from(context), parent, false)
        return VideoViewHolder(view, context,videoPreparedListener)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val model = videoArrayList[position]

        holder.binding.playerTitle.text = model.title
        holder.setVideoPath(model.url)
    }

    override fun getItemCount(): Int {
        return videoArrayList.size ?: 0
    }

    interface onVideoPreparedListener {
        fun onVideoPrepared(exoPlayerItem : ExoPlayerItem)
    }
}