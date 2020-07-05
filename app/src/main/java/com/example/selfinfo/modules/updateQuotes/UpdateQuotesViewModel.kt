package com.example.selfinfo.modules.updateQuotes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.selfinfo.models.database.Databases
import com.example.selfinfo.models.database.QuotesModel
import com.example.selfinfo.modules.commonRepository.QuoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UpdateQuotesViewModel (application: Application) : AndroidViewModel(application){

    private val repository : QuoteRepository

    init {
        val quotesDao = Databases.getDatabase(application)?.quotesDao()
        repository = QuoteRepository(quotesDao!!)
    }

    fun updateQuote(quotesModel: QuotesModel){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateQuotes(quotesModel)
        }
    }
}