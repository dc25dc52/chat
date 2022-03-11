package com.tom.chat

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContract
import com.tom.chat.databinding.ActivityLoginBinding
import com.tom.chat.databinding.ActivityUserDataBinding

class UserDataActivity : AppCompatActivity() {
    lateinit var binding :ActivityUserDataBinding
    var TAG = UserDataActivity::class.java.simpleName
    companion object{
        var name = ""
    }
    var pq = registerForActivityResult(//註冊將要執行甚麼功能
       NameContract1()){ result ->
        Log.d(TAG, ": $result")
//
   }
//    var personForUserData = registerForActivityResult(MainActivity.NameContract1()){
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
    //val username = intent.getStringExtra("PERSONDATA")
    //println("輸出 $username")



    }
        fun tt(view:View){
            binding.tvAcc.text="aaa $name"
            binding.tvId.text="weidddd"
            println("04us")
        }
    class NameContract1:ActivityResultContract<Unit,String>(){
        //創建意圖
        override fun createIntent(context: Context, input: Unit?): Intent {
            return Intent(context,MainActivity::class.java)
        }
        //接受意圖並處理
        override fun parseResult(resultCode: Int, intent: Intent?): String {
            println("testparseResult")
            if(resultCode == RESULT_OK) {
                var personData = intent?.getStringExtra("PERSONDATA")
                Log.d(TAG, "qqqparseResult首頁獲取用戶資訊: $personData ")
                name = "$personData"

                return personData!!
            }else {
                return  "no data"
            }
        }
    }
}

