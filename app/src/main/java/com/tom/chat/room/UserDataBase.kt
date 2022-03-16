package com.tom.chat.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(UserData::class),version = 1)
abstract class UserDataBase :RoomDatabase(){
    abstract fun userDao():UserDao
    companion object{
        const val TABLENAME = "MyTable"
        private var instance :UserDataBase? = null //一開始
        fun getInstance(context:Context) :UserDataBase?{
            if(instance==null){
                instance= Room.databaseBuilder(context,
                    UserDataBase::class.java,"user5.db").build()
            }
            return instance
        }
    }

}