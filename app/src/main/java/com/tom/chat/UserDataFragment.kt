package com.tom.chat

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tom.chat.databinding.FragmentUserdataBinding


class UserDataFragment :Fragment() {
    lateinit var binding : FragmentUserdataBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserdataBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titles = (activity as MainActivity).getTitles()
        println(titles)
        binding.tvId.text = "暱稱：$titles"
        binding.bSignout.setOnClickListener {

        }
    }
    companion object{
        var titles :String?= ""
    }
    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
       //titles = (activity as MainActivity).getTitles()
       // binding.tvId.text = "暱稱：$titles"


    }

}