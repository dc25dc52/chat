package com.tom.chat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.tom.chat.databinding.FragmentLoginBinding
import com.tom.chat.room.UserDataBase


class LoginFragment : Fragment() {

    companion object{
        var user1 = ""
        var pwd1 = ""
        var temp = 0
    }
    interface SendListener{
        fun sendData(data : String)
    }

    //lateinit var binding : FragmentLoginBinding
    var TAG = LoginFragment ::class.java.simpleName
    var remember =false


    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
//        binding = FragmentLoginBinding.inflate(inflater)
//        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val pref = requireContext().getSharedPreferences("chat",Context.MODE_PRIVATE)
        val checked = pref.getBoolean("rem_username", false)
        binding.cbRemember.isChecked = checked
        binding.cbRemember.setOnCheckedChangeListener { compoundButton, checked ->
            remember = checked
            println("被執行１")
            pref.edit().putBoolean("rem_username", remember).apply()
            if (!checked) {
                println("被執行ㄉ2")
                pref.edit().putString("acc", "")
                pref.edit().putString("pwd", "")
                    .apply()
            }
        }
        val prefUser = pref.getString("acc", "")
        val prefUser2 = pref.getString("pwd", "")
        if (prefUser != "") {
            binding.tAcc.setText(prefUser)
            binding.tAcc.setText(prefUser2)
        }
        binding.bLogin.setOnClickListener {
            login()
        }
        binding.bRegister.setOnClickListener {
            register()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    ///方法區
    fun register(){
//        findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.my_container,RegisterFragment()).commit()
    }

    fun login (){
        var username = binding.tAcc.text.toString()
        var password = binding.tPwd.text.toString()
        var nikename = ""
        if(username.trim().length>=4 && password.trim().length>=4
            &&username.trim().length<=20 &&password.trim().length<=12){
            Thread{
                var list =  UserDataBase.getInstance(requireContext())!!.userDao().getAll()
                list?.forEach {
                    user1 = it.acc
                    pwd1 = it.pwd
                    nikename = it.nikeneme
                    if (user1 == username && pwd1 == password) {
                        //傳遞使用者資訊至首頁上方

                        val Mactivity = context as MainActivity
                        Mactivity.sendData(nikename)
                        //儲存數據
                        val spf =
                            requireContext().getSharedPreferences("userData", Context.MODE_PRIVATE)
                        spf.edit()
                            .putString("nikeName", nikename)
                            .putString("acc", user1)
                            .putString("pwd", pwd1)
                            .apply()

                        if (remember) {
                            val pref =
                                requireContext().getSharedPreferences("chat", Context.MODE_PRIVATE)
                            pref.edit()
                                .putString("acc", user1)
                                .putString("pwd", pwd1)
                                .apply() //.commit()
                        }
                        temp=0
                        //切換頁面
//                        findNavController().navigate(R.id.action_loginFragment_to_liveHomeFragment)

                            val intent = Intent(requireContext(), MainActivity::class.java)
                            startActivity(intent)




                    }else{
                       temp=1
                    }
                }
                //if(temp==1){
                    println("=0")
                    Looper.prepare(); //加兩句在執行緒才可使用https://www.itread01.com/content/1550295913.html
                    AlertDialog.Builder(requireContext())
                        .setTitle("錯誤")
                        .setMessage("帳號或密碼輸入錯誤")
                        .setPositiveButton("OK") { d, w ->
                            binding.tAcc.setText("")
                            binding.tPwd.setText("")
                        }.show()
                    Looper.loop();
                //}
            }.start()

        }else{
           Toast.makeText(requireContext(),"帳號：請輸入4-20位字母或數字+ 密碼：請輸入6-12位字母或數字",Toast.LENGTH_LONG).show()
            Log.d(TAG, "verify(): # 帳號：請輸入4-20位字母或數字+ 密碼：請輸入6-12位字母或數字")
        }

    }





}