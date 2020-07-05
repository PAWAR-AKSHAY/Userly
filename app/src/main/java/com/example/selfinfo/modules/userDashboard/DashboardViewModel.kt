package com.example.selfinfo.modules.userDashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.selfinfo.models.database.*
import com.example.selfinfo.modules.commonRepository.QuoteRepository
import com.example.selfinfo.modules.commonRepository.UserRepository

class DashboardViewModel (application: Application) : AndroidViewModel(application){

    private val repositoryUsers: UserRepository
    private val repositoryQuotes : QuoteRepository
    //val obsFeedbackResponse = MutableLiveData<LiveData<List<FeedbackModel>>>()
    val obsUserResponse = MutableLiveData<UsersModel?>()


    init {
        val userDao = Databases.getDatabase(application)?.usersDao()
        repositoryUsers = UserRepository(userDao!!)

        val quotesDao = Databases.getDatabase(application)?.quotesDao()
        repositoryQuotes = QuoteRepository(quotesDao!!)
    }

    fun getUser(id : Int) : LiveData<UsersModel> {
            return repositoryUsers.getUser(id)
    }

    fun getAllQuotes() : LiveData<List<QuotesModel>>{
        return repositoryQuotes.getAllQuotes()
    }

//    fun getUserFeedback(id : Int) : LiveData<List<FeedbackModel>>{
//        //return viewModelScope.launch(Dispatchers.IO){
//            return repository.getUserFeedback(id)
//            //obsFeedbackResponse.postValue(repository.getUserFeedback(id))
//        //}
//    }

//    fun getUserData(id : Int){
//        viewModelScope.launch(Dispatchers.IO){
//            obsUserResponse.postValue(repository.getUserData(id))
//        }
//    }
}