package ro.sapientia.android_11eloadas.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text
import ro.sapientia.android_11eloadas.R
import ro.sapientia.android_11eloadas.model.Department
import ro.sapientia.android_11eloadas.model.MyTaskItem
import ro.sapientia.android_11eloadas.model.User
import ro.sapientia.android_11eloadas.repository.TrackerRepository
import ro.sapientia.android_11eloadas.viewmodel.DepartmentsViewModel
import ro.sapientia.android_11eloadas.viewmodel.DepartmentsViewModelFactory
import ro.sapientia.android_11eloadas.viewmodel.UserListViewModel
import ro.sapientia.android_11eloadas.viewmodel.UserListViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

class MyTaskAdapter(private val list : List<MyTaskItem>,private val listUsers : List<User>,private val listDepartment : List<Department>) : RecyclerView.Adapter<MyTaskAdapter.MyTaskViewHolder>() {
    class MyTaskViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var id : Int = 0
        val projectType : TextView = itemView.findViewById(R.id.projectType)
        var taskName : TextView = itemView.findViewById(R.id.title)
        var assigne: TextView = itemView.findViewById(R.id.assigneeName)
        var priority: TextView = itemView.findViewById(R.id.priorityConstant)
        var deadline: TextView = itemView.findViewById(R.id.deadlineDate)
        var content: TextView = itemView.findViewById(R.id.content)
        var assignedByAndDate : TextView = itemView.findViewById(R.id.assignedByAndDate)
        var moreButton: ImageButton = itemView.findViewById(R.id.moreButton)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyTaskViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.my_task_item,parent,false)
        return MyTaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyTaskViewHolder, position: Int) {

        val currentItem = list[position]
        holder.id = list[position].ID
        val type = listDepartment.find { it.ID == currentItem.department_ID }
        holder.projectType.text=type?.name
        holder.taskName.text=currentItem.title
        val createdUser = listUsers.find { it.ID == currentItem.created_by_user_ID }
        val assigne = listUsers.find { it.ID == currentItem.asigned_to_user_ID }
        holder.assigne.text=assigne?.first_name + " " + assigne?.last_name
        holder.priority.text=currentItem.priority.toString()
        holder.deadline.text=longToDateDeadline(currentItem.deadline!!)
        holder.content.text=currentItem.description
        holder.assignedByAndDate.text = createdUser?.first_name+" "+createdUser?.last_name + " " + longToDate(currentItem.created_time)
        holder
        val bundle = bundleOf("position" to position)
        holder.moreButton.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_myTasksFragment_to_taskDescriptionFragment,bundle)
        )

    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun longToDate(date: Long): String {
        val formatter = SimpleDateFormat("hh:mm a")
        val formattedDate = formatter.format(date)
        return formattedDate
    }

    fun longToDateDeadline(date: Long): String {
        val formatter = SimpleDateFormat("MM-dd-yyyy")
        val formattedDate = formatter.format(date)
        return formattedDate
    }


}