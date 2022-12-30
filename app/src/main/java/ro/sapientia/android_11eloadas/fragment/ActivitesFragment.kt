package ro.sapientia.android_11eloadas.fragment

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import ro.sapientia.android_11eloadas.R
import ro.sapientia.android_11eloadas.adapter.ActivitesAdapter
import ro.sapientia.android_11eloadas.databinding.FragmentActivitesBinding
import ro.sapientia.android_11eloadas.databinding.FragmentMyTasksBinding
import ro.sapientia.android_11eloadas.util.ActivityDataModel
import ro.sapientia.android_11eloadas.viewmodel.ActivityViewModel


class ActivitesFragment : Fragment() {

    private var _binding: FragmentActivitesBinding? = null
    private val binding get() = _binding!!
   private val activityAdapter : ActivitesAdapter by lazy{
       ActivitesAdapter()
   }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentActivitesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var toolbarTitle = activity?.findViewById<TextView>(R.id.mytitle)
        toolbarTitle?.setText("Activities")
        activityAdapter.setData(getData())
        binding.activitiesRecyclerView.apply {
            layoutManager = LinearLayoutManager(this.context)
            hasFixedSize()
            this.adapter = activityAdapter
        }

    }

    private fun getData() : List<ActivityDataModel> = listOf(
        ActivityDataModel.Group(
         "Horvath Janos",
        "May 21",
            "Horvath Janos added you to general group",
        ),
        ActivityDataModel.Task(
            "Balint Lehel",
            "April 9",
        "Balint Lehel assigned this task to you",
            "1234",
            "Foci",
            "Balint Lehel 2:34 pm",
            "Horvath Janos"
        ),
        ActivityDataModel.Group(
            "Horvath Janos",
            "May 21",
            "Horvath Janos added you to general group",
        ),
        ActivityDataModel.Task(
            "Balint Lehel",
            "April 9",
            "Balint Lehel assigned this task to you",
            "1234",
            "Foci",
            "Balint Lehel 2:34 pm",
            "Horvath Janos"
        ),
        ActivityDataModel.Group(
            "Horvath Janos",
            "May 21",
            "Horvath Janos added you to general group",
        ),
        ActivityDataModel.Task(
            "Balint Lehel",
            "April 9",
            "Balint Lehel assigned this task to you",
            "1234",
            "Foci",
            "Balint Lehel 2:34 pm",
            "Horvath Janos"
        )
    )


}