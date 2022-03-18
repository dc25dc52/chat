package com.tom.chat

import android.R
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.room.Room
import com.tom.chat.databinding.FragmentRigisterBinding
import com.tom.chat.room.UserData
import com.tom.chat.room.UserDataBase
import kotlin.concurrent.thread


class RegisterFragment :Fragment() {
    lateinit var binding:FragmentRigisterBinding
    val fragments = mutableListOf<Fragment>()
    val selectPictureFromGallery =
        registerForActivityResult(ActivityResultContracts.GetContent()){
                uri ->
            uri?.let{
                binding.imageHead.setImageURI(it)
                uri.toString()
            }
        }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRigisterBinding.inflate(inflater)
        // Inflate the layout for this fragment
        val s ="ssssss"
        val uri = Uri.parse(s)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imageBHead.setOnClickListener {
            pickFromGallery()
        }
        binding.bSend.setOnClickListener {

            sendData()
        }
    }
    private fun pickFromGallery(){
        selectPictureFromGallery.launch("image/*")

    }



    fun sendData(){
        val nikename = binding.tvName.text.toString()
        val userid = binding.tvUserid.text.toString()
        val pwd = binding.tvPwd.text.toString()

        if(userid.trim().length>=4 && pwd.trim().length>=4
            &&userid.trim().length<=20 &&pwd.trim().length<=12) {




            println("insert成功")
            var da = UserData(nikename,userid,pwd,0)
            Thread{
                UserDataBase.getInstance(requireContext())!!.userDao().insert(da)

            }.start()
            val intent= Intent(requireContext(),MainActivity::class.java)
            startActivity(intent)






        }else{
            println("nononono")
        }

    }


}