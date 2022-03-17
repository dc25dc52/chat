package com.tom.chat

import android.app.Dialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import com.google.gson.Gson
import com.tom.chat.databinding.ActivityTalkRoomBinding
import okhttp3.*
import okio.ByteString
import java.util.concurrent.TimeUnit

class TalkRoomActivity : AppCompatActivity() {
    private val TAG = TalkRoomActivity::class.java.simpleName
    lateinit var binding: ActivityTalkRoomBinding
    //websocket
    lateinit var websocket: WebSocket

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityTalkRoomBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //Web socket
        val client = OkHttpClient.Builder()
            .readTimeout(3, TimeUnit.SECONDS)
            .build()
        val request = Request.Builder()
            .url("wss://lott-dev.lottcube.asia/ws/chat/chat:app_test?nickname=Hank")
            .build()
        websocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosed(webSocket, code, reason)
                Log.d(TAG, ": onClosed");
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosing(webSocket, code, reason)
                Log.d(TAG, ": onClosing");
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                super.onFailure(webSocket, t, response)
                Log.d(TAG, ": onFailure");
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                super.onMessage(webSocket, text)
                Log.d(TAG, ": onMessage $text");
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                super.onMessage(webSocket, bytes)
                Log.d(TAG, ": onMessage ${bytes.hex()}");
            }

            override fun onOpen(webSocket: WebSocket, response: Response) {
                super.onOpen(webSocket, response)
                Log.d(TAG, ": onOpen");
//                webSocket.send("Hello, I am Hank")
            }
        })
        binding.bSendtalking.setOnClickListener {
            val message = binding.talkSend.text.toString()
//            val json = "{\"action\": \"N\", \"content\": \"$message\" }"
//            websocket.send(json)
            websocket.send(Gson().toJson(Message("N", message)))
        }


        binding.bTalkout.setOnClickListener {

            val item = LayoutInflater.from(this).inflate(R.layout.heart, null)
            AlertDialog.Builder(this)
                .setView(item)
                .setPositiveButton("OK") { d, w ->
                    val intent= Intent(this,MainActivity::class.java)
                    startActivity(intent)
                }
                .setNegativeButton("Stay"){
                    d,w->null
                }
                .show()



        }
    }


}
data class Message(val action:String, val content: String)