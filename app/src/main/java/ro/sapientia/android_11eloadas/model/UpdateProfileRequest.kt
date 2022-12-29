package ro.sapientia.android_11eloadas.model

import androidx.annotation.Nullable
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class UpdateProfileRequest(
    var lastName : String,
    var firstName: String,
    @Nullable
    var location: String?,
    @Nullable
    var phoneNumber : String?,
    @Nullable
    var imageUrl : String?
)