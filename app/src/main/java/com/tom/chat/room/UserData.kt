package com.tom.chat.room

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
class UserData{
 //閃退 auto
    @PrimaryKey(autoGenerate = true)
    var id = 0
    var nikeneme :String=""
    var acc :String=""
    var pwd : String=""
    var picture : Byte=0
   constructor(
       nikeneme :String,
        acc :String,
        pwd : String,
       picture : Byte
   ){
       this.nikeneme=nikeneme
       this.acc=acc
       this.pwd=pwd
       this.picture=picture
   }


}