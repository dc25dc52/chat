package com.tom.chat

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.tom.chat.databinding.FragmentOneroomBinding
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import java.util.concurrent.TimeUnit

class OneRoomFragment : Fragment(){

    lateinit var binding: FragmentOneroomBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOneroomBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pref = requireContext().getSharedPreferences("member", Context.MODE_PRIVATE)
        val num = pref.getInt("lognum", 0)
        val name = pref.getString("name$num", "")
        //聊天室
        binding.recyclerMessage.setHasFixedSize(true)
        binding.recyclerMessage.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL , true)
        binding.recyclerMessage.scrollToPosition(-1)
        //websocket
        val client = OkHttpClient.Builder()
            .readTimeout(3, TimeUnit.SECONDS)
            .build()
        val request = Request.Builder()
            .url("wss://lott-dev.lottcube.asia/ws/chat/chat:app_test?nickname=$name")
            .build()
//        var websocket = client.newWebSocket(request, object : WebSocketListener(){
//            override fun onMessage(webSocket: WebSocket, text: String) {
//                super.onMessage(webSocket, text)
//                var message = ""
//                val json = text
//                if ("default_message" in json){
//                    val response = Gson().fromJson(json, Message::class.java)
//                    message = response.body.nickname+":"+response.body.text
//                }else if ("sys_updateRoomStatus" in json){
//                    val response = Gson().fromJson(json, Into::class.java)
//                    var action = response.body.entry_notice.action
//                    var user = response.body.entry_notice.username
//                    when(action){
//                        "enter" -> message = "$user"+getString(R.string.enter)
//                        "leave" -> message = "$user"+getString(R.string.leave)
//                        else -> message = ""
//                    }
//                }else if ("admin_all_broadcast" in json){
//                    val response = Gson().fromJson(json, Notice::class.java)
//                    val cn = response.body.content.cn
//                    val en = response.body.content.en
//                    val tw = response.body.content.tw
//                    message = """en"""+cn+"\n"+"""cn"""+en+"\n"+"""tw"""+tw
//                }else if ("sys_room_endStream" in json){
//                    val response = Gson().fromJson(json, End::class.java)
//                    message = response.body.text
//                }
//                BindingViewHolder(RowMessageBinding.bind(view)).chatmsg.setText(message)
//            }
//        })

        //Media
        val videoView = binding.videoView2
        videoView.setVideoURI(Uri.parse("android.resource://" + requireContext().packageName + "/" + R.raw.hime3))
        videoView.start()

//        //按傳送按鈕後
//        binding.ibSend.setOnClickListener {
//            val msg = binding.edMessage.text.toString()
//            val j = Gson().toJson(Send("N",msg))
//            websocket.send(j)
//            binding.edMessage.setText("")
//        }

//        //按右上的按鈕會回首頁
//        binding.ibOut.setOnClickListener {
//            val parentactivity = requireActivity() as MainActivity
//            requireActivity().supportFragmentManager.beginTransaction().run {
//                replace(R.id.my_container, HomeFragment()).commit()
//            }
//            parentactivity.binding.roomContainer.visibility = View.GONE
//            parentactivity.binding.buttonNavBar.visibility = View.VISIBLE
//            parentactivity.binding.myContainer.visibility = View.VISIBLE
//        }
    }
//    inner class BindingViewHolder(binding: RowMessageBinding): RecyclerView.ViewHolder(binding.root){
//        val chatmsg = binding.tvMessage
//    }
}