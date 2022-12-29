package ro.sapientia.android_11eloadas.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import ro.sapientia.android_11eloadas.R
import ro.sapientia.android_11eloadas.databinding.FragmentCreateNewTaskBinding
import ro.sapientia.android_11eloadas.databinding.FragmentMyTasksBinding
import ro.sapientia.android_11eloadas.databinding.FragmentTaskDescriptionBinding
import ro.sapientia.android_11eloadas.repository.TrackerRepository
import ro.sapientia.android_11eloadas.viewmodel.*
import java.text.SimpleDateFormat


class TaskDescriptionFragment : Fragment() {

    private var _binding: FragmentTaskDescriptionBinding? = null
    private val binding get() = _binding!!

    private lateinit var toolbarTitle : TextView
    private lateinit var toolbarEdit : ImageView

    private lateinit var myTaskViewModel: MyTasksViewModel
    private lateinit var userListViewModel: UserListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = MyTasksViewModelFactory(TrackerRepository())
        val factoryUsers = UserListViewModelFactory(TrackerRepository())
        userListViewModel = ViewModelProvider(this,factoryUsers).get(UserListViewModel :: class.java)
        myTaskViewModel = ViewModelProvider(this,factory).get(MyTasksViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentTaskDescriptionBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val position = arguments?.getInt("position")

        toolbarTitle = activity?.findViewById(R.id.mytitle)!!
        toolbarEdit = activity?.findViewById(R.id.editTask)!!


        toolbarTitle?.setText("Task Description")
        toolbarEdit?.visibility=View.VISIBLE

        val bundle = bundleOf("position" to position)
        toolbarEdit.setOnClickListener {
            findNavController().navigate(R.id.action_taskDescriptionFragment_to_editTaskFragment,bundle)
        }



        myTaskViewModel.getMyTasks()
        myTaskViewModel.tasksList.observe(viewLifecycleOwner){
            val task = myTaskViewModel.tasksList.value?.get(position!!)

            userListViewModel.readUsers()
            userListViewModel.userList.observe(viewLifecycleOwner){
                val userList = userListViewModel.userList.value!!
                binding.taskDescription.text = task?.description
                binding.deadline.text = task?.deadline.toString()
                binding.taskType.text = task?.department_ID.toString()
                binding.taskTitle.text = task?.title
                val assignedByName = userList.find { it.ID == task?.created_by_user_ID}
                val date = longToDate(task?.created_time)
                binding.assignedBy.text = assignedByName?.last_name + " " + assignedByName?.first_name + " " + date
                binding.assignedDate.text = date
                val assigne = userList.find { it.ID == task?.asigned_to_user_ID }
                binding.assignee.text = assigne?.last_name + " " + assigne?.first_name
                binding.deadline.text = date.substringAfterLast(" ")

            }


        }


    }
    fun longToDate(date: Long?): String {
        val formatter = SimpleDateFormat("hh:mm MM-dd-yyyy")
        val formattedDate = formatter.format(date)
        return formattedDate
    }

    override fun onDestroyView() {
        super.onDestroyView()
        toolbarEdit?.visibility=View.GONE
    }


}