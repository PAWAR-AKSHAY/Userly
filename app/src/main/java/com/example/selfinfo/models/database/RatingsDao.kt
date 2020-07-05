package com.example.selfinfo.models.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RatingsDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRatings(ratingsModel: RatingsModel)

    @Query("SELECT * FROM $RATING_TABLE_NAME INNER JOIN $QUOTES_TABLE_NAME ON $QUOTES_TABLE_NAME.$QUOTES_ID = $RATING_TABLE_NAME.qid INNER JOIN $USERS_TABLE_NAME ON $USERS_TABLE_NAME.$USERS_ID = $RATING_TABLE_NAME.uid WHERE $USERS_TABLE_NAME.$USERS_ID =:userId and $QUOTES_TABLE_NAME.$QUOTES_ID =:quotesId")
    suspend fun getQuotesRating(userId : Int ,quotesId : Int?):List<RatingsModel>

}