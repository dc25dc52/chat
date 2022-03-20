package com.tom.chat

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.room.Room
import com.tom.chat.databinding.ActivityMainBinding
import com.tom.chat.room.UserDataBase


class MainActivity : AppCompatActivity(),LoginFragment.SendListener{
    lateinit var binding: ActivityMainBinding
    val fragments = mutableListOf<Fragment>()
//main
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var dataBase = Room.databaseBuilder(this, UserDataBase::class.java,
            "user5.db").build()
    Log.d("MainActivity","接收的數據:$Gdata")

    //初始設置用戶狀態
    if(Gdata.length != 0){
        binding.textView.setVisibility(View.VISIBLE)
        binding.textView.text = "用戶：$Gdata"
    }else{
        binding.textView.text = "用戶：訪客"
    }
//        if(Gdata.length != 0){
//            binding.textView6.setVisibility(View.VISIBLE)
//            binding.textView6.text = "用戶：$Gdata"
//        }else{
//            binding.textView6.text = "用戶：訪客"
//        }

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
            R.id.n_home ->{1
                if(Gdata.length !=0){
                    binding.textView.setVisibility(View.VISIBLE)
                    binding.textView.text = "用戶：$Gdata"
                }
                supportFragmentManager.beginTransaction().run {
                    replace(R.id.my_container,fragments[1])
                    commit()
                    true
                }}
            R.id.n_search->{true}
            R.id.n_person->{
        println("點個人$Gdata")
                    if(Gdata == "訪客"){
                        println(Gdata)
                        println("~~~~")
                        supportFragmentManager.beginTransaction().run {
                            binding.textView.setVisibility(View.INVISIBLE)
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
                           binding.textView.setVisibility(View.INVISIBLE)
                            replace(R.id.my_container,fragments[2])
                            commit()
                            true
                        }
                    }


            }
            else ->true
        }

    }

2
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


    }
    companion object {
        private var Gdata: String = "訪客"
    }

    override fun sendData(data: String) {
        Gdata = data
    }


}