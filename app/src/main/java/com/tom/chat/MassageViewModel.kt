package com.tom.chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL

class MassageViewModel :ViewModel() {
    val massages = MutableLiveData<List<Lightyear>>()
    fun getAllMsg(){
        viewModelScope.launch(Dispatchers.IO) {
            val json = URL("https://api.jsonserve.com/qHsaqy").readText()
            val response = Gson().fromJson(json, ChatRooms::class.java)
            massages.postValue(response.result.lightyear_list)
        }
    }
}