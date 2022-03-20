package com.tom.chat

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.tom.chat.databinding.FragmentRigisterBinding
import com.tom.chat.room.UserData
import com.tom.chat.room.UserDataBase


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
        binding.bEnd.setOnClickListener{
            end()
        }
    }

    private fun pickFromGallery() {
        selectPictureFromGallery.launch("image/*")
    }

    fun end(){
        replaceF(LoginFragment())
    }

    fun sendData(){
        val nikename = binding.tvName.text.toString()
        val acc = binding.tvUserid.text.toString()
        val pwd = binding.tvPwd.text.toString()
        if(acc.trim().length>=4 && pwd.trim().length>=6 &&acc.trim().length<=20 &&pwd.trim().length<=12) {
            var da = UserData(nikename,acc,pwd,0)
            Thread{
                UserDataBase.getInstance(requireContext())!!.userDao().insert(da)
            }.start()
          replaceF(LoginFragment())
    println("cccc")
//            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            val intent= Intent(requireContext(),MainActivity::class.java)
            startActivity(intent)
        }else{
            println("失敗")
            AlertDialog.Builder(requireContext())
                .setTitle("格式錯誤")
                .setMessage("請確認帳號與密碼是否格式正確:帳號4~20、密碼6~12")
                .setPositiveButton("ok",null)
                .show()
        }

    }
    fun replaceF(fr : Fragment){
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.my_container, fr)
            .disallowAddToBackStack()
            .commit()
    }


}