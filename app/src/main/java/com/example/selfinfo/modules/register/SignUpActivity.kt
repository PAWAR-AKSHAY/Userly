package com.example.selfinfo.modules.register

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.selfinfo.R
import com.example.selfinfo.modules.login.LoginActivity
import com.example.selfinfo.utils.onTextChange
import kotlinx.android.synthetic.main.sign_up_activity.*

class SignUpActivity : AppCompatActivity() {

    private lateinit var context : Context
    private lateinit var viewModel: SignUpViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
//        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        setContentView(R.layout.sign_up_activity)
        context = this
        viewModel = ViewModelProviders.of(this).get(SignUpViewModel::class.java)

        etxtFullName.onTextChange { viewModel.fullName = it.trim() }
        etxtEmail.onTextChange { viewModel.email = it.trim() }
        etxtPassword.onTextChange { viewModel.password = it.trim() }
        etxtContactNumber.onTextChange { viewModel.contact = it.trim() }

        viewModel.obsButtonEnabled.observe(this, Observer { btnSignUP.isEnabled = it } )

        txtLogIn.setOnClickListener {
            startActivity(Intent(context, LoginActivity::class.java))
        }

        btnSignUP.setOnClickListener {
            viewModel.insertData()
            Toast.makeText(context,"User Registered Successfully",Toast.LENGTH_SHORT).show()
            startActivity(Intent(context, LoginActivity::class.java))
            finish()
        }
    }

//    private fun validateData() {
//        var isValidate = true
//
//        val fullName = etxtFullName.text.toString().trim()
//        val ic_email = etxtEmail.text.toString().trim()
//        val password = etxtPassword.text.toString().trim()
//        val contact = etxtContactNumber.text.toString().trim()
//
//        when {
//            fullName.isEmpty() -> {
//                txtFullName.error = getString(R.string.error_empty_field)
//                isValidate = false
//            }
//            else -> {
//                txtFullName.error = null
//                txtFullName.isErrorEnabled = false
//            }
//        }
//
//        when {
//            ic_email.isEmpty() -> {
//                txtEmail.error = getString(R.string.error_empty_field)
//                isValidate = false
//            }
//            else -> if(!Patterns.EMAIL_ADDRESS.matcher(ic_email).matches()){
//                txtEmail.error = getString(R.string.error_email)
//                isValidate = false
//            } else{
//                txtEmail.error = null
//                txtEmail.isErrorEnabled = false
//            }
//        }
//
//        when {
//            password.isEmpty() -> {
//                txtPassword.error = getString(R.string.error_empty_field)
//                isValidate = false
//            }
//            password.length < 6 -> {
//                txtPassword.error = getString(R.string.error_password)
//                isValidate = false
//            }
//            else -> {
//                txtPassword.error = null
//                txtPassword.isErrorEnabled = false
//            }
//        }
//
//        when {
//            contact.isEmpty() -> {
//                txtContactNumber.error = getString(R.string.error_empty_field)
//                isValidate = false
//            }
//            contact.length != 10 -> {
//                txtContactNumber.error = getString(R.string.error_contact_length)
//                isValidate = false
//            }
//            else -> {
//                txtContactNumber.error = null
//                txtContactNumber.isErrorEnabled = false
//            }
//        }
//
//        if(isValidate){
//
//
//            val users = UserModel(fullName, ic_email, password, contact)
//            dbUser = UserDatabase.getUserDatabase(context)!!
//            loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
//            loginViewModel.insert(users)
//
//            startActivity(Intent(context, LoginActivity::class.java))
//
//                etxtFullName.text = null
//                etxtEmail.text = null
//                etxtPassword.text = null
//                etxtContactNumber.text = null
//
//        }
//    }
}
