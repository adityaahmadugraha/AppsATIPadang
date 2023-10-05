package com.aditya.appsatipadang.admin.fragment.history_admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.aditya.appsatipadang.databinding.FragmentHistoryAdminBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragmentAdmin : Fragment() {

    private var _binding: FragmentHistoryAdminBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HistoryAdminViewModel by viewModels()

//    private lateinit var mAdapter: AdapterLaporan
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
    _binding = FragmentHistoryAdminBinding.inflate(inflater, container, false)
    return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


}