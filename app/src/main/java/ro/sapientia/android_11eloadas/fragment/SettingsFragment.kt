package ro.sapientia.android_11eloadas.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import ro.sapientia.android_11eloadas.R
import ro.sapientia.android_11eloadas.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {


    var _binding : FragmentSettingsBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater,container,false)
        binding.viewprofile.setOnClickListener{
            findNavController().navigate(R.id.action_settingsFragment_to_myProfileFragment)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var toolbarTitle = activity?.findViewById<TextView>(R.id.mytitle)
        toolbarTitle?.setText("Settings")
    }

}