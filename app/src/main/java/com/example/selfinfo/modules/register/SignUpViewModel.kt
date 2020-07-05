package com.example.selfinfo.modules.register

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

class SignUpViewModel (application: Application) : AndroidViewModel(application){

    private val repository : UserRepository
    val obsButtonEnabled = MutableLiveData<Boolean>()
    var fullName : String = ""
        set(value) {
            field = value
            obsButtonEnabled.postValue(!fullName.isBlank() && !email.isBlank() &&
                    Pattern.matches(Patterns.EMAIL_ADDRESS.toString(),email) && !password.isBlank() && !contact.isBlank())
        }

    var email : String = ""
        set(value) {
            field = value
            obsButtonEnabled.postValue(!fullName.isBlank() && !email.isBlank() &&
                    Pattern.matches(Patterns.EMAIL_ADDRESS.toString(),email) && !password.isBlank() && !contact.isBlank())
        }

    var password : String = ""
        set(value) {
            field = value
            obsButtonEnabled.postValue(!fullName.isBlank() && !email.isBlank() &&
                    Pattern.matches(Patterns.EMAIL_ADDRESS.toString(),email) && !password.isBlank() && !contact.isBlank())
        }

    var contact : String = ""
        set(value) {
            field = value
            obsButtonEnabled.postValue(!fullName.isBlank() && !email.isBlank() &&
                    Pattern.matches(Patterns.EMAIL_ADDRESS.toString(),email) && !password.isBlank() && !contact.isBlank())
        }

    init {
        val userDao = Databases.getDatabase(application)?.usersDao()
        repository = UserRepository(userDao!!)
    }

    fun insertData() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(usersModel = UsersModel(null,fullName, email, password, contact,null))
        }
    }
}