package com.example.selfinfo.modules.commonRepository

import androidx.lifecycle.LiveData
import com.example.selfinfo.models.database.UsersDao
import com.example.selfinfo.models.database.UsersModel

class UserRepository (private val usersDao: UsersDao){
    suspend fun insert(usersModel: UsersModel) {
        usersDao.addUser(usersModel)
    }

    suspend fun validateLogin(email: String, password: String):UsersModel? = usersDao.validateLogin(email,password).firstOrNull()

    suspend fun getUserData(id :Int): UsersModel? = usersDao.getUserData(id).firstOrNull()

    suspend fun updateUserData(usersModel: UsersModel){
        usersDao.updateUserData(usersModel)
    }

    fun getUser(id : Int): LiveData<UsersModel> = usersDao.getUser(id)

    fun getAllUsers() : LiveData<List<UsersModel>> = usersDao.getAllUsers()

}