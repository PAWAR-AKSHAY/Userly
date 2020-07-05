package com.example.selfinfo.modules.ratings

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.selfinfo.R
import com.example.selfinfo.models.prefs.SharedPreferenceManager
import com.example.selfinfo.models.database.QuotesModel
import com.example.selfinfo.utils.onTextChange
import kotlinx.android.synthetic.main.ratings_activity.*

class RatingsActivity : AppCompatActivity() {

    private lateinit var context : Context
    private lateinit var viewModel : RatingsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ratings_activity)
        context = this
        viewModel = ViewModelProviders.of(this).get(RatingsViewModel::class.java)

        setSupportActionBar(toolbarRatings as Toolbar?)
        supportActionBar?.title = getString(R.string.ratings)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        (toolbarRatings as Toolbar?)?.setNavigationOnClickListener {
            onBackPressed()
        }
        etxtQuotesComment.onTextChange { viewModel.comment = it.trim() }
        viewModel.obsButtonEnabled.observe(this, Observer { btnSend.isEnabled = it })

        val quotesModel = intent.getParcelableExtra<QuotesModel>("quotes")
        txtQuotesAuthorDetails.text = quotesModel.quotesAuthor
        txtQuotesMessageDetails.text = quotesModel.quotesMessage
        val quotesId = quotesModel.quotesId
        val userId = SharedPreferenceManager.getUserId(context)

        btnSend.setOnClickListener {
            val ratings = quotesRatingBar.rating
            viewModel.insertRatings(userId,quotesId,ratings)
            Toast.makeText(context,"Ratings send Successfully", Toast.LENGTH_SHORT).show()
            etxtQuotesComment.text = null
            quotesRatingBar.rating = 0.0F
        }
    }
}
