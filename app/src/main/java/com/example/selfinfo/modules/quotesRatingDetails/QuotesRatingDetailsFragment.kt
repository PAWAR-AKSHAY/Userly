package com.example.selfinfo.modules.quotesRatingDetails


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.example.selfinfo.R
import kotlinx.android.synthetic.main.quotes_rating_details_fragment.*

/**
 * A simple [Fragment] subclass.
 */
class QuotesRatingDetailsFragment : Fragment() {

    private lateinit var viewModel : QuotesRatingDetailsViewModel
    private val args: QuotesRatingDetailsFragmentArgs by navArgs()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.quotes_rating_details_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (toolbarQuotesRatings as Toolbar?)?.setupWithNavController(findNavController())

        viewModel = ViewModelProviders.of(this).get(QuotesRatingDetailsViewModel::class.java)

        val userId = args.userId
        val quoteId = args.quotesModel.quotesId
        txtQuotesAuthorDetails.text = args.quotesModel.quotesAuthor
        txtQuotesMessageDetails.text = args.quotesModel.quotesMessage
        viewModel.getQuotesRating(userId,quoteId)

        viewModel.obsQuotesRatingResponse.observe(this, Observer {
            if (it != null) {
                quotesRatingBar.rating = it.ratings!!
                txtQuotesCommentDetails.text = it.comments
            }
        })

        super.onViewCreated(view, savedInstanceState)
    }


}
