package com.tom.chat

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.tom.chat.databinding.FragmentSearchBinding
import com.tom.chat.databinding.RowHotroomsBinding

class SearchFragment : Fragment() {


companion object{

}


    val roomViewModel by viewModels<SearchViewModel>()
    lateinit var binding:FragmentSearchBinding
    lateinit var adapter: ChatRoomAdapter
    val viewModel by viewModels<ChatViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

//recycler元件設置
//        尺寸設常數不可變動 當我們確定Item的改變不會影響RecyclerView的寬高的時候可以設置
        binding.hotRecycler.setHasFixedSize(true)
        //設定水平列表為２
        binding.hotRecycler.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = ChatRoomAdapter()
        binding.hotRecycler.adapter = adapter

        //ViewModel . ChatViewModel獲取數據
        viewModel.chatRooms.observe(viewLifecycleOwner) { rooms ->
            //呼叫並且傳進去房間數據
            adapter.submitRooms(rooms)
        }
        //var adapter1 = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, lightYear)
       // binding.listView.adapter = adapter1

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                val keywords = binding.searchView.query.toString()
                roomViewModel.getSearchRooms(keywords)
                return false
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                //通过首字符筛选内容
                //adapter1.filter.filter(p0)
                val keywords = binding.searchView.query.toString()
                roomViewModel.getSearchRooms(keywords)
                return false
            }
        })
//        binding.hotRecycler.setHasFixedSize(true)
//        //設定水平列表為２
//        binding.hotRecycler.layoutManager = GridLayoutManager(requireContext(), 2)
//        adapter = ChatRoomAdapter()
//        binding.hotRecycler.adapter = adapter

       // viewModel.getAllRooms()
    }
    inner class ChatRoomAdapter : RecyclerView.Adapter<ChatRoomViewHolder>() {

        val searchRooms = mutableListOf<Lightyear>()
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatRoomViewHolder {
            val binding = RowHotroomsBinding.inflate(layoutInflater,parent,false)
            return ChatRoomViewHolder(binding)
        }
        //設置圖片
        val option = RequestOptions()
            .error(R.mipmap.ic_launcher_round)
            .transform(CenterCrop(), RoundedCorners(50))

        override fun onBindViewHolder(holder: ChatRoomViewHolder, position: Int) {
            val lightYear= searchRooms[position]
            holder.title.text = lightYear.stream_title
            holder.nickname.text = lightYear.nickname
            Glide.with(this@SearchFragment)//參考：https://blog.csdn.net/Simon_YDS/article/details/108106764
                .applyDefaultRequestOptions(option)
                .load(lightYear.head_photo)
                .into(holder.headpic)

            //個人房間點擊
            holder.itemView.setOnClickListener {
                chatRoomClicked(lightYear)
            }
        }

        override fun getItemCount(): Int {
            return searchRooms.size
        }
        fun submitRooms(rooms: List<Lightyear>) {
            searchRooms.clear()
            searchRooms.addAll(rooms)
            notifyDataSetChanged()
        }
    }
    /////
    //獲取房間資料
    inner class ChatRoomViewHolder(val binding: RowHotroomsBinding): RecyclerView.ViewHolder(binding.root){
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