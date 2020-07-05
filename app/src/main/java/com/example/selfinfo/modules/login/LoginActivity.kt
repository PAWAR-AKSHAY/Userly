package com.example.selfinfo.modules.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.selfinfo.R
import com.example.selfinfo.models.prefs.SharedPreferenceManager
import com.example.selfinfo.modules.adminDashboard.AdminDashboardActivity
import com.example.selfinfo.modules.userDashboard.DashboardActivity
import com.example.selfinfo.modules.register.SignUpActivity
import com.example.selfinfo.utils.onTextChange
import kotlinx.android.synthetic.main.login_activity.*
const val ADMIN = "admin@gmail.com"
const val PASSWORD = "123123"
class LoginActivity : AppCompatActivity() {

    private lateinit var context: Context
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
//        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        setContentView(R.layout.login_activity)
        context = this
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        etxtEmail.onTextChange { viewModel.email = it.trim() }
        etxtPassword.onTextChange { viewModel.password = it.trim() }

        viewModel.obsButtonEnabled.observe(this, Observer { btnLogin.isEnabled = it })

        viewModel.obsLoginResponse.observe(this, Observer {
            if (it == null)
                Toast.makeText(context, "Invalid Credentials", LENGTH_SHORT).show()
            else {
                val id = it.usersId
                startActivity(Intent(context, DashboardActivity::class.java))
                finish()
                if (id != null) {
                    SharedPreferenceManager.setUserId(context,id)
                }
            }

        })

        txtSignUp.setOnClickListener {
            startActivity(Intent(context, SignUpActivity::class.java))
        }

        btnLogin.setOnClickListener {
            if(viewModel.email == ADMIN && viewModel.password == PASSWORD){
                startActivity(Intent(context, AdminDashboardActivity::class.java))
                finish()
                SharedPreferenceManager.setUserEmail(context,viewModel.email)
            }
            else{
                viewModel.checkLogin()
            }

        }

    }

//    private fun validateData() {
//        var isValidate = true
//
//        val ic_email = etxtEmail.text.toString().trim()
//        val password = etxtPassword.text.toString().trim()
//
//        when {
//            ic_email.isEmpty() -> {
//                txtEmail.error = getString(R.string.error_empty_field)
//                isValidate = false
//            }
//
//            !Patterns.EMAIL_ADDRESS.matcher(ic_email).matches() -> {
//                txtEmail.error = getString(R.string.error_email)
//                isValidate = false
//            }
//            else -> txtEmail.error = null
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
//            else -> txtPassword.error = null
//        }
//
//
//        if (isValidate) {
//            //dbUser =  UserDatabase.getUserDatabase(context)!!
//            viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
//            viewModel.getAllUsers(ic_email, password)
//            startActivity(Intent(context, DashboardActivity::class.java))
//        } else {
//            Toast.makeText(context, getString(R.string.error_login), Toast.LENGTH_SHORT).show()
//        }
//    }
}
