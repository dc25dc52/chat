//package com.tom.chat.room
//
//import androidx.room.Dao
//import androidx.room.Insert
//import androidx.room.OnConflictStrategy
//import androidx.room.Query
//
//@Dao
//interface UserDao {
//    @Insert(onConflict = OnConflictStrategy.REPLACE)  //預設萬一執行出錯怎麼辦，REPLACE為覆蓋
//    fun insert(userdata : UserData)
//    @Query("select * from UserData")
//    fun getAll() : List<UserData>
//
//}