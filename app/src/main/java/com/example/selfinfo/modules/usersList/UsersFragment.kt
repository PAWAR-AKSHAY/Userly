package com.example.selfinfo.modules.usersList

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.selfinfo.R
import com.example.selfinfo.models.prefs.SharedPreferenceManager
import com.example.selfinfo.modules.login.LoginActivity
import kotlinx.android.synthetic.main.users_fragment.recUserDetails
import kotlinx.android.synthetic.main.users_fragment.toolbarUsersDetails

/**
 * A simple [Fragment] subclass.
 */
class UsersFragment : Fragment() {

    private lateinit var viewModel : UsersViewModel
    private var adapter : UsersAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.users_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        (toolbarUsersDetails as Toolbar?)?.setupWithNavController(findNavController())
        (toolbarUsersDetails as Toolbar?)?.inflateMenu(R.menu.logout_menu)
        (activity as AppCompatActivity).setSupportActionBar(toolbarUsersDetails as Toolbar?)
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(UsersViewModel::class.java)
        viewModel.getAllUsers().observe(this, Observer {
            if(!it.isNullOrEmpty()){
                recUserDetails.adapter = UsersAdapter(it)
            }
        })

        recUserDetails.layoutManager = LinearLayoutManager(context)
        recUserDetails.adapter = adapter

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.logout_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.action_logout){
            exitDialog()
            return false
        }
        return super.onOptionsItemSelected(item)
    }

    private fun exitDialog() {
        val builder = context?.let {
            AlertDialog.Builder(it)
                .setMessage(getString(R.string.logout_message))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.ok)){ _,_->
                    startActivity(Intent(context, LoginActivity::class.java))
                    SharedPreferenceManager.clearSharedPreferences(it)
                    activity?.finish()
                }
                .setNegativeButton(getString(R.string.cancel)){ dialog, _->
                    dialog.cancel()
                }
        }
        val alertDialog = builder?.create()
        alertDialog?.show()
    }
}
