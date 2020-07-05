package com.example.selfinfo.modules.adminDashboard

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.selfinfo.R
import kotlinx.android.synthetic.main.admin_dashboard_activity.*

class AdminDashboardActivity : AppCompatActivity() {

    private lateinit var context : Context
    private lateinit var navController : NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_dashboard_activity)
        context = this
        navController = findNavController(R.id.fragmentHost)

        bottomNav.setupWithNavController(navController)

        navController.addOnDestinationChangedListener{ _ ,destination ,_ ->
            if(destination.id == R.id.usersFragment || destination.id == R.id.quotesFragment)
                bottomNav.visibility = View.VISIBLE
            else
                bottomNav.visibility = View.GONE

        }

    }

    override fun onNavigateUp(): Boolean {
        return findNavController(R.id.fragmentHost).navigateUp()
    }
}
