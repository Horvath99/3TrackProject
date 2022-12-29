package ro.sapientia.android_11eloadas.model

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class Department (
    var ID : Int,
    var name: String
    )


