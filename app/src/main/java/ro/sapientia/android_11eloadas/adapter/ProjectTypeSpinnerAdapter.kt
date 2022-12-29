package ro.sapientia.android_11eloadas.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import ro.sapientia.android_11eloadas.R
import ro.sapientia.android_11eloadas.model.Department

class ProjectTypeSpinnerAdapter(
    context: Context,
    private val resource: Int,
    objects: List<Department>
) : ArrayAdapter<Department>(context, resource, objects) {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Inflate the custom layout for the spinner items
        val view = inflater.inflate(resource, parent, false)

        // Get the TextView from the layout and set the value
        val textView = view.findViewById<TextView>(R.id.name)
        val obj = getItem(position)
        val value = obj?.name// Replace this with a method that returns the desired value
        textView.text = value

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getView(position, convertView, parent)
    }
}