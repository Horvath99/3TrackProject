package ro.sapientia.android_11eloadas.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UpdateProfileResponse (
    var message:String
    )