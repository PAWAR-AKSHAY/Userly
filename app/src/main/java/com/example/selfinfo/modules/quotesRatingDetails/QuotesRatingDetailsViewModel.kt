package com.example.selfinfo.modules.quotesRatingDetails

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.selfinfo.models.database.Databases
import com.example.selfinfo.models.database.RatingsModel
import com.example.selfinfo.modules.commonRepository.RatingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuotesRatingDetailsViewModel (application: Application) : AndroidViewModel(application){

    private val repository : RatingsRepository
    val obsQuotesRatingResponse = MutableLiveData<RatingsModel?>()

    init {
        val ratingsDao = Databases.getDatabase(application)?.ratingsDao()
        repository = RatingsRepository(ratingsDao!!)
    }

    fun getQuotesRating(userId: Int, quoteId: Int?){
        viewModelScope.launch (Dispatchers.IO){
            obsQuotesRatingResponse.postValue(repository.getQuotesRating(userId,quoteId))
        }
    }
}