package com.tom.chat

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.tom.chat.databinding.FragmentLivehomeBinding
import com.tom.chat.databinding.RowHotroomsBinding


class LiveHomeFragment :Fragment() {
    lateinit var adapter: ChatRoomAdapter
    lateinit var binding :FragmentLivehomeBinding

    val viewModel by viewModels<ChatViewModel>()
    val lyviewModel by viewModels<MyViewmodel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLivehomeBinding.inflate(layoutInflater)
        return binding.root
    }
//////////////////////////////////////////////////////////////////////////////
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("您執行live")
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

    val option = RequestOptions()
        .error(R.mipmap.ic_launcher_round)
        .transform(CenterCrop(),RoundedCorners(50))

    override fun onBindViewHolder(holder: ChatRoomViewHolder, position: Int) {
        val lightYear= chatRooms[position]
        holder.title.text = lightYear.stream_title
        holder.nickname.text = lightYear.nickname
        Glide.with(this@LiveHomeFragment)
            .applyDefaultRequestOptions(option)
            .load(lightYear.head_photo)
            .into(holder.headpic)

        //個人房間
        holder.itemView.setOnClickListener {
            chatRoomClicked(lightYear)
        }
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

    fun chatRoomClicked(lightyear : Lightyear) {
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.my_container, OneRoomFragment())
            .disallowAddToBackStack()
            .commit()
    }
//        lyviewModel.setroom(lightyear)
//        val inf = bundle.getParcelable<Lightyear>("room")
//        Log.d("pref room ", "${inf?.nickname}")
//        Log.d("to talkActivity clicked", "$bundle")


}