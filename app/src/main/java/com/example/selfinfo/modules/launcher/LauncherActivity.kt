package com.example.selfinfo.modules.launcher


import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import com.example.selfinfo.R
import com.example.selfinfo.models.prefs.SharedPreferenceManager
import com.example.selfinfo.modules.login.ADMIN
import com.example.selfinfo.modules.adminDashboard.AdminDashboardActivity
import com.example.selfinfo.modules.userDashboard.DashboardActivity
import com.example.selfinfo.modules.login.LoginActivity
import kotlinx.android.synthetic.main.launcher_activity.*

const val  SPLASH_TIME = 3000L
class LauncherActivity : AppCompatActivity() {

    private lateinit var context: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        setContentView(R.layout.launcher_activity)

        context = this
        val topAnimation = AnimationUtils.loadAnimation(this,
            R.anim.top_animation
        )
        val bottomAnimation = AnimationUtils.loadAnimation(this,
            R.anim.bottom_animation
        )

        imgLogo.animation = topAnimation
        txtLogo.animation = bottomAnimation

        if(SharedPreferenceManager.getUserId(context) > 0){
            Handler().postDelayed({
                startActivity(Intent(context, DashboardActivity::class.java))
                finish()
            }, SPLASH_TIME)
        }else if(SharedPreferenceManager.getUserEmail(context) == ADMIN){
            Handler().postDelayed({
                startActivity(Intent(context, AdminDashboardActivity::class.java))
                finish()
            }, SPLASH_TIME)
        } else{
            Handler().postDelayed ({
                startActivity(Intent(context, LoginActivity::class.java))
                finish()
            }, SPLASH_TIME)
        }
    }
}
