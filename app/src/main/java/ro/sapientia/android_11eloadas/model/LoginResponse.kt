package ro.sapientia.android_11eloadas.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginResponse (
    var userId: Int,
    var token: String,
    var deadline: Long
)