package com.example.selfinfo.models.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface QuotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuotes(quotesModel: QuotesModel)

    @Update
    suspend fun updateQuotes(quotesModel: QuotesModel)

    @Delete
    suspend fun deleteQuote(quotesModel: QuotesModel)

    @Query("SELECT * FROM $QUOTES_TABLE_NAME")
    fun getAllQuotes() : LiveData<List<QuotesModel>>

    @Transaction
    @Query("SELECT * FROM $QUOTES_TABLE_NAME")
    fun getQuotesWithUsers() : List<QuotesWithUsers>

    @Query("SELECT * FROM $QUOTES_TABLE_NAME INNER JOIN $RATING_TABLE_NAME ON $RATING_TABLE_NAME.qid = $QUOTES_TABLE_NAME.$QUOTES_ID INNER JOIN $USERS_TABLE_NAME ON $USERS_TABLE_NAME.$USERS_ID = $RATING_TABLE_NAME.uid WHERE $USERS_TABLE_NAME.$USERS_ID =:userId ")
    fun getUserQuotes(userId: Int?):LiveData<List<QuotesModel>>


}