package ro.sapientia.android_11eloadas.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ro.sapientia.android_11eloadas.R
import ro.sapientia.android_11eloadas.adapter.GroupsAdapter
import ro.sapientia.android_11eloadas.databinding.FragmentMyGroupsBinding
import ro.sapientia.android_11eloadas.databinding.FragmentMyTasksBinding
import ro.sapientia.android_11eloadas.model.Department
import ro.sapientia.android_11eloadas.repository.TrackerRepository
import ro.sapientia.android_11eloadas.viewmodel.DepartmentsViewModel
import ro.sapientia.android_11eloadas.viewmodel.DepartmentsViewModelFactory


class MyGroupsFragment : Fragment() {


    private var _binding: FragmentMyGroupsBinding? = null
    private val binding get() = _binding!!

    private lateinit var departmentsViewModel: DepartmentsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = DepartmentsViewModelFactory(TrackerRepository())
        departmentsViewModel = ViewModelProvider(this,factory).get(DepartmentsViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMyGroupsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var toolbarTitle = activity?.findViewById<TextView>(R.id.mytitle)
        toolbarTitle?.text = "My Groups"


        departmentsViewModel.getDepartments()
        departmentsViewModel.departmentList.observe(viewLifecycleOwner){

            val departmentList : List<Department> = departmentsViewModel.departmentList.value!!

            val adapter = GroupsAdapter(departmentList)
            val layoutManager = LinearLayoutManager(context)
            binding.grouprecyclerview.layoutManager =layoutManager
            binding.grouprecyclerview.adapter = adapter

        }
    }


}