package ro.sapientia.android_11eloadas.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import org.w3c.dom.Text
import ro.sapientia.android_11eloadas.R
import ro.sapientia.android_11eloadas.databinding.FragmentMyProfile2Binding
import ro.sapientia.android_11eloadas.databinding.FragmentSettingsBinding
import ro.sapientia.android_11eloadas.model.User
import ro.sapientia.android_11eloadas.repository.TrackerRepository
import ro.sapientia.android_11eloadas.viewmodel.MyUserViewModel
import ro.sapientia.android_11eloadas.viewmodel.MyUserViewModelFactory
import ro.sapientia.android_11eloadas.viewmodel.UserListViewModel
import ro.sapientia.android_11eloadas.viewmodel.UserListViewModelFactory


class MyProfileFragment : Fragment() {

    var _binding : FragmentMyProfile2Binding? = null
    val binding get() = _binding!!

    private lateinit var userListViewModel: UserListViewModel
    private lateinit var myUserViewModel: MyUserViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val factoryUsers = UserListViewModelFactory(TrackerRepository())
        userListViewModel = ViewModelProvider(this, factoryUsers).get(UserListViewModel::class.java)

        val factory = MyUserViewModelFactory(TrackerRepository())
        myUserViewModel = ViewModelProvider(this,factory).get(MyUserViewModel :: class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMyProfile2Binding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userName : TextView = binding.myName
        val userEmail : TextView = binding.email
        val userPhoneNumber : TextView = binding.phoneNumber
        val userLocation : TextView = binding.location
        val mentorName : TextView = binding.mentorName
        val mentorFunction : TextView = binding.mentorFunction
        val mentorImage : ImageView = binding.mentorImage

        val updateButton : ImageView = binding.vector1

        updateButton.setOnClickListener{
            findNavController().navigate(R.id.action_myProfileFragment_to_updateProfileFragment)
        }




        myUserViewModel.getMyUser()
        myUserViewModel.myUser.observe(viewLifecycleOwner){
            val user = myUserViewModel.myUser.value
            userListViewModel.readUsers()
            userListViewModel.userList.observe(viewLifecycleOwner){
                val userList = userListViewModel.userList.value
                val mentor=userList?.find{it.department_id == user?.department_id && it?.type == 0}
                val id = user?.ID


                mentorName.setText(mentor?.first_name+" "+mentor?.last_name)
                userName.setText(user?.last_name+" "+user?.first_name)
                userEmail.setText((user?.email))
                userLocation.setText(user?.location)
                userPhoneNumber.setText(user?.phone_number)
                mentorFunction.setText(user?.last_name+" "+user?.first_name + " mentor")


                println("image "+mentor?.image .toString())
                Glide.with(this)
                    .load(mentor?.image.toString())
                    .into(mentorImage)
            }
        }




    }

}

