package com.example.selfinfo.modules.userProfile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.selfinfo.models.database.Databases
import com.example.selfinfo.models.database.UsersModel
import com.example.selfinfo.modules.commonRepository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel (application: Application): AndroidViewModel(application){

    private val repository : UserRepository
    val obsUserResponse = MutableLiveData<UsersModel?>()


    init {
        val userDao = Databases.getDatabase(application)?.usersDao()
        repository = UserRepository(userDao!!)
    }

    fun getUserData(id : Int){
        viewModelScope.launch (Dispatchers.IO){
            obsUserResponse.postValue(repository.getUserData(id))
        }
    }

    fun updateUserData(usersModel: UsersModel){
        viewModelScope.launch (Dispatchers.IO){
            repository.updateUserData(usersModel)
        }
    }
}