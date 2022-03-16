package com.tom.chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.tom.chat.databinding.ActivityLoginBinding
import com.tom.chat.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity() {
    lateinit var binding :ActivityLoginBinding
    var TAG = LoginActivity::class.java.simpleName
    //var perosonResultLaunchar = registerForActivityResult(            //註冊將要執行甚麼功能
        //ActivityResultContracts.StartActivityForResult()) { result ->  //跳轉頁面並回傳資料
        //Log.d(TAG, "back from LoginActivity with data?")
  //  }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
        var a = Intent(this,RegisterActivity::class.java)
        startActivity(a)
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
            setResult(RESULT_OK,personData)
            finish() //關閉
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