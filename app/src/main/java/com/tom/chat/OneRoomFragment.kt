package com.tom.chat

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
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
import com.tom.chat.databinding.RowHotroomsBinding
import com.tom.chat.databinding.RowMessageBinding
import okhttp3.*
import okio.ByteString
import java.util.concurrent.TimeUnit

class OneRoomFragment : Fragment(){
    var TAG = OneRoomFragment::class.java.simpleName
    lateinit var websocket: WebSocket
   lateinit var adapter: MsgAdapter
    val viewModel by viewModels<MassageViewModel>()
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

        val pref2= requireContext().getSharedPreferences("userData", Context.MODE_PRIVATE)
        //Web socket
        val client = OkHttpClient.Builder()
            .readTimeout(3, TimeUnit.SECONDS)
            .build()
        val request = Request.Builder()
            .url("wss://lott-dev.lottcube.asia/ws/chat/chat:app_test?nickname=${pref2.getString("nikeName","訪客")}")
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
                var json = text
                val response = Gson().fromJson(json, MsgData::class.java)
                val userMsg = "${response.body.nickname} : ${response.body.text}"
               Log.d(TAG, ": onMessage1 $userMsg");



            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                super.onMessage(webSocket, bytes)
                Log.d(TAG, ": onMessage2 ${bytes.hex()}");
            }

            override fun onOpen(webSocket: WebSocket, response: Response) {
                super.onOpen(webSocket, response)
                Log.d(TAG, ": onOpen");
//                webSocket.send("Hello, I am Hank")
            }
        })
        binding.bSend.setOnClickListener{

            val message = binding.edMessage.text.toString()
            println(message)
//            {
//                "action": "N",                     // 訊息類型，只有N是訪客能使用的，其餘都發不出去
//                "content": "測試測試測試測試測試測試"  // 訊息文字內容
//            }
//            val json = "{\"action\": \"N\", \"content\": \"$message\" }"
//            websocket.send(json)
            websocket.send(Gson().toJson(MessageSend("N", message)))
        }


        //聊天室
        binding.recyclerMessage.setHasFixedSize(true) //尺寸常數
        binding.recyclerMessage.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL , true)
        adapter = MsgAdapter()
       // binding.recyclerMessage.scrollToPosition(-1)
        binding.recyclerMessage.adapter = adapter
        viewModel.massages.observe(viewLifecycleOwner) { mas ->
            //呼叫並且傳進去房間數據
            adapter.submitMsg(mas)
        }

        viewModel.getAllMsg()





        //影片
        val videoView = binding.videoView2
        videoView.setVideoURI(Uri.parse("android.resource://" + requireContext().packageName + "/" + R.raw.hime))
        videoView.start()

        //回首頁
        binding.bOut.setOnClickListener {
            val item = LayoutInflater.from(requireContext()).inflate(R.layout.heart, null)
            androidx.appcompat.app.AlertDialog.Builder(requireContext())
            AlertDialog.Builder(requireContext()) .setView(item)
                .setTitle("確定要離開嗎")
                .setMessage("不再想想嗎")
                .setPositiveButton("立馬走") { d, w ->
                    val intent= Intent(requireContext(),MainActivity::class.java)
                    startActivity(intent)
//                    requireActivity().supportFragmentManager.beginTransaction().run {
//                        replace(R.id.my_container, LiveHomeFragment()).commit()
//                    }
                }
                .setNegativeButton("先不要") { d, w ->
                    null
                }.show()
        }
    }
    inner class MsgAdapter :RecyclerView.Adapter<MsgViewHolder>(){
        val msgs = mutableListOf<Body>()
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MsgViewHolder {
            val binding = RowMessageBinding.inflate(layoutInflater,parent,false)

            return MsgViewHolder(binding)
        }

        override fun onBindViewHolder(holder: MsgViewHolder, position: Int) {
            val body= msgs[position]
            holder.tvmsg.text = "$body.nickname : $body.text "
        }

        override fun getItemCount(): Int {
            return  msgs.size
        }

        fun submitMsg(rrr: List<Body>) {
        msgs.clear()
        msgs.addAll(rrr)
        notifyDataSetChanged()
        }

    }

    inner class MsgViewHolder(val binding: RowMessageBinding):RecyclerView.ViewHolder(binding.root){
//        val title = binding.tvTitle
//        val nickname = binding.tvName
//        val headpic = binding.imageView
        val tvmsg  = binding.tvMsg
    }

}