package com.example.selfinfo.models.database

import androidx.room.Entity

@Entity(primaryKeys = ["usersId","quotesId"])
data class UsersQuotesCrossRef(
    val usersId : Int,
    val quotesId : Int
)