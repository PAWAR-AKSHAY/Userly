package com.example.selfinfo.modules.commonRepository

import com.example.selfinfo.models.database.RatingsDao
import com.example.selfinfo.models.database.RatingsModel

class RatingsRepository (private val ratingsDao: RatingsDao){

    suspend fun insertRatings(ratingsModel: RatingsModel){
            ratingsDao.insertRatings(ratingsModel)
    }

    suspend fun getQuotesRating(userId: Int, quoteId: Int?) : RatingsModel? = ratingsDao.getQuotesRating(userId,quoteId).firstOrNull()

}