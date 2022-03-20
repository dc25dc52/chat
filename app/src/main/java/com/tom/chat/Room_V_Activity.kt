package com.tom.chat

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tom.chat.databinding.ActivityRoomVBinding
import okhttp3.*


class Room_V_Activity : AppCompatActivity(){
    lateinit var binding: ActivityRoomVBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRoomVBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        var videoview = binding.videoView
        val uri : Uri = Uri.parse("android.resource://"+packageName+"/"+"raw/her")
        videoview.setVideoURI(uri)
        videoview.setOnPreparedListener {
            videoview.start()
        }

    }




}
data class Message(val action:String, val content: String)