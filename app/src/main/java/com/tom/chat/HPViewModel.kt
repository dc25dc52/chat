package com.tom.chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL


class HPViewModel:ViewModel() {
    val talkRooms = MutableLiveData<List<Lightyear>>()
    fun getALLRooms(){

        viewModelScope.launch(Dispatchers.IO) {
            val json = URL("https://api.jsonserve.com/qHsaqy").readText()
            println("qq+$json")
            val response = Gson().fromJson(json, ChatRooms::class.java)
            talkRooms.postValue(response.result.lightyear_list)
        }
    }



}
