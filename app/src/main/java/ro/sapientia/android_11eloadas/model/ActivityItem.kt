package ro.sapientia.android_11eloadas.model

import androidx.annotation.Nullable
import com.squareup.moshi.JsonClass

@JsonClass (generateAdapter = true)
data class ActivityItem(
    var ID: Int,
    var type: Int,
    var created_by_user_id: Int,
    var sub_type:Int,
    var created_time:Long,
    var sub_ID:Int,
    var sub_user_ID:Int,
    @Nullable
    var note:String?,
    @Nullable
    var progress:Int?,
    )