package com.tom.chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL

class SearchViewModel : ViewModel() {
    val searchRooms = MutableLiveData<List<Lightyear>>()

    fun getSearchRooms(key : String) {
        viewModelScope.launch(Dispatchers.IO) {
            val json = URL("https://api.jsonserve.com/qHsaqy").readText()
            val response = Gson().fromJson(json, ChatRooms::class.java)
            val map = mutableMapOf<String, Lightyear>()
            val keyList = mutableListOf<String>()
            val resultRoomsSet = mutableSetOf<Lightyear>()

            response.result.lightyear_list.forEach {
                map.put(it.stream_id.toString(), it)
                map.put(it.tags,it)
                map.put(it.stream_title, it)
                map.put(it.nickname, it)
                keyList.add(it.stream_id.toString())
                keyList.add(it.tags)
                keyList.add(it.stream_title)
                keyList.add(it.nickname)
            }

            if ( key == ""){
                resultRoomsSet.clear()
                println("清除1")
            } else {
                resultRoomsSet.clear()
                keyList.forEach {
                    if ( key in it){
                        println("清除2")
                        map[it]?.let {
                            resultRoomsSet.add(it)
                        }
                    }
                }
            }
          //  searchRooms.postValue(resultRoomsSet.toList())
            //卡在這
            searchRooms.postValue(response.result.lightyear_list)
        }
    }
}