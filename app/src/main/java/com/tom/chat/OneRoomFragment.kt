package com.tom.chat

import android.app.AlertDialog
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tom.chat.databinding.FragmentOneroomBinding
import okhttp3.OkHttpClient
import okhttp3.Request
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
                    requireActivity().supportFragmentManager.beginTransaction().run {
                        replace(R.id.my_container, LiveHomeFragment()).commit()
                    }
                }
                .setNegativeButton("先不要") { d, w ->
                    null
                }.show()



        }
    }
}