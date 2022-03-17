package com.tom.chat

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.tom.chat.databinding.FragmentHomePopulerBinding
import com.tom.chat.databinding.RowHotroomsBinding



class HomePopulerFragment : Fragment() {
    lateinit var binding: FragmentHomePopulerBinding
    private lateinit var adapter:ChatRoomAdapter
    val viewModel by viewModels<HPViewModel>()
    val rooms = mutableListOf<Lightyear>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomePopulerBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.populerRecycler.setHasFixedSize(true)
        binding.populerRecycler.layoutManager = GridLayoutManager(requireContext(),2)
        adapter = ChatRoomAdapter()
        binding.populerRecycler.adapter =adapter

        /*thread {
            val json = URL("https://api.jsonserve.com/qHsaqy").readText()
            val gsonroom = Gson().fromJson(json,ChatRooms::class.java)
            rooms.clear()
            rooms.addAll(gsonroom.result.lightyear_list)
            activity?.runOnUiThread {
                adapter.notifyDataSetChanged()
            }
        }*/

        //ViewModel
        viewModel.talkRooms.observe(viewLifecycleOwner) { rooms ->
            adapter.submitRooms(rooms)
        }

        viewModel.getALLRooms()
    }
    inner class ChatRoomAdapter:RecyclerView.Adapter<ChatRoomHolder>(){
        val chatRooms = mutableListOf<Lightyear>()
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatRoomHolder {
            val binding = RowHotroomsBinding.inflate(layoutInflater,parent,false)
            return ChatRoomHolder(binding)
        }
        //圓角
        val option = RequestOptions()
            .error(R.mipmap.ic_launcher_round)
            //.transform(RoundedCorners(300))
            .transform(CenterCrop(),RoundedCorners(50))

        override fun onBindViewHolder(holder: ChatRoomHolder, position: Int) {
            val lightYear= chatRooms[position]
            holder.title.setText(lightYear.stream_title)
            holder.nickname.setText(lightYear.nickname)
            Glide.with(this@HomePopulerFragment)
                .applyDefaultRequestOptions(option)
                .load(lightYear.head_photo)
                .into(holder.headpic)
            holder.itemView.setOnClickListener {
                //chatRoomClicked(lightYear)
                val intents = Intent(context,TalkRoomActivity::class.java)
                startActivity(intents)
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
    inner class ChatRoomHolder(val binding:RowHotroomsBinding):RecyclerView.ViewHolder(binding.root){
        val title = binding.tvTitle
        val nickname = binding.tvName
        val headpic = binding.imageView

    }
}