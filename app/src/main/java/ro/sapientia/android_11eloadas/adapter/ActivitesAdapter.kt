package ro.sapientia.android_11eloadas.adapter

import android.text.method.TextKeyListener.clear
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ro.sapientia.android_11eloadas.R
import ro.sapientia.android_11eloadas.model.ActivityItem
import ro.sapientia.android_11eloadas.util.ActivityDataModel
import java.lang.IllegalArgumentException
import androidx.appcompat.widget.AppCompatTextView
import de.hdodenhof.circleimageview.CircleImageView
import java.util.Collections.addAll


class ActivitesAdapter: RecyclerView.Adapter<ActivitesAdapter.ActivitiesAdapterViewHolder>(){

    private val data = mutableListOf<ActivityDataModel>()

    class ActivitiesAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private fun bindGroup(item: ActivityDataModel.Group){
            itemView.findViewById<AppCompatTextView>(R.id.groupAssignedByName)?.text = item.assignerName
            itemView.findViewById<AppCompatTextView>(R.id.groupAssignedByDate)?.text= item.date
            itemView.findViewById<AppCompatTextView>(R.id.groupStaticText)?.text = item.text

        }
        private fun bindTask(item: ActivityDataModel.Task){
            itemView.findViewById<AppCompatTextView>(R.id.assignedByName)?.text= item.assignerName
            itemView.findViewById<AppCompatTextView>(R.id.assignedByDate)?.text= item.date
            itemView.findViewById<AppCompatTextView>(R.id.staticText)?.text = item.text
            itemView.findViewById<AppCompatTextView>(R.id.subTaskId)?.text = item.taskId
            itemView.findViewById<AppCompatTextView>(R.id.subTaskTitle)?.text = item.taskTitle
            itemView.findViewById<AppCompatTextView>(R.id.subTaskAssignedByAndDate)?.text= item.taskAssignerAndDate
            itemView.findViewById<AppCompatTextView>(R.id.subTaskAssignee)?.text= item.assigne

        }

        fun bind(activityDataModel: ActivityDataModel){
            when(activityDataModel){
                is ActivityDataModel.Group ->bindGroup(activityDataModel)
                is ActivityDataModel.Task -> bindTask(activityDataModel)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivitiesAdapterViewHolder {
       val layout = when(viewType){
           TYPE_GROUP -> R.layout.activites_group_item
           TYPE_TASK -> R.layout.activities_task_item
           else -> throw IllegalArgumentException("Invalid type")
       }
        val view = LayoutInflater.from(parent.context).inflate(layout,parent,false)
        return ActivitiesAdapterViewHolder(view)
    }


    override fun onBindViewHolder(holder: ActivitiesAdapterViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size

    }

    override fun getItemViewType(position: Int): Int {
        return when(data[position]){
            is ActivityDataModel.Task -> TYPE_TASK
            else -> TYPE_GROUP
        }
    }
    fun setData(data1 : List<ActivityDataModel>){
        data.apply {
            clear()
            addAll(data1)
        }
    }

    companion object{
        private const val TYPE_GROUP = 0
        private const val TYPE_TASK = 1
    }
}