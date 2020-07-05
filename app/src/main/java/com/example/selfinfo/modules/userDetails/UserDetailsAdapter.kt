package com.example.selfinfo.modules.userDetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.selfinfo.R
import com.example.selfinfo.models.database.QuotesModel
import kotlinx.android.synthetic.main.quotes_list.view.*

class UserDetailsAdapter (private val userId : Int?,private val quotesList : List<QuotesModel>) : RecyclerView.Adapter<UserDetailsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.quotes_list, parent, false)
        )

    override fun getItemCount(): Int =  quotesList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.itemView){
            quotesList[position].apply{
                txtQuotesMessage.text = quotesMessage
                txtQuotesAuthor.text = "- $quotesAuthor"
            }

            setOnClickListener {
                val directions = userId?.let { it1 ->
                    UserDetailsFragmentDirections.actionUserDetailsFragmentToQuotesRatingDetailsFragment(
                        quotesList[position],
                        it1
                    )
                }
                findNavController().navigate(directions!!)
            }


        }

    }

    class ViewHolder(view : View): RecyclerView.ViewHolder(view)
}