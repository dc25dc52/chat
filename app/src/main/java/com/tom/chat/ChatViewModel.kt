package com.tom.chat

import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.tom.chat.databinding.RowHotroomsBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import java.net.URL
import kotlin.concurrent.thread

class ChatViewModel : ViewModel() {
    lateinit var adapter: MassageAdater
    val chatRooms = MutableLiveData<List<Lightyear>>()
    fun getAllRooms(){
        viewModelScope.launch(Dispatchers.IO) {
            val json = URL("https://api.jsonserve.com/qHsaqy").readText()
         val response = Gson().fromJson(json, ChatRooms::class.java)
            chatRooms.postValue(response.result.lightyear_list)
        }
    }

    inner class MasViewHolder(val binding: RowHotroomsBinding):RecyclerView.ViewHolder(binding.root){
        val title = binding.tvTitle
        val nickname = binding.tvName
        val headpic = binding.imageView
    }

    inner class MassageAdater() : RecyclerView.Adapter<MasViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MasViewHolder {
            TODO("Not yet implemented")
        }

        override fun onBindViewHolder(holder: MasViewHolder, position: Int) {
            TODO("Not yet implemented")
        }

        override fun getItemCount(): Int {
            TODO("Not yet implemented")
        }
    }
}