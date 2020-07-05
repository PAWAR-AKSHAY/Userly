package com.example.selfinfo.models.database

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class UsersWithQuotes(
    @Embedded val usersModel: UsersModel,
    @Relation(
        parentColumn = "usersId",
        entityColumn = "quotesId",
        associateBy = Junction(UsersQuotesCrossRef::class)
    )
    val quotesList: List<QuotesModel>
)



data class QuotesWithUsers(
    @Embedded val quotesModel: QuotesModel,
    @Relation(
        parentColumn = "quotesId",
        entityColumn = "usersId",
        associateBy = Junction(UsersQuotesCrossRef::class)
    )
    val usersList: List<UsersModel>
)