package ro.sapientia.android_11eloadas.model


import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class CreateTaskRequest(
    var title:String,
    var description:String,
    var assignedToUserId:Int,
    var priority: Int,
    var deadline: Long,
    var departmentId: Int,
    var status:Int
)
