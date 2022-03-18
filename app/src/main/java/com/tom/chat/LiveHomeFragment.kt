package com.tom.chat

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.tom.chat.databinding.FragmentLivehomeBinding
import com.tom.chat.databinding.RowChatroomBinding
import com.tom.chat.databinding.RowHotroomsBinding
import okhttp3.WebSocket

class LiveHomeFragment :Fragment() {
    lateinit var adapter: ChatRoomAdapter
    lateinit var binding :FragmentLivehomeBinding
    val viewModel by viewModels<ChatViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLivehomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = arguments
        if (bundle != null) {
            Log.d("FragmentB",bundle.getString("data")!!)
            val mess = bundle.getString("data")
            println("ssssss"+mess)

        }

        //RecyclerView's Adapter
        binding.recycler.setHasFixedSize(true)
        binding.recycler.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = ChatRoomAdapter()
        binding.recycler.adapter = adapter

        //ViewModel
        viewModel.chatRooms.observe(viewLifecycleOwner) { rooms ->
            adapter.submitRooms(rooms)
        }
        viewModel.getAllRooms()
    }
inner class ChatRoomAdapter : RecyclerView.Adapter<ChatRoomViewHolder>() {
    val chatRooms = mutableListOf<Lightyear>()
    //繼承
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatRoomViewHolder {
        val binding = RowHotroomsBinding.inflate(layoutInflater,parent,false)
        return ChatRoomViewHolder(binding)
    }
    //圓角
    val option = RequestOptions()
        .error(R.mipmap.ic_launcher_round)
        //.transform(RoundedCorners(300))
        .transform(CenterCrop(),RoundedCorners(50))

    override fun onBindViewHolder(holder: ChatRoomViewHolder, position: Int) {
        val lightYear= chatRooms[position]
        holder.title.setText(lightYear.stream_title)
        holder.nickname.setText(lightYear.nickname)
        Glide.with(this@LiveHomeFragment)
            .applyDefaultRequestOptions(option)
            .load(lightYear.head_photo)
            .into(holder.headpic)
    }

    override fun getItemCount(): Int {
        return chatRooms.size
    }
    fun submitRooms(rooms: List<Lightyear>) {
        chatRooms.clear()
        chatRooms.addAll(rooms)
        notifyDataSetChanged()
    }
}

    inner class ChatRoomViewHolder(val binding: RowHotroomsBinding):RecyclerView.ViewHolder(binding.root){
        val title = binding.tvTitle
        val nickname = binding.tvName
        val headpic = binding.imageView

    }
}