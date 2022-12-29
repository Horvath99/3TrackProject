package ro.sapientia.android_11eloadas.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import ro.sapientia.android_11eloadas.MyApplication
import ro.sapientia.android_11eloadas.R
import ro.sapientia.android_11eloadas.databinding.FragmentLoginBinding
import ro.sapientia.android_11eloadas.model.LoginRequest
import ro.sapientia.android_11eloadas.model.LoginResult
import ro.sapientia.android_11eloadas.repository.TrackerRepository
import ro.sapientia.android_11eloadas.util.Constants
import ro.sapientia.android_11eloadas.viewmodel.LoginViewModel
import ro.sapientia.android_11eloadas.viewmodel.LoginViewModelFactory


class LoginFragment : Fragment() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var editText1: EditText




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val factory = LoginViewModelFactory(TrackerRepository())
        loginViewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var toolbar = activity?.findViewById<Toolbar>(R.id.toolbar)
        toolbar?.visibility = View.GONE

        editText1 = view.findViewById(R.id.edittext_name_login_fragment)
        val editText2: EditText = view.findViewById(R.id.edittext_password_login_fragment)
        val button: Button = view.findViewById(R.id.button_login_fragment)

        val prefs = requireActivity().getPreferences(Context.MODE_PRIVATE)
        if (!prefs.getString("email", "").equals("")) {
            editText1.setText(prefs.getString("email", ""))
        }

        button.setOnClickListener {
            val email = editText1.text.toString().trim()
            val password = editText2.text.toString().trim()
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    this.requireContext(),
                    "Please, enter your email and password",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                loginViewModel.login(LoginRequest(email, password))
            }
        }

        loginViewModel.loginResult.observe(viewLifecycleOwner) {
            // Save data to preferences
            if( it == LoginResult.INVALID_CREDENTIALS){
                Toast.makeText(
                    this.requireContext(),
                    "Invalid credentials",
                    Toast.LENGTH_LONG
                ).show()
            }
            if ( it == LoginResult.SUCCESS ) {
                val prefs = requireActivity().getPreferences(Context.MODE_PRIVATE)
                val edit = prefs.edit()
                edit.putString("token", MyApplication.token)
                edit.putLong("deadline", MyApplication.deadline)
                edit.putString("email", editText1.text.toString())
                edit.apply()
                findNavController().navigate(R.id.action_loginFragment_to_activitesFragment)
            }
        }

    }
}