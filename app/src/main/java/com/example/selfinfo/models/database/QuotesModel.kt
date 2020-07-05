package com.example.selfinfo.models.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = QUOTES_TABLE_NAME)
data class QuotesModel(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = QUOTES_ID)
    var quotesId : Int? = 0,

    @ColumnInfo(name = QUOTES_MESSAGE)
    var quotesMessage : String?,

    @ColumnInfo(name = QUOTES_AUTHOR)
    var quotesAuthor : String?


) : Parcelable{
    constructor():this(null,"","")
}

const val QUOTES_TABLE_NAME = "QuotesModel"
const val QUOTES_ID = "quotesId"
const val QUOTES_MESSAGE = "quotesMessage"
const val QUOTES_AUTHOR = "quotesAuthor"
