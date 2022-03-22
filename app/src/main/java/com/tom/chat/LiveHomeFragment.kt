package com.tom.chat

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLivehomeBinding.inflate(layoutInflater)
        return binding.root
    }
//////////////////////////////////////////////////////////////////////////////
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("您執行live")
        //recycler元件設置
        //尺寸設常數不可變動 當我們確定Item的改變不會影響RecyclerView的寬高的時候可以設置
        binding.recycler.setHasFixedSize(true)
        //設定水平列表為２
        binding.recycler.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = ChatRoomAdapter()
        binding.recycler.adapter = adapter

        //ViewModel . ChatViewModel獲取數據
        viewModel.chatRooms.observe(viewLifecycleOwner) { rooms ->
            //呼叫並且傳進去房間數據
            adapter.submitRooms(rooms)
        }
        viewModel.getAllRooms()
    }

    //需繼承 並覆寫方法
    // /


inner class ChatRoomAdapter : RecyclerView.Adapter<ChatRoomViewHolder>() {
    val chatRooms = mutableListOf<Lightyear>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatRoomViewHolder {
        val binding = RowHotroomsBinding.inflate(layoutInflater,parent,false)
        return ChatRoomViewHolder(binding)
    }
    //設置圖片
    val option = RequestOptions()
        .error(R.mipmap.ic_launcher_round)
        .transform(CenterCrop(),RoundedCorners(50))

    override fun onBindViewHolder(holder: ChatRoomViewHolder, position: Int) {
        val lightYear= chatRooms[position]
        holder.title.text = lightYear.stream_title
        holder.nickname.text = lightYear.nickname
        Glide.with(this@LiveHomeFragment)//參考：https://blog.csdn.net/Simon_YDS/article/details/108106764
            .applyDefaultRequestOptions(option)
            .load(lightYear.head_photo)
            .into(holder.headpic)

        //個人房間點擊
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
/////
    //獲取房間資料
    inner class ChatRoomViewHolder(val binding: RowHotroomsBinding):RecyclerView.ViewHolder(binding.root){
        val title = binding.tvTitle
        val nickname = binding.tvName
        val headpic = binding.imageView
    }
    //轉換各自房間
    fun chatRoomClicked(lightyear : Lightyear) {
        val hide = requireActivity() as MainActivity
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.my_container, OneRoomFragment())
            .disallowAddToBackStack()
            .commit()
        hide.binding.bottomNavBar.visibility = View.GONE

    }
}