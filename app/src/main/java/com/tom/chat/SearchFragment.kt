package com.tom.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.tom.chat.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {
    val roomViewModel by viewModels<SearchViewModel>()
    lateinit var binding:FragmentSearchBinding
    lateinit var adapter1: ChatRoomAdapter
    lateinit var adapter: SearchRoomAdapter
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
        adapter = SearchRoomAdapter()
        binding.hotRecycler.adapter = adapter

        //ViewModel . ChatViewModel獲取數據
        roomViewModel.searchRooms.observe(viewLifecycleOwner) { rooms ->
            //呼叫並且傳進去房間數據
            adapter.submitRooms(rooms)
        }
        /////
        binding.hot2.setHasFixedSize(true)
        //設定水平列表為２
        binding.hot2.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter1 = ChatRoomAdapter()
        binding.hot2.adapter = adapter1

        //ViewModel . ChatViewModel獲取數據
        viewModel.chatRooms.observe(viewLifecycleOwner) { rooms ->
            //呼叫並且傳進去房間數據
            adapter1.submitRooms1(rooms)
        }
        viewModel.getAllRooms()
        //var adapter1 = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, emptyList)
        // binding.listView.adapter = adapter1
        //var adapter1 = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, bestCities)

        //binding.tvSerachtext.visibility = View.GONE
        binding.hotRecycler.visibility = View.GONE
        binding.searchView.setOnCloseListener(object : SearchView.OnCloseListener{
            override  fun onClose(): Boolean {
                binding.hotRecycler.visibility = View.GONE
               // binding.tvHot.visibility = View.VISIBLE
                //binding.hot2.visibility = View.VISIBLE
                return false
            }
        })
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
                if(keywords ==""){
                    //binding.tvSerachtext.visibility = View.GONE
                   binding.hotRecycler.visibility = View.GONE
                   /// binding.hot2.visibility = View.VISIBLE

                }else {
                  //  binding.tvSerachtext.visibility = View.VISIBLE
                    binding.hotRecycler.visibility = View.VISIBLE
                  //  binding.hot2.visibility = View.GONE
                   // binding.tvHot.visibility = View.GONE
                }

                roomViewModel.getSearchRooms(keywords)
                return false
            }
        })


    }
    inner class SearchRoomAdapter : RecyclerView.Adapter<ChatRoomViewHolderr>() {
        val searchRooms = mutableListOf<Lightyear>()
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatRoomViewHolderr {
            val view = layoutInflater.inflate(
                R.layout.row_hotrooms, parent, false)
            return ChatRoomViewHolderr(view)
        }
        //設置圖片
        val option = RequestOptions()
            .error(R.mipmap.ic_launcher_round)
            .transform(CenterCrop(), RoundedCorners(50))

        override fun onBindViewHolder(holder: ChatRoomViewHolderr, position: Int) {
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
            emptyList.addAll(rooms)
            notifyDataSetChanged()
        }

    }
    var emptyList = mutableListOf<Lightyear>()

    inner class ChatRoomAdapter : RecyclerView.Adapter<ChatRoomViewHolderr>() {

        val chatRooms = mutableListOf<Lightyear>()
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatRoomViewHolderr {
            val view = layoutInflater.inflate(
                R.layout.row_hotrooms, parent, false)
            return ChatRoomViewHolderr(view)
        }
        //設置圖片
        val option = RequestOptions()
            .error(R.mipmap.ic_launcher_round)
            .transform(CenterCrop(),RoundedCorners(50))

        override fun onBindViewHolder(holder: ChatRoomViewHolderr, position: Int) {
            val lightYear= chatRooms[position]
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
        fun submitRooms1(rooms: List<Lightyear>) {
            chatRooms.clear()
            chatRooms.addAll(rooms)
            emptyList.addAll(rooms)
            notifyDataSetChanged()
        }
        override fun getItemCount(): Int {
            return chatRooms.size
        }

    }





    /////
    //獲取房間資料
    inner class ChatRoomViewHolderr(view: View) : RecyclerView.ViewHolder(view){
        val title = view.findViewById<TextView>(R.id.tv_title)
        val nickname = view.findViewById<TextView>(R.id.tv_name)
        val headpic = view.findViewById<ImageView>(R.id.imageView)
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
