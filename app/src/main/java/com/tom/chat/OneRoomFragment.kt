package com.tom.chat

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tom.chat.databinding.FragmentOneroomBinding
import com.tom.chat.databinding.RowHotroomsBinding

class OneRoomFragment : Fragment(){
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
        //聊天室
        binding.recyclerMessage.setHasFixedSize(true) //尺寸常數
        binding.recyclerMessage.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL , true)
        adapter = MsgAdapter()
        binding.recyclerMessage.scrollToPosition(-1)
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
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MsgViewHolder {
            TODO("Not yet implemented")
        }

        override fun onBindViewHolder(holder: MsgViewHolder, position: Int) {
            TODO("Not yet implemented")
        }

        override fun getItemCount(): Int {
            TODO("Not yet implemented")
        }
        fun submitMsg(rooms: List<Lightyear>) {
//        chatRooms.clear()
//        chatRooms.addAll(rooms)
//        notifyDataSetChanged()
        }

    }
    inner class MsgViewHolder(val binding: RowHotroomsBinding):RecyclerView.ViewHolder(binding.root){
//        val title = binding.tvTitle
//        val nickname = binding.tvName
//        val headpic = binding.imageView
    }

}