package ro.sapientia.android_11eloadas.util

import de.hdodenhof.circleimageview.CircleImageView

sealed class ActivityDataModel {
    data class Group(
        val assignerName : String,
        val date : String,
        val text: String,
        //val image: CircleImageView
    ) : ActivityDataModel()
    data class Task(
        val assignerName : String,
        val date : String,
       // val image: CircleImageView,
        val text: String,
        val taskId : String,
        val taskTitle: String,
        val taskAssignerAndDate: String,
        val assigne: String
    ) : ActivityDataModel()
}