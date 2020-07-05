package com.example.selfinfo.modules.userDashboard

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.net.toUri
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.selfinfo.R
import com.example.selfinfo.models.prefs.SharedPreferenceManager
import com.example.selfinfo.modules.userProfile.ProfileActivity
import com.example.selfinfo.modules.login.LoginActivity
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.dashboard_activity.*
import kotlinx.android.synthetic.main.dashboard_content.*
import kotlinx.android.synthetic.main.header.view.*

class DashboardActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var context: Context
    private lateinit var viewModel: DashboardViewModel
    private var adapter : DashboardAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard_activity)
        context = this
        viewModel = ViewModelProviders.of(this).get(DashboardViewModel::class.java)

        setSupportActionBar(toolbarDashboard as Toolbar?)
        supportActionBar?.title = getString(R.string.dashboard)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val toggle = ActionBarDrawerToggle(this,drawer_layout, toolbarDashboard as Toolbar?,R.string.navigagtion_drawer_open,R.string.navigagtion_drawer_close)

        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        val id = SharedPreferenceManager.getUserId(context)

//        viewModel.getUserFeedback(id).observe(this, Observer {
//            if (!it.isNullOrEmpty())
//                recFeedback.adapter = FeedbackAdapter(it)
//        })

        viewModel.getUser(id).observe(this, Observer {
            if(it != null ){
                nav_menu.getHeaderView(0)?.apply {
                    txtName.text = it.usersFullName
                    txtEmail.text = it.usersEmail
                    imgProfile.setImageURI(it.usersProfileImage?.toUri())
                }
            }

        })

        nav_menu.setNavigationItemSelectedListener(this)
//        nav_menu.getHeaderView(0)?.apply {
//            txtName.text = intent.extras?.getString("name")
//            txtEmail.text = intent.extras?.getString("ic_email")
//        }

        viewModel.getAllQuotes().observe(this, Observer {
            if(!it.isNullOrEmpty()){
                recQuotes.adapter = DashboardAdapter(it)
            }
        })

        recQuotes.layoutManager = LinearLayoutManager(context)
        recQuotes.adapter = adapter
    }

    override fun onBackPressed() {
        if(drawer_layout.isDrawerOpen(GravityCompat.START)){
           drawer_layout.closeDrawer(GravityCompat.START)
        }else{
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_profile ->{
                startActivity(Intent(context, ProfileActivity::class.java))
            }

            R.id.menu_logout ->{
               exitDialog()
            }
//            R.id.menu_settings ->{
//                startActivity(Intent(context,SettingsActivity::class.java))
//            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun exitDialog() {
       val builder = AlertDialog.Builder(context)
           .setMessage(getString(R.string.logout_message))
           .setCancelable(false)
           .setPositiveButton(getString(R.string.ok)){ _,_->
               startActivity(Intent(context, LoginActivity::class.java))
               SharedPreferenceManager.clearSharedPreferences(context)
               finish()
           }
           .setNegativeButton(getString(R.string.cancel)){ dialog, _->
               dialog.cancel()
           }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    override fun onNavigateUp(): Boolean {
        onBackPressed()
        return super.onNavigateUp()
    }
}
