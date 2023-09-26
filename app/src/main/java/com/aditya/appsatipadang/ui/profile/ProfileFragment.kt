package com.aditya.appsatipadang.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.aditya.appsatipadang.R
import com.aditya.appsatipadang.databinding.FragmentProfileBinding
import com.aditya.appsatipadang.ui.login.LoginActivity
import com.aditya.laporan.kamtibmas.ActivityKamtibmas
import com.aditya.laporan.prasarana.ActivityPrasarana
import com.aditya.laporan.sarana.SaranaActivity
import com.aditya.laporan.status.ActivityStatus

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupButtonBackClicked()

        binding?.btnLogout?.setOnClickListener {
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }
    }


    private fun setupButtonBackClicked() {

        binding?.imgBackProfil?.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_profile_to_navigation_home)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}