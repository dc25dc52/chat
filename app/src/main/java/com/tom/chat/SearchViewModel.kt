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
           // println("~~"+response)
            val map = mutableMapOf<String, Lightyear>()
            val keyList = mutableListOf<String>()
            val resultRoomsSet = mutableSetOf<Lightyear>()
//ChatRooms(error_code=0, error_text=, result=Result(lightyear_list=[Lightyear(background_image=
            response.result.lightyear_list.forEach {
//                println(it.stream_id.toString() + "~~~" + it)
                //設置kiy v存放他整個單一直播數據
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
                searchRooms.postValue(response.result.lightyear_list)
            } else {
                resultRoomsSet.clear()
                keyList.forEach {
                    //用list搜尋 key是否在list之中 list存放的是各內容的名
                    if ( key in it){
                        //在it的其中一個 就該索引 對應 加進新的集合
                        map[it]?.let {
                            resultRoomsSet.add(it)
                         //這裡將資料加進集合
                        }
                    }
                }
                searchRooms.postValue(resultRoomsSet.toList())
            }



        }
    }
}