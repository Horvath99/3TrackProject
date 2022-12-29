package ro.sapientia.android_11eloadas.fragment

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import ro.sapientia.android_11eloadas.R
import ro.sapientia.android_11eloadas.adapter.AssigneSpinnerAdapter
import ro.sapientia.android_11eloadas.adapter.ProjectTypeSpinnerAdapter
import ro.sapientia.android_11eloadas.databinding.FragmentCreateNewTaskBinding
import ro.sapientia.android_11eloadas.databinding.FragmentEditTaskBinding
import ro.sapientia.android_11eloadas.model.CreateTaskRequest
import ro.sapientia.android_11eloadas.model.CreateTaskResult
import ro.sapientia.android_11eloadas.model.UpdateTaskRequest
import ro.sapientia.android_11eloadas.model.UpdateTaskResult
import ro.sapientia.android_11eloadas.repository.TrackerRepository
import ro.sapientia.android_11eloadas.viewmodel.*
import java.text.SimpleDateFormat
import java.util.*


class EditTaskFragment : Fragment() {


    private var _binding: FragmentEditTaskBinding? = null
    private val binding get() = _binding!!

    private lateinit var toolbarTitle : TextView
    private lateinit var toolbarUpdate : Button

    private lateinit var updateTaskViewModel: UpdateTaskViewModel
    private lateinit var departmentsViewModel: DepartmentsViewModel
    private lateinit var userListViewModel: UserListViewModel
    private lateinit var myTaskViewModel: MyTasksViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = MyTasksViewModelFactory(TrackerRepository())
        val factoryDepartment = DepartmentsViewModelFactory(TrackerRepository())
        val factoryUsers = UserListViewModelFactory(TrackerRepository())
        val factoryUpdate = UpdateTaskViewModelFactory(TrackerRepository())

        updateTaskViewModel = ViewModelProvider(this,factoryUpdate).get(UpdateTaskViewModel::class.java)
        myTaskViewModel = ViewModelProvider(this,factory).get(MyTasksViewModel::class.java)
        userListViewModel = ViewModelProvider(this,factoryUsers).get(UserListViewModel :: class.java)
        departmentsViewModel = ViewModelProvider(this, factoryDepartment).get(DepartmentsViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditTaskBinding.inflate(inflater,container,false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val position = arguments?.getInt("position")

         toolbarTitle = activity?.findViewById(R.id.mytitle)!!
        toolbarUpdate =activity?.findViewById(R.id.updateTask)!!

        toolbarUpdate.visibility = View.VISIBLE
         toolbarTitle.text = "Edit Task"

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

        userListViewModel.readUsers()
        userListViewModel.userList.observe(viewLifecycleOwner){
            val userList = userListViewModel.userList.value!!

            departmentsViewModel.getDepartments()
            departmentsViewModel.departmentList.observe(viewLifecycleOwner){
                val departmentList = departmentsViewModel.departmentList.value!!

                myTaskViewModel.getMyTasks()
                myTaskViewModel.tasksList.observe(viewLifecycleOwner){
                    val task = myTaskViewModel.tasksList.value?.get(position!!)

                    val spinnerProjectType = binding.projectTypeSpinner
                    spinnerProjectType.adapter= activity?.let {
                        ProjectTypeSpinnerAdapter(it,R.layout.spinner_item,departmentList)
                    }
                    val spinnerAssigne = binding.assigneSpinner
                    spinnerAssigne.adapter = activity?.let {
                        AssigneSpinnerAdapter(it,R.layout.spinner_item,userList)
                    }

                    binding.taskDescription.setText(task?.description)
                    val departmentPosition = departmentList.indexOfFirst { it.ID == task?.department_ID }
                    binding.projectTypeSpinner.setSelection(departmentPosition)
                    binding.createTaskName.setText(task?.title)
                    val userPoisition = userList.indexOfFirst { it.ID == task?.asigned_to_user_ID }
                    binding.assigneSpinner.setSelection(userPoisition)
                    binding.prioritySpinner.setSelection(task?.priority!!-1)
                    binding.deadline.setText(longToDate(task.deadline))

                    toolbarUpdate.setOnClickListener{
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

                      updateTaskViewModel.updateTask(UpdateTaskRequest(
                          task.ID,
                          taskName!!,
                          description!!,
                          assigne,
                          priority,
                          dateToLong(date),
                          type,
                          0
                      ))

                    }
                    updateTaskViewModel.updateTaskResult.observe(viewLifecycleOwner){
                        if(it == UpdateTaskResult.INVALID_CREDENTIALS){
                            Toast.makeText(
                                this.requireContext(),
                                "Invalid credentials",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        if(it == UpdateTaskResult.SUCCESS){
                            Toast.makeText(
                                this.requireContext(),
                                "Succes",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }


            }
        }
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
    fun longToDate(date: Long?): String {
        val formatter = SimpleDateFormat("MM-dd-yyyy")
        val formattedDate = formatter.format(date)
        return formattedDate
    }

    override fun onDestroyView() {
        super.onDestroyView()
        toolbarUpdate.visibility = View.GONE
    }
}