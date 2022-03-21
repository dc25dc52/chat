package com.tom.chat

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.tom.chat.databinding.FragmentUserdataBinding


class UserDataFragment :Fragment() {
    companion object{
        var titles :String?= ""
    }

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
        if(titles !="訪客" ){
            val pref = requireContext().getSharedPreferences("userData", Context.MODE_PRIVATE)
            binding.tvId.text = "暱稱:${pref.getString("nikeName","沒")}"
            binding.tvAcc.text= "帳號:${pref.getString("acc","沒")}"
        }


        binding.bSignout.setOnClickListener {
            val Mactivity = context as MainActivity
            Mactivity.sendData("訪客")
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.my_container, LoginFragment())
                .disallowAddToBackStack()
                .commit()
          //  findNavController().navigate(R.id.action_userDataFragment2_to_loginFragment)
           // navController.navigate(R.id.action_userDataFragment2_to_loginFragment)
        }
    }




}