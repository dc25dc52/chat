package com.tom.chat

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.tom.chat.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {
    interface SendListener{
        fun sendData(data : String)
    }
    lateinit var binding : FragmentLoginBinding
    var TAG = LoginFragment ::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
    binding = FragmentLoginBinding.inflate(inflater)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.bLogin.setOnClickListener {
            login()
        }
        binding.bRegister.setOnClickListener {
            register()
        }

    }

    //    fun b_login(view: View){
//
//    }
    fun register(){
       requireActivity().supportFragmentManager.beginTransaction()
           .replace(R.id.my_container,RegisterFragment()).commit()
    }
    fun login (){
        var username = binding.tAcc.text.toString()
        var password = binding.tPwd.text.toString()
        var user1 = "weiru"
        var pwd1  ="123456"
        if(username.trim().length>=4 && password.trim().length>=4
            &&username.trim().length<=20 &&password.trim().length<=12){
            if(user1==username && pwd1 ==password){
                Log.d(TAG, "b_login: 登陸帳號成功")
                //傳遞使用者資訊至首頁上方
                val personData = Intent()
                personData.putExtra("PERSONDATA",username)
                println("login: $username")

                val intent=Intent(requireContext(),MainActivity::class.java)
                startActivity(intent)
//                setResult(AppCompatActivity.RESULT_OK,personData)
//                finish() //關閉
                //perosonResultLaunchar.launch(null)
                //perosonResultLaunchar.launch(Intent(this,MainActivity::class.java)) //跳轉登陸頁面
            }else{
                Log.d(TAG, "b_login: 登陸帳號失敗")
            }
        }else{
            Log.d(TAG, "verify(): # 帳號：請輸入4-20位字母或數字+ 密碼：請輸入6-12位字母或數字")
        }
    }




}