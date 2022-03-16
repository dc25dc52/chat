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
        var name :String? ="無"
    }

    var pq = registerForActivityResult(//註冊將要執行甚麼功能
        NameContract1()){ result ->
        Log.d(TAG, "useradat測試: $result")
        println("被點擊3")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        println("user oncrate被執行")
        name = intent.getStringExtra("PERSONDATA")
        //＊＊＊＊登出後在登陸所得到NULL ,已解決 因為前面有put別的 所以第二個put被阻塞無法接受
        println("得到姓名 $name")
        //得到用戶資料
        binding.tvAcc.text="陽光宅男"
        binding.tvId.text="$name"
        signout()
        //
        //pq.launch(null)
    }
    fun signout(){

        binding.bSignout.setOnClickListener {
            println("被點擊登出")
//            val personData = Intent(this,UserDataActivity::class.java)
//            personData.putExtra("userStatus",0)
//            startActivity(personData)
          //  finish() //關閉
            pq.launch(null)

        }
    }
    class NameContract1:ActivityResultContract<Unit,String>(){
        //創建意圖
        override fun createIntent(context: Context, input: Unit?): Intent {
            return Intent(context,LoginActivity::class.java)
        }
        //接受意圖並處理
        override fun parseResult(resultCode: Int, intent: Intent?): String {
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

