package ro.sapientia.android_11eloadas.viewmodel

import androidx.lifecycle.ViewModel
import ro.sapientia.android_11eloadas.model.ActivityItem

class ActivityViewModel(): ViewModel() {
    val list = listOf(
        ActivityItem(1,
            1,
            89,
            2,
            1668365058849,
            89, // ki adja
             99, //kinek
            null,
            null,
        ) ,ActivityItem(2,
        2,
        89,
        1,
        1668365058849,
        89, // ki adja
        98, //kinek
        "Set up company profile",
        null,
    ),
        ActivityItem(3,
        2,
        79,
        1,
        1668365058849,
        79, // ki adja
        69, //kinek
        null,
        null,
    ),
        ActivityItem(4,
            1,
            89,
            1,
            1668365058849,
            89, // ki adja
            99, //kinek
            null,
            null,
        )
    )
}