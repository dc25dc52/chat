package com.tom.chat.room

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = UserDataBase.TABLENAME)
class UserData(

    @PrimaryKey
    var id :Int= 5,
    @NonNull
    var nikeneme :String,
    @NonNull
    var acc :String,
    @NonNull
    var pwd : String,
    @NonNull
    var picture : Byte
    ){


}