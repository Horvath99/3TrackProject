package ro.sapientia.android_11eloadas.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toolbar
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ro.sapientia.android_11eloadas.R
import ro.sapientia.android_11eloadas.adapter.MyTaskAdapter
import ro.sapientia.android_11eloadas.databinding.FragmentMyTasksBinding
import ro.sapientia.android_11eloadas.databinding.MyTaskItemBinding
import ro.sapientia.android_11eloadas.model.Department

import ro.sapientia.android_11eloadas.model.MyTaskItem
import ro.sapientia.android_11eloadas.model.User
import ro.sapientia.android_11eloadas.repository.TrackerRepository
import ro.sapientia.android_11eloadas.viewmodel.*


class MyTasksFragment : Fragment() {


    private var _binding: FragmentMyTasksBinding? = null
    private val binding get() = _binding!!


    private lateinit var toolbarAddButton : ImageView
    private lateinit var myTasksViewModel: MyTasksViewModel
    private lateinit var userListViewModel: UserListViewModel
    private lateinit var departmentsViewModel: DepartmentsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = MyTasksViewModelFactory(TrackerRepository())
        val factoryUsers = UserListViewModelFactory(TrackerRepository())
        val factoryDepartment = DepartmentsViewModelFactory(TrackerRepository())
        departmentsViewModel = ViewModelProvider(this, factoryDepartment).get(DepartmentsViewModel::class.java)
        userListViewModel = ViewModelProvider(this,factoryUsers).get(UserListViewModel :: class.java)
        myTasksViewModel = ViewModelProvider(this,factory).get(MyTasksViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentMyTasksBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var toolbarTitle = activity?.findViewById<TextView>(R.id.mytitle)
        toolbarAddButton = activity?.findViewById(R.id.addTask)!!
        toolbarAddButton?.visibility = View.VISIBLE
        var toolbarSearchButton = activity?.findViewById<ImageView>(R.id.search)
        toolbarTitle?.setText("My Tasks")
        toolbarAddButton?.setOnClickListener{
            findNavController().navigate(R.id.action_myTasksFragment_to_createNewTaskFragment)
        }

        myTasksViewModel.getMyTasks()
        myTasksViewModel.tasksList.observe(viewLifecycleOwner){
            val tasksList: List<MyTaskItem> = myTasksViewModel.tasksList.value!!
            userListViewModel.readUsers()
            userListViewModel.userList.observe(viewLifecycleOwner){
                val userList : List<User> = userListViewModel.userList.value!!
                departmentsViewModel.getDepartments()
                departmentsViewModel.departmentList.observe(viewLifecycleOwner){
                    val departmentList : List<Department> = departmentsViewModel.departmentList.value!!
                    val adapter=MyTaskAdapter(tasksList,userList,departmentList)
                    val layoutManager = LinearLayoutManager(context)
                    binding.recycleview.layoutManager=layoutManager
                    binding.recycleview.adapter = adapter
                }
            }

        }

    }

    fun setAppBar(title:String,addButton: Boolean,searchButton: Boolean,createButton:Boolean,updateButton:Boolean,editButton:Boolean){
        var toolbarTitle = activity?.findViewById<TextView>(R.id.mytitle)
        var toolbarAddButton= activity?.findViewById<ImageView>(R.id.addTask)
        var toolbarSearchButton = activity?.findViewById<ImageView>(R.id.search)
        var toolbarCreateButton = activity?.findViewById<Button>(R.id.createTask)
        var toolbarUpdateButton = activity?.findViewById<Button>(R.id.updateTask)
        var toolbarEditButton = activity?.findViewById<ImageView>(R.id.editTask)
        toolbarTitle?.setText(title)

        if(addButton == true){
            toolbarAddButton?.visibility=View.VISIBLE

            toolbarAddButton?.setOnClickListener{
                findNavController().navigate(R.id.action_myTasksFragment_to_createNewTaskFragment)
            }
        }else{
            toolbarAddButton?.visibility=View.GONE
        }
        if(searchButton == true){
            toolbarSearchButton?.visibility=View.VISIBLE
        }else{
            toolbarSearchButton?.visibility=View.GONE
        }
        if(createButton == true){
            toolbarCreateButton?.visibility=View.VISIBLE
        }else{
            toolbarCreateButton?.visibility=View.GONE
        }
        if(updateButton == true){
            toolbarUpdateButton?.visibility=View.VISIBLE
        }else{
            toolbarUpdateButton?.visibility=View.GONE
        }
        if(editButton == true){
            toolbarEditButton?.visibility= View.VISIBLE
        }else{
            toolbarAddButton?.visibility = View.GONE
        }




    }

    override fun onDestroyView() {
        super.onDestroyView()
        toolbarAddButton?.visibility = View.GONE
        _binding = null
    }

}


