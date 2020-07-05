package com.example.selfinfo.modules.quotesList


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.selfinfo.R
import kotlinx.android.synthetic.main.add_quotes_dialog.view.*
import kotlinx.android.synthetic.main.add_quotes_dialog.view.etxtQuotesAuthor
import kotlinx.android.synthetic.main.quotes_fragment.*

/**
 * A simple [Fragment] subclass.
 */
class QuotesFragment : Fragment() {

    private lateinit var viewModel: QuotesViewModel
    private var adapter: QuotesAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.quotes_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (toolbarQuotesDetails as Toolbar?)?.setupWithNavController(findNavController())
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(QuotesViewModel::class.java)
        fabAddQuotes.setOnClickListener {
            openDialog(context)
        }

        viewModel.getAllQuotes().observe(this, Observer {
            if(!it.isNullOrEmpty()){
                recQuoteDetails.adapter = QuotesAdapter(it) { quote -> viewModel.deleteQuote(quote) }
            }
        })

        recQuoteDetails.layoutManager = LinearLayoutManager(context)
        recQuoteDetails.adapter = adapter

    }

    private fun openDialog(context: Context?) {
       val dialogView = layoutInflater.inflate(R.layout.add_quotes_dialog, null)
       val builder  = AlertDialog.Builder(context!!)
           .setView(dialogView)
           .setCancelable(false)


       val alertDialog = builder.show()

       dialogView.btnAddQuotes.setOnClickListener {
           val message  = dialogView.etxtQuotesMessage.text.toString().trim()
           val author = dialogView.etxtQuotesAuthor.text.toString().trim()
           viewModel.insertQuotes(message,author)
           alertDialog.dismiss()
       }
       dialogView.btnCancel.setOnClickListener { alertDialog.dismiss() }

    }

}


