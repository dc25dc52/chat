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

    companion object {
        private var Gdata: String = "訪客"
    }

    lateinit var binding: ActivityMainBinding
    val fragments = mutableListOf<Fragment>()

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
       initFragments()

    binding.bottomNavBar.setOnItemSelectedListener {
            item ->
        when(item.itemId){
            R.id.n_home ->{
                if(Gdata.length !=0){
                    binding.textView.setVisibility(View.VISIBLE)
                    binding.textView.text = "用戶：$Gdata"
                }
                userStateTitle(1)
                true
            }
            R.id.n_search->{
                    binding.textView.setVisibility(View.INVISIBLE)
                userStateTitle(3)
                    true
                }
            R.id.n_person->{
                binding.textView.setVisibility(View.INVISIBLE)
                    if(Gdata == "訪客"){
                        binding.textView.setVisibility(View.INVISIBLE)
                        userStateTitle(0)
                            true
                    }else{
                        userStateTitle(2)
                            true
                    }
            }
            else ->true
        }
    }
    }
    fun getTitles(): String? {
        return Gdata
    }

    private fun initFragments(){
        fragments.add(0,LoginFragment())
        fragments.add(1, LiveHomeFragment())
        fragments.add(2, UserDataFragment())
        fragments.add(3, SearchFragment())
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.my_container, fragments[1])
            commit()
        }
    }

    //用來指定頁面跳轉
    fun userStateTitle(i:Int){
        supportFragmentManager.beginTransaction().run {
            replace(R.id.my_container,fragments[i])
            commit()
            true
        }
    }

    override fun sendData(data: String) {
        Gdata = data
    }


}