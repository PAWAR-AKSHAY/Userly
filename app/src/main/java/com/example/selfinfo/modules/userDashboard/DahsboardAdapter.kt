package com.example.selfinfo.modules.userDashboard

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.selfinfo.R
import com.example.selfinfo.models.database.QuotesModel
import com.example.selfinfo.modules.ratings.RatingsActivity
import kotlinx.android.synthetic.main.quotes_list.view.*

class DashboardAdapter(private val quotesList: List<QuotesModel?>) : RecyclerView.Adapter<DashboardAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.quotes_list, parent, false)
        )

    override fun getItemCount(): Int = quotesList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.itemView) {
            quotesList[position]?.apply {
                txtQuotesMessage.text = quotesMessage
                txtQuotesAuthor.text = "- $quotesAuthor"
            }
            setOnClickListener {
             val intent = Intent(holder.itemView.context, RatingsActivity::class.java)
                intent.putExtra("quotes",quotesList[position])
                holder.itemView.context.startActivity(intent)
            }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}