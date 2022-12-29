package ro.sapientia.android_11eloadas.fragment

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi

import androidx.lifecycle.ViewModelProvider
import ro.sapientia.android_11eloadas.MyApplication.Companion.token
import ro.sapientia.android_11eloadas.R
import ro.sapientia.android_11eloadas.adapter.AssigneSpinnerAdapter
import ro.sapientia.android_11eloadas.adapter.ProjectTypeSpinnerAdapter
import ro.sapientia.android_11eloadas.databinding.FragmentCreateNewTaskBinding
import ro.sapientia.android_11eloadas.databinding.FragmentMyTasksBinding
import ro.sapientia.android_11eloadas.model.CreateTaskRequest
import ro.sapientia.android_11eloadas.model.CreateTaskResult
import ro.sapientia.android_11eloadas.model.Department
import ro.sapientia.android_11eloadas.model.User
import ro.sapientia.android_11eloadas.repository.TrackerRepository
import ro.sapientia.android_11eloadas.viewmodel.*
import java.util.*
import kotlin.collections.ArrayList


class CreateNewTaskFragment : Fragment() {

    private var _binding: FragmentCreateNewTaskBinding? = null
    private val binding get() = _binding!!

    private lateinit var departmentsViewModel: DepartmentsViewModel
    private lateinit var userListViewModel: UserListViewModel
    private lateinit var createNewTaskViewModel: CreateTaskViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factoryDepartment = DepartmentsViewModelFactory(TrackerRepository())
        val factoryUsers = UserListViewModelFactory(TrackerRepository())
        val factoryCreateTask = CreateTaskViewModelFactory(TrackerRepository())
        userListViewModel = ViewModelProvider(this,factoryUsers).get(UserListViewModel :: class.java)
        departmentsViewModel = ViewModelProvider(this, factoryDepartment).get(DepartmentsViewModel::class.java)
        createNewTaskViewModel = ViewModelProvider(this,factoryCreateTask).get(CreateTaskViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCreateNewTaskBinding.inflate(inflater,container,false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbarCreate = activity?.findViewById<Button>(R.id.createTask)
        toolbarCreate?.visibility = View.VISIBLE

        val toolbarAdd = activity?.findViewById<ImageView>(R.id.addTask)
        toolbarAdd?.visibility=View.GONE

        val toolbarSearch = activity?.findViewById<ImageView>(R.id.search)
        toolbarSearch?.visibility = View.GONE

        val spinnerPriority = binding.prioritySpinner
        val priorityList = listOf<String>("LOW","MEDIUM","HIGH","BLOCKED","DONE")
        spinnerPriority.adapter = activity?.let{
            ArrayAdapter<String>(it,android.R.layout.simple_spinner_item,priorityList)
        }

        val datePicker = binding.datePicker
        val datePickerButton = binding.datePickerButton
        datePickerButton.setOnClickListener{
            datePicker.visibility=View.VISIBLE
        }
        var date: Date = Date()
        datePicker.setOnDateChangedListener{ _,_,_,_ ->
            datePicker.visibility=View.GONE
             val year = datePicker.year
             val month = datePicker.month
             val day = datePicker.dayOfMonth
            binding.deadline.setText(year.toString()+"."+month.toString()+"."+day.toString())
            date = setDate(year,month,day)
        }

        departmentsViewModel.getDepartments()
        departmentsViewModel.departmentList.observe(viewLifecycleOwner){
            val departmentList : List<Department> = departmentsViewModel.departmentList.value!!


            val departmentArray : ArrayList<Department> = arrayListOf()

            val spinnerProjectType = binding.projectTypeSpinner
            spinnerProjectType.adapter= activity?.let {
                ProjectTypeSpinnerAdapter(it,R.layout.spinner_item,departmentList)
            }

            userListViewModel.readUsers()
            userListViewModel.userList.observe(viewLifecycleOwner){
                val userList = userListViewModel.userList.value!!

                val spinnerAssigne = binding.assigneSpinner
                spinnerAssigne.adapter = activity?.let {
                    AssigneSpinnerAdapter(it,R.layout.spinner_item,userList)
                }

                var addButton = activity?.findViewById<Button>(R.id.createTask)
                addButton?.setOnClickListener{
                    var type : Int = spinnerProjectType.selectedItemPosition
                    type = departmentList[type].ID
                    println(type)
                    val taskName = binding.createTaskName.text?.toString()?.trim()
                    println(taskName)
                    var assigne: Int = spinnerAssigne.selectedItemPosition
                    assigne = userList[assigne].ID

                    val priorityText = spinnerPriority.selectedItem.toString()
                    var priority :Int = 0
                    if(priorityText == "LOW"){
                        priority = 1
                    }
                    if(priorityText == "MEDIUM"){
                        priority = 2
                    }
                    if(priorityText == "HIGH"){
                        priority = 3
                    }
                    if(priorityText == "BLOCKED"){
                        priority = 4
                    }
                    if(priorityText == "DONE"){
                        priority = 5
                    }
                    //check for empty fields


                    println(date.toString())
                    val description = binding.taskDescription.text?.toString()?.trim()
                    println(description)
                    //println(Calendar.getInstance().time)

                    createNewTaskViewModel.addTask(CreateTaskRequest(
                        taskName!!,
                        description!!,
                        assigne,
                        priority,
                        dateToLong(date),
                        type,
                        0
                    ))
                }
            }

        }




        createNewTaskViewModel.createTaskResult.observe(viewLifecycleOwner){
            if(it == CreateTaskResult.INVALID_CREDENTIALS){
                Toast.makeText(
                    this.requireContext(),
                    "Invalid credentials",
                    Toast.LENGTH_LONG
                ).show()
            }
            if(it == CreateTaskResult.SUCCESS){
                Toast.makeText(
                    this.requireContext(),
                    "Succes",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        val toolbarCreate = activity?.findViewById<Button>(R.id.createTask)
        toolbarCreate?.visibility = View.GONE

        val toolbarAdd = activity?.findViewById<ImageView>(R.id.addTask)
        toolbarAdd?.visibility=View.VISIBLE

        val toolbarSearch = activity?.findViewById<ImageView>(R.id.search)
        toolbarSearch?.visibility = View.VISIBLE

    }
    fun dateToLong(date: Date): Long {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar.timeInMillis
    }
    fun setDate(year: Int, month: Int, day: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month - 1) // Month is 0-based
        calendar.set(Calendar.DAY_OF_MONTH, day)
        return calendar.time
    }


}


