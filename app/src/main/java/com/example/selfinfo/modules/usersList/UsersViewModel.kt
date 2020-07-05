package com.example.selfinfo.modules.usersList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.selfinfo.models.database.Databases
import com.example.selfinfo.models.database.UsersModel
import com.example.selfinfo.modules.commonRepository.UserRepository

class UsersViewModel(application: Application) : AndroidViewModel(application){

    private val repository : UserRepository

    init {
        val userDao = Databases.getDatabase(application)?.usersDao()
        repository = UserRepository(userDao!!)
    }

    fun getAllUsers() : LiveData<List<UsersModel>>{
        return repository.getAllUsers()
    }
}
