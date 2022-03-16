package com.tom.chat

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.room.Room
import com.tom.chat.databinding.ActivityMainBinding
import com.tom.chat.room.UserData
import com.tom.chat.room.UserDataBase
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var TAG = MainActivity::class.java.simpleName

    //static
    companion object{//判斷是否已登陸
       var userStatus = false
        var username = ""
    }

    var personResultLaunchar = registerForActivityResult(    //註冊將要執行甚麼功能
        NameContract()){result ->  //ActivityResultContracts.StartActivityForResult()){ result ->  //跳轉頁面並回傳資料
        Log.d(TAG, " $result")
        binding.textView2.text = "用戶：$result"
        username = result
        userStatus=true
    }
    //用來給使用資料
//    var personForUserData2 = registerForActivityResult(
//      NameContract1()){result ->
//        Log.d(TAG, "date2 : $result ")
//   }
    override fun onCreate(savedInstanceState: Bundle?) { //一開始呼叫的方法
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val ddd = com.tom.chat.room.UserData(1,"nikename","userid","pwd",10)
        Thread{
            UserDataBase.getInstance(this)!!.userDao()?.insert(ddd)
        }.start()
        userStatus = intent.getBooleanExtra("st",false)//用來判斷登陸狀態

               setupFuctions() //導覽功能 設置功能

    }

    fun setupFuctions() {//個人按鈕 跳轉
            binding.bPerson.setOnClickListener {
                if(userStatus==true) { //判斷登陸狀態選擇個人頁面
                    val personData = Intent(this,UserDataActivity::class.java)
                    personData.putExtra("PERSONDATA","$username")
                    startActivity(personData)
                    finish() //關閉
                }else {
                    println("狀態：未登陸")
                    personResultLaunchar.launch(null)
                }
            }
            binding.bSearch.setOnClickListener {
            }
            binding.bHome.setOnClickListener {
            }
    }
    //用來接收登陸後傳遞的資料
    class NameContract:ActivityResultContract<Unit,String>(){
        //創建意圖
        override fun createIntent(context: Context, input: Unit?): Intent {
            return Intent(context,LoginActivity::class.java)
        }
        //接受意圖並處理
        override fun parseResult(resultCode: Int, intent: Intent?): String {
            if(resultCode == RESULT_OK) {
                var personData = intent?.getStringExtra("PERSONDATA")
                Log.d(TAG, "parseResult首頁獲取用戶資訊: $personData ")
                return personData!!
            }else {
                return  "no data"
            }
        }
    }
//    class NameContract1:ActivityResultContract<Unit,String>(){
//        //創建意圖
//        override fun createIntent(context: Context, input: Unit?): Intent {
//            return Intent(context,UserDataActivity::class.java)
//        }
//        //接受意圖並處理
//        override fun parseResult(resultCode: Int, intent: Intent?): String {
//            println("test")
//            if(resultCode == RESULT_OK) {
//                var personData = intent?.getStringExtra("PERSONDATA")
//                Log.d(TAG, "parseResult首頁獲取用戶資訊: $personData ")
//                return personData!!
//            }else {
//                return  "no data"
//            }
//        }
   //}
}