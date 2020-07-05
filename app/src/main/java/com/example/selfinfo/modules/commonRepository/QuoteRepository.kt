package com.example.selfinfo.modules.commonRepository

import androidx.lifecycle.LiveData
import androidx.room.Insert
import com.example.selfinfo.models.database.QuotesDao
import com.example.selfinfo.models.database.QuotesModel

class QuoteRepository (private val quotesDao: QuotesDao){

    @Insert
    suspend fun insertQuotes(quotesModel: QuotesModel){
        quotesDao.insertQuotes(quotesModel)
    }

    suspend fun updateQuotes(quotesModel: QuotesModel){
        quotesDao.updateQuotes(quotesModel)
    }

    suspend fun deleteQuote(quotesModel: QuotesModel){
        quotesDao.deleteQuote(quotesModel)
    }
    fun getAllQuotes() : LiveData<List<QuotesModel>> = quotesDao.getAllQuotes()

    fun getUserQuotes(userId: Int?) :LiveData<List<QuotesModel>> = quotesDao.getUserQuotes(userId)

}