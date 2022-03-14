package com.tom.chat

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import com.tom.chat.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var TAG = MainActivity::class.java.simpleName
    companion object{//判斷是否已登陸
       var userStatus = false
        var username = ""
    }

    var personResultLaunchar = registerForActivityResult(    //註冊將要執行甚麼功能
        NameContract()){result ->
        //ActivityResultContracts.StartActivityForResult()){ result ->  //跳轉頁面並回傳資料
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
        setupFuctions()
    }
    fun setupFuctions() {
            binding.bPerson.setOnClickListener { //思考帶東西回來之類
                if(userStatus==true) { //判斷登陸狀態選擇個人頁面
                println("狀態登陸")
                    ///這裡沒有正常發送
                    val personData = Intent(this,UserDataActivity::class.java)
                    personData.putExtra("PERSONDATA","wei")
//                    setResult(RESULT_OK,personData)
                    startActivity(personData)
                    finish() //關閉
                    //personForUserData2.launch(
                        //Intent(this,LoginActivity::class.java)
                       // null
                   // )
//                    personForUserData2.launch(
//                        //Intent(this,LoginActivity::class.java)
//                        null
//                    )
                }else {
                    println("狀態未登陸")
                    //val intent = Intent(this, LoginActivity::class.java)
                    personResultLaunchar.launch(
                        //Intent(this,LoginActivity::class.java)
                        null
                    )
                }
            }
            //
            //三顆按鈕的事情
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
            println("test")
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