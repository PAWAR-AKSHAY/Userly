package com.example.selfinfo.modules.quotesList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.selfinfo.models.database.Databases
import com.example.selfinfo.models.database.QuotesModel
import com.example.selfinfo.modules.commonRepository.QuoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuotesViewModel (application: Application) : AndroidViewModel(application){
    private val repository : QuoteRepository

    init {
        val quotesDao = Databases.getDatabase(application)?.quotesDao()
        repository = QuoteRepository(quotesDao!!)
    }

    fun insertQuotes(message : String,author : String){
        viewModelScope.launch (Dispatchers.IO) {
            repository.insertQuotes(quotesModel = QuotesModel(null,message,author))
        }
    }

    fun getAllQuotes() : LiveData<List<QuotesModel>>{
        return repository.getAllQuotes()
    }

    fun deleteQuote(quotesModel: QuotesModel){
        viewModelScope.launch (Dispatchers.IO){
            repository.deleteQuote(quotesModel)
        }
    }
}