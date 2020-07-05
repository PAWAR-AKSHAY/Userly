package com.example.selfinfo.modules.login

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.selfinfo.models.database.Databases
import com.example.selfinfo.models.database.UsersModel
import com.example.selfinfo.modules.commonRepository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository
    val obsButtonEnabled = MutableLiveData<Boolean>()
    val obsLoginResponse = MutableLiveData<UsersModel?>()

    var email: String = ""
        set(value) {
            field = value
            obsButtonEnabled.postValue(!email.isBlank() && Pattern.matches(Patterns.EMAIL_ADDRESS.toString(),email) && !password.isBlank())
        }

    var password: String = ""
        set(value) {
            field = value
            obsButtonEnabled.postValue(!email.isBlank() && Pattern.matches(Patterns.EMAIL_ADDRESS.toString(),email) && !password.isBlank())
        }

    init {
        val userDao = Databases.getDatabase(application)?.usersDao()
        repository = UserRepository(userDao!!)
    }


    fun insert(usersModel: UsersModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(usersModel)
        }
    }

    fun checkLogin(){
        viewModelScope.launch {
            obsLoginResponse.postValue(repository.validateLogin(email,password))
        }
    }
}