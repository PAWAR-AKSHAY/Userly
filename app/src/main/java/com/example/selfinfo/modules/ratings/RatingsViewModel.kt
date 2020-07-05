package com.example.selfinfo.modules.ratings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.selfinfo.models.database.Databases
import com.example.selfinfo.models.database.RatingsModel
import com.example.selfinfo.modules.commonRepository.RatingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RatingsViewModel (application: Application) : AndroidViewModel(application){

    private val repository : RatingsRepository
    val obsButtonEnabled =  MutableLiveData<Boolean>()

    var comment : String = ""
    set(value) {
        field = value
        obsButtonEnabled.postValue(!comment.isBlank())
    }

    init {
        val ratingsDao = Databases.getDatabase(application)?.ratingsDao()
        repository = RatingsRepository(ratingsDao!!)
    }


    fun insertRatings(userId :Int , quotesId: Int?, ratings: Float){
        viewModelScope.launch (Dispatchers.IO){
            repository.insertRatings(ratingsModel = RatingsModel(userId, quotesId!!,comment,ratings,userId,quotesId))
        }
    }
}