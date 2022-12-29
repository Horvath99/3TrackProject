package ro.sapientia.android_11eloadas.model

import androidx.annotation.Nullable
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    var ID: Int,
    var last_name: String,
    var first_name: String,
    var email: String,
    var type: Int,
    @Nullable
    var location: String?,
    @Nullable
    var phone_number: String?,
    var department_id: Int,
    @Nullable
    var image: String?
)

