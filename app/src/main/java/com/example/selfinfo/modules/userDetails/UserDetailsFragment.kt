package com.example.selfinfo.modules.userDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.net.toUri
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.selfinfo.R
import kotlinx.android.synthetic.main.user_details_fragment.*


class UserDetailsFragment : Fragment() {

    private lateinit var viewModel : UserDetailsViewModel
    private val args : UserDetailsFragmentArgs by navArgs()
    private var adapter: UserDetailsAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.user_details_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (toolbarDetails as Toolbar?)?.setupWithNavController(findNavController())

        imgUserProfile.setImageURI(args.userModel.usersProfileImage?.toUri())
        txtUserName.text = args.userModel.usersFullName
        txtUserEmail.text = args.userModel.usersEmail
        txtUserContact.text = args.userModel.usersContact
        val userId = args.userModel.usersId
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(UserDetailsViewModel::class.java)
        viewModel.getUserQuotes(userId).observe(this, Observer {
            if(!it.isNullOrEmpty())
                recUserQuotes.adapter = UserDetailsAdapter(userId, it)
            else
                cardError.visibility = View.VISIBLE

            if(recUserQuotes.adapter?.itemCount == null)
                txtQuotesCount.text = "0"
            else
                txtQuotesCount.text = recUserQuotes.adapter?.itemCount.toString()
        })

        recUserQuotes.layoutManager = LinearLayoutManager(context)
        recUserQuotes.adapter = adapter
    }
}
