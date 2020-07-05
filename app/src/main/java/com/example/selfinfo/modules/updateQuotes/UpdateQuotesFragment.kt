package com.example.selfinfo.modules.updateQuotes


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.example.selfinfo.R
import com.example.selfinfo.models.database.QuotesModel
import kotlinx.android.synthetic.main.update_quotes_fragment.*

/**
 * A simple [Fragment] subclass.
 */
class UpdateQuotesFragment : Fragment() {

    private val args : UpdateQuotesFragmentArgs by navArgs()
    private lateinit var viewModel : UpdateQuotesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.update_quotes_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (toolbarUpdateQuote as Toolbar?)?.setupWithNavController(findNavController())
        viewModel = ViewModelProviders.of(this).get(UpdateQuotesViewModel::class.java)

        etxtQuotesMessage.setText(args.quoteModel.quotesMessage)
        etxtQuotesAuthor.setText(args.quoteModel.quotesAuthor)
        super.onViewCreated(view, savedInstanceState)

        btnUpdate.setOnClickListener { updateQuote() }
    }

    private fun updateQuote() {
        val quotesModel = QuotesModel(args.quoteModel.quotesId,
            etxtQuotesMessage.text.toString(),
            etxtQuotesAuthor.text.toString())
        viewModel.updateQuote(quotesModel)
        Toast.makeText(context,"Quotes Updated", Toast.LENGTH_SHORT).show()
    }


}
