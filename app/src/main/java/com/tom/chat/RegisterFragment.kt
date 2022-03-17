package com.tom.chat

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.tom.chat.databinding.FragmentRigisterBinding

class RegisterFragment :Fragment() {
    lateinit var binding:FragmentRigisterBinding
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
//            val intent=Intent(requireContext(),LoginFragment::class.java)
//            startActivity(intent)
//            var a = Intent(requireContext(),LoginFragment::class.java)
//            startActivity(a)


        }else{
            println("nononono")
        }
    }


}