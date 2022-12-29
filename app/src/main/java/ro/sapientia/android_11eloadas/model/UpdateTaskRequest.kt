package ro.sapientia.android_11eloadas.model

import androidx.annotation.Nullable
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class UpdateTaskRequest (
        var taskId: Int,
        var title: String,
        var description: String,
        var assignedToUserId: Int,
        @Nullable
        val priority : Int?,
        var dedline: Long,
        var departmentId:Int,
        @Nullable
        var status:Int?
        )