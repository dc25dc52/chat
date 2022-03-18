package com.tom.chat

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.tom.chat.databinding.ActivityMainBinding
import com.tom.chat.room.UserData
import com.tom.chat.room.UserDataBase
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity(),LoginFragment.SendListener{
    lateinit var binding: ActivityMainBinding
    var TAG = MainActivity::class.java.simpleName
    val fragments = mutableListOf<Fragment>()

    //static
//    companion object{//判斷是否已登陸
//       var userStatus = false
//        var username = ""
//    }

//    var personResultLaunchar = registerForActivityResult(    //註冊將要執行甚麼功能
//        NameContract()){result ->  //ActivityResultContracts.StartActivityForResult()){ result ->  //跳轉頁面並回傳資料
//        Log.d(TAG, " $result")
//        binding.textView2.text = "用戶：$Gdata"
//        username = result
//        userStatus=true
//    }
    //用來給使用資料
//    var personForUserData2 = registerForActivityResult(
//      NameContract1()){result ->
//        Log.d(TAG, "date2 : $result ")
//   }
//main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var dataBase = Room.databaseBuilder(this, UserDataBase::class.java,
            "user5.db").build()

    //    dataBase.userDao().insert(da)
//        thread {
//            dataBase.userDao().insert(da)
//        }.start()


        Log.d("MainActivity1111","传递来的数据是---$Gdata")
        if(Gdata.length != 0){
            binding.textView6.setVisibility(View.VISIBLE)
            binding.textView6.text = "用戶：$Gdata"
        }else{
            binding.textView6.text = "用戶：訪客"
        }

      //  userStatus = intent.getBooleanExtra("st",false)//用來判斷登陸狀態
//        val bundle = Bundle()
//        bundle.putString("data",Gdata)
//        val fragment = LiveHomeFragment()
//        fragment.arguments = bundle
//        val manager = supportFragmentManager
//        val transaction  = manager.beginTransaction()
//        transaction.replace(R.id.recycler,fragment)
//        transaction.commit()
       initFragments()

    binding.bottomNavBar.setOnItemSelectedListener {
            item ->
        when(item.itemId){
            R.id.n_home ->{
                if(Gdata.length !=0){
                    binding.textView6.setVisibility(View.VISIBLE)
                    binding.textView6.text = "用戶：$Gdata"
                }
                supportFragmentManager.beginTransaction().run {
                    replace(R.id.my_container,fragments[1])
                    commit()
                    true
                }}
            R.id.n_search->{true}
            R.id.n_person->{

                    if(Gdata.length ==0){
                        supportFragmentManager.beginTransaction().run {
                            binding.textView6.setVisibility(View.INVISIBLE)
                            replace(R.id.my_container,fragments[0])
                            commit()
                            true
                        }
                    }else{
//                        val fu = UserDataFragment()
//                        val bundle = Bundle()
//                        bundle.putString("test", "test")
//                        fu.setArguments(bundle)
                        supportFragmentManager.beginTransaction().run {
                            binding.textView6.setVisibility(View.INVISIBLE)
                            replace(R.id.my_container,fragments[2])
                            commit()
                            true
                        }
                    }


            }
            else ->true
        }

    }

        //setupFuctions() //導覽功能 設置功能
        //ViewModel

//        viewModel.chatRooms.observe(this) { rooms ->
//            adapter.submitRooms(rooms)
//        }
//        viewModel.getAllRooms()
    }
    fun getTitles(): String? {
        return Gdata
    }

    private fun initFragments(){
        fragments.add(0,LoginFragment())
        fragments.add(1, LiveHomeFragment())
        fragments.add(2, UserDataFragment())
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.my_container, fragments[1])
            commit()
        }


//    //SecondFragment
//    fun chatRoomClicked(lightyear : Lightyear) {
//        val bundle = Bundle().apply {
//            putParcelable("room", lightyear)
//        }
//    }


//    fun setupFuctions() {//個人按鈕 跳轉
//        binding.bottomNavBar.setOnItemReselectedListener { item ->
//            when(item.itemId) {
//                R.id.n_home -> {
//                    println("hell")
//                    true
//                }
//                R.id.n_search -> {
//                    println("n_search")
//                    true
//                }
//                R.id.n_person -> {
//                    if(userStatus==true) { //判斷登陸狀態選擇個人頁面
////                    val personData = Intent(this,UserDataActivity::class.java)
////                    personData.putExtra("PERSONDATA","$username")
////                    startActivity(personData)
////                    finish() //關閉
//                }else {
//                    println("狀態：未登陸")
//                   // personResultLaunchar.launch(null)
//                }
//                    true
//                }
//                else -> true
//            }
//        }
//    }
    //用來接收登陸後傳遞的資料
//    class NameContract:ActivityResultContract<Unit,String>(){
//        //創建意圖
//        override fun createIntent(context: Context, input: Unit?): Intent {
//            return Intent(context,LoginActivity::class.java)
//        }
//        //接受意圖並處理
//        override fun parseResult(resultCode: Int, intent: Intent?): String {
//            if(resultCode == RESULT_OK) {
//                var personData = intent?.getStringExtra("PERSONDATA")
//                Log.d(TAG, "parseResult首頁獲取用戶資訊: $personData ")
//                return personData!!
//            }else {
//                return  "no data"
//            }
//        }
//    }
    }
    companion object {
        private var Gdata: String = ""
    }

    override fun sendData(data: String) {
        Gdata = data
    }


}