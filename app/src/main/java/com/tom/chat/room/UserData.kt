package com.tom.chat.room

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
class UserData(
 //閃退 auto
//    @PrimaryKey
//    val id: Int,
    @NonNull
    var nikeneme :String,
    @NonNull
    var acc :String,
    @NonNull
    var pwd : String,
    @NonNull
    var picture : Byte
    ){
    @PrimaryKey
    var id =0


}