package com.example.selfinfo.models.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = RATING_TABLE_NAME,
    primaryKeys = ["usersId","quotesId"],
    foreignKeys = [
    ForeignKey(
        entity = UsersModel::class,
        parentColumns = ["usersId"],
        childColumns = ["uid"],
        onDelete = ForeignKey.CASCADE
    ),
        ForeignKey(
            entity = QuotesModel::class,
            parentColumns = ["quotesId"],
            childColumns = ["qid"],
            onDelete = ForeignKey.CASCADE
        )]
)
class RatingsModel(

    @ColumnInfo(name = "usersId")
    var usersId : Int,

    @ColumnInfo(name = "quotesId")
    var quotesId : Int,

    @ColumnInfo(name = COMMENTS)
    var comments : String?,

    @ColumnInfo(name = RATINGS)
    var ratings : Float?,

    @ColumnInfo(name = "uid")
    var uid : Int? = 0,

    @ColumnInfo(name = "qid")
    var qid : Int? = 0
):Parcelable{
    constructor():this(0,0,"",null,null,null)
}


const val RATING_TABLE_NAME = "RatingsModel"
const val RATING_ID = "ratingsId"
const val RATINGS = "ratings"
const val COMMENTS = "comments"