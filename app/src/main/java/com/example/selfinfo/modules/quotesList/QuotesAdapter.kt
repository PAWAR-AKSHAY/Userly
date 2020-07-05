package com.example.selfinfo.modules.quotesList

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.selfinfo.R
import com.example.selfinfo.models.database.QuotesModel
import kotlinx.android.synthetic.main.quotes_list.view.*

class QuotesAdapter(private val quotesList: List<QuotesModel>, private val callback: (QuotesModel) -> Unit) : RecyclerView.Adapter<QuotesAdapter.ViewHolder>() {

    private var deletingIndex: Int? = null
        set(value) {
            val old = field
            field = value
            if (old != null)
                notifyItemChanged(old)
            if (value != null)
                notifyItemChanged(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.quotes_list, parent, false))

    override fun getItemCount(): Int = quotesList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.itemView) {

            imgDeleteQuote.visibility = if(position == deletingIndex) VISIBLE else GONE

            quotesList[position].apply {
                txtQuotesMessage.text = quotesMessage
                txtQuotesAuthor.text = "- $quotesAuthor"
            }

            setOnClickListener {
                val directions =
                    QuotesFragmentDirections.actionQuotesFragmentToUpdateQuotesFragment(quotesList[position])
                findNavController().navigate(directions)
            }

            setOnLongClickListener {
                deletingIndex = position
                return@setOnLongClickListener true
            }

            imgDeleteQuote.setOnClickListener { callback(quotesList[position]) }
        }

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}