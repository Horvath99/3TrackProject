package ro.sapientia.android_11eloadas.model

import androidx.annotation.Nullable

data class MyTaskItem(
    var ID:Int,
    var title:String,
    var description: String,
    var created_time: Long,
    var created_by_user_ID: Int,
    @Nullable
    var asigned_to_user_ID: Int?,
    @Nullable
    var priority: Int?,
    @Nullable
    var deadline: Long?,
    var department_ID: Int,
    @Nullable
    var status: Int?,
    @Nullable
    var progress: Int?,
)
