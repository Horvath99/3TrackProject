package ro.sapientia.android_11eloadas.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import ro.sapientia.android_11eloadas.R
import ro.sapientia.android_11eloadas.databinding.FragmentMyProfile2Binding
import ro.sapientia.android_11eloadas.databinding.FragmentUpdateProfileBinding
import ro.sapientia.android_11eloadas.model.UpdateProfileRequest
import ro.sapientia.android_11eloadas.model.UpdateProfileResult
import ro.sapientia.android_11eloadas.repository.TrackerRepository
import ro.sapientia.android_11eloadas.viewmodel.MyUserViewModel
import ro.sapientia.android_11eloadas.viewmodel.MyUserViewModelFactory
import ro.sapientia.android_11eloadas.viewmodel.UpdateProfileViewModel
import ro.sapientia.android_11eloadas.viewmodel.UpdateProfileViewModelFactory


class UpdateProfileFragment : Fragment() {


    var _binding : FragmentUpdateProfileBinding? = null
    val binding get() = _binding!!

    private lateinit var myUserViewModel: MyUserViewModel
    private lateinit var updateProfileViewModel: UpdateProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = MyUserViewModelFactory(TrackerRepository())
        val factoryUpdateProfil = UpdateProfileViewModelFactory(TrackerRepository())
        updateProfileViewModel = ViewModelProvider(this,factoryUpdateProfil).get(UpdateProfileViewModel::class.java)
        myUserViewModel = ViewModelProvider(this,factory).get(MyUserViewModel :: class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpdateProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myUserViewModel.getMyUser()
        myUserViewModel.myUser.observe(viewLifecycleOwner){
           val myUser = myUserViewModel.myUser.value

           binding.firstNameUpdateProfile.setText(myUser?.first_name)
           binding.lastNameUpdateProfile.setText(myUser?.last_name)
           binding.locationUpdateProfile.setText(myUser?.location)
            binding.phoneNumberUpdateProfile.setText(myUser?.phone_number)
            binding.imageUrlUpdateProfile.setText(myUser?.image)


            binding.updateMyProfile.setOnClickListener{
                val firstName = binding.firstNameUpdateProfile.text
                val lastName = binding.lastNameUpdateProfile.text
                val phoneNumber = binding.phoneNumberUpdateProfile.text
                val location = binding.locationUpdateProfile.text
                val image = binding.imageUrlUpdateProfile.text

                updateProfileViewModel.updateProfile(UpdateProfileRequest(
                    lastName.toString(),
                    firstName.toString(),
                    phoneNumber.toString(),
                    location.toString(),
                    image.toString()
                ))

            }
            updateProfileViewModel.updateProfileResult.observe(viewLifecycleOwner){
                if( it == UpdateProfileResult.INVALID_CREDENTIALS){
                    Toast.makeText(
                        this.requireContext(),
                        "Invalid credentials",
                        Toast.LENGTH_LONG
                    ).show()
                }
                if(it == UpdateProfileResult.SUCCESS){
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