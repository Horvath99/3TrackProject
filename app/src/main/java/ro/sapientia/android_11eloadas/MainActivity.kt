package ro.sapientia.android_11eloadas

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.opengl.Visibility
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import ro.sapientia.android_11eloadas.util.Constants

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.setBackgroundDrawable(ColorDrawable(getColor(R.color.white)))
        supportActionBar?.setTitleColor(Color.BLACK)


        val bottomNavigationView : BottomNavigationView
        bottomNavigationView = findViewById(R.id.bottom_nav_view)
        val controller = findNavController(R.id.nav_host_fragment)

        // Read data from preferences
        val prefs = this.getPreferences(MODE_PRIVATE)
        val token = prefs.getString("token", "")

        val deadline = prefs.getLong("deadline", 0L)

        Log.i("xxx", "token: " + token)
        // @TODO - check the token's validity
        val isValid = true
        /*if (!token.equals("") && isValid ) {

            MyApplication.token = token!!
            MyApplication.email = prefs.getString("email","")!!

            controller.navigate(R.id.activitesFragment)
        }*/
        bottomNavigationView.setOnNavigationItemSelectedListener{
            when(it.itemId){
                R.id.activitesFragment -> controller.navigate(R.id.activitesFragment)
                R.id.myTasksFragment -> controller.navigate(R.id.myTasksFragment)
                R.id.myGroupsFragment -> controller.navigate(R.id.myGroupsFragment)
                R.id.settingsFragment -> controller.navigate(R.id.settingsFragment)
            }
            true
        }

    }
    private fun ActionBar.setTitleColor(color: Int) {
        val text = SpannableString(title ?: "")
        text.setSpan(ForegroundColorSpan(color),0,text.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        title = text
    }


}