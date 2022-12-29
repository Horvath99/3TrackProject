package ro.sapientia.android_11eloadas.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ro.sapientia.android_11eloadas.R
import ro.sapientia.android_11eloadas.model.Department
import ro.sapientia.android_11eloadas.model.User

class UserAdapter(private val list : List<User>,private val departmentName : String)
    : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
        class UserViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
            var userName : TextView = itemView.findViewById(R.id.userName)
            var userGroup : TextView = itemView.findViewById(R.id.userGroup)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.user_item,parent,false)
        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentItem = list[position]

        holder.userName.text = currentItem.last_name + " " + currentItem.first_name
        holder.userGroup.text = departmentName
    }

    override fun getItemCount(): Int {
        return list.size
    }
}