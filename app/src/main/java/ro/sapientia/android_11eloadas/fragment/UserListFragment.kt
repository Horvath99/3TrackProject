package ro.sapientia.android_11eloadas.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ro.sapientia.android_11eloadas.R
import ro.sapientia.android_11eloadas.adapter.UserAdapter
import ro.sapientia.android_11eloadas.databinding.FragmentMyGroupsBinding
import ro.sapientia.android_11eloadas.databinding.FragmentUserListBinding
import ro.sapientia.android_11eloadas.repository.TrackerRepository
import ro.sapientia.android_11eloadas.viewmodel.DepartmentsViewModel
import ro.sapientia.android_11eloadas.viewmodel.DepartmentsViewModelFactory
import ro.sapientia.android_11eloadas.viewmodel.UserListViewModel
import ro.sapientia.android_11eloadas.viewmodel.UserListViewModelFactory

class UserListFragment : Fragment() {

    private var _binding: FragmentUserListBinding? = null
    private val binding get() = _binding!!

    private lateinit var userListViewModel: UserListViewModel
    private lateinit var departmentsViewModel: DepartmentsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = UserListViewModelFactory(TrackerRepository())
        userListViewModel = ViewModelProvider(this, factory).get(UserListViewModel::class.java)
        val factoryDepartment = DepartmentsViewModelFactory(TrackerRepository())
        departmentsViewModel = ViewModelProvider(this,factoryDepartment).get(DepartmentsViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            val groupName: String = arguments?.getString("groupName")!!
        departmentsViewModel.getDepartments()
        departmentsViewModel.departmentList.observe(viewLifecycleOwner) {
            val departmentList = departmentsViewModel.departmentList.value
            userListViewModel.readUsers()
            userListViewModel.userList.observe(viewLifecycleOwner) {
                val userList = userListViewModel.userList.value

                val department = departmentList?.find { it.name == groupName }
                val finalUserList = userList?.filter { it.department_id == department?.ID }

                val adapter = UserAdapter(finalUserList!!, groupName)
                val layoutManager = LinearLayoutManager(context)

                binding.usersrecycleview.layoutManager = layoutManager
                binding.usersrecycleview.adapter = adapter
            }
        }

    }

}