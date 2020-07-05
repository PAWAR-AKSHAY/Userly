package com.example.selfinfo.modules.userDetails

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.selfinfo.models.database.Databases
import com.example.selfinfo.models.database.QuotesModel
import com.example.selfinfo.modules.commonRepository.QuoteRepository

class UserDetailsViewModel (application: Application) : AndroidViewModel(application){

    private val repository : QuoteRepository

    init {
        val quotesDao = Databases.getDatabase(application)?.quotesDao()
        repository = QuoteRepository(quotesDao!!)
    }

    fun getUserQuotes(userId: Int?) : LiveData<List<QuotesModel>>{
        return repository.getUserQuotes(userId)
    }
}