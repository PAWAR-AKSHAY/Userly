package com.example.selfinfo.modules.usersList


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.selfinfo.R
import com.example.selfinfo.models.database.UsersModel
import kotlinx.android.synthetic.main.users_list.view.*

class UsersAdapter (private val userDetailsList : List<UsersModel?>): RecyclerView.Adapter<UsersAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.users_list, parent, false))


    override fun getItemCount(): Int =  userDetailsList.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.itemView){
            userDetailsList[position]?.apply{
                imgUserProfile.setImageURI(usersProfileImage?.toUri())
                txtUserName.text = usersFullName
                txtUserEmail.text = usersEmail
            }
            setOnClickListener {
//                val intent = Intent(holder.itemView.context,DetailsActivity::class.java)
//                intent.putExtra("user",userDetailsList[position])
//                holder.itemView.context.startActivity(intent)
                val directions = userDetailsList[position]?.let { it1 ->
                    UsersFragmentDirections.actionUsersFragmentToUserDetailsFragment(
                        it1
                    )
                }
                if (directions != null) {
                    findNavController().navigate(directions)
                }
            }
        }
    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view)

}