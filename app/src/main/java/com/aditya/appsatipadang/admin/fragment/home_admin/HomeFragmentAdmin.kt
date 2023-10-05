package com.aditya.appsatipadang.admin.fragment.home_admin

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.aditya.appsatipadang.R
import com.aditya.appsatipadang.admin.ui.kamtibmas_admin.KamtibmasActivityAdmin
import com.aditya.appsatipadang.admin.ui.prasarana_admin.PrasaranaActivityAdmin
import com.aditya.appsatipadang.admin.ui.sarana_admin.SaranaActivityAdmin
import com.aditya.appsatipadang.admin.ui.status_admin.StatusActivityAdmin
import com.aditya.appsatipadang.databinding.FragmentHomeAdminBinding
import com.aditya.appsatipadang.databinding.FragmentHomeBinding
import com.aditya.appsatipadang.user.adapter.AdapterLaporan
import com.aditya.appsatipadang.user.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragmentAdmin : Fragment() {

    private var _binding: FragmentHomeAdminBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeAdminViewModel by viewModels()

//    private lateinit var mAdapter: AdapterLaporan
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

    _binding = FragmentHomeAdminBinding.inflate(inflater, container, false)
    return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.cardSaranaAdmin.setOnClickListener {
            val intent = Intent(activity, SaranaActivityAdmin::class.java)
            startActivity(intent)
        }
        binding.cardPrasaranaAdmin.setOnClickListener {
            val intent = Intent(activity, PrasaranaActivityAdmin::class.java)
            startActivity(intent)
        }
        binding.cardKamtibmasAdmin.setOnClickListener {
            val intent = Intent(activity, KamtibmasActivityAdmin::class.java)
            startActivity(intent)
        }
        binding.cardStatusAdmin.setOnClickListener {
            val intent = Intent(activity, StatusActivityAdmin::class.java)
            startActivity(intent)
        }
    }


}