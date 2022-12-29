package ro.sapientia.android_11eloadas.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import ro.sapientia.android_11eloadas.R
import ro.sapientia.android_11eloadas.model.Department

class GroupsAdapter(private val list: List<Department>)
    : RecyclerView.Adapter<GroupsAdapter.GroupsViewHolder>(){
        class GroupsViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
            var id : Int = 0
            val departmentName: TextView = itemView.findViewById(R.id.groupName)
            val button : ImageView = itemView.findViewById(R.id.groubsMoreButton)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.group_item,parent,false)
        return GroupsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GroupsViewHolder, position: Int) {
        val currentItem = list[position]
        holder.id = currentItem.ID
        holder.departmentName.text=currentItem.name

        val bundle = bundleOf("groupName" to currentItem.name)
        holder.button.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_myGroupsFragment_to_userListFragment,bundle)
        )

    }

    override fun getItemCount(): Int {
        return list.size
    }
}