package com.tom.chat

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import com.tom.chat.databinding.ActivityRegisterBinding
import com.tom.chat.room.*
import kotlin.concurrent.thread

class RegisterActivity : AppCompatActivity() {
    lateinit var binding :ActivityRegisterBinding
    var e = registerForActivityResult(NameContract()){

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bSend.setOnClickListener {
            sendData()
        }

    }
    fun sendData(){
        val nikename = binding.tvName.text.toString()
        val userid = binding.tvUserid.text.toString()
        val pwd = binding.tvPwd.text.toString()

        if(userid.trim().length>=4 && pwd.trim().length>=4
            &&userid.trim().length<=20 &&pwd.trim().length<=12) {
            val ddd =UserData(nikename,userid,pwd,0)

            thread {
                UserDataBase.getInstance(this)?.userDao()?.insert(ddd)
            }
            println("insert成功")
            var a = Intent(this,LoginActivity::class.java)
            startActivity(a)

        }else{
            println("nononono")
        }
    }
    }
    //步驟一
    class NameContract : ActivityResultContract<Unit,String>(){
        //createIntent 负责为 startActivityForResult 提供 Intent ，
        // parseResult 负责处理 onActivityResult 中获取的结果。
        override fun createIntent(context: Context, input: Unit?): Intent {
        return Intent(context,LoginActivity::class.java)
        }

        override fun parseResult(resultCode: Int, intent: Intent?): String {
            TODO("Not yet implemented")
        }

}