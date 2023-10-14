package com.aditya.appsatipadang.supervisor.ui_supervisor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aditya.appsatipadang.R
import com.aditya.appsatipadang.adapter.AdapterHystoryHarian
import com.aditya.appsatipadang.admin.fragment.history_admin.HistoryAdminViewModel
import com.aditya.appsatipadang.data.Resource
import com.aditya.appsatipadang.databinding.FragmentSppHistoryBinding
import com.aditya.appsatipadang.teknik.ui_teknisi.history.HistoryAdapterBulanan
import com.aditya.appsatipadang.utils.Constant.getToken
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SppHistoryFragment : Fragment() {

    private var _binding: FragmentSppHistoryBinding? = null
    private val viewModel: HistoryAdminViewModel by viewModels()
    private lateinit var mAdapterHarian: AdapterHystoryHarian
    private lateinit var mAdapterBulanan: HistoryAdapterBulanan
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSppHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupButtonBackClicked()
        getDataUser()
        setupAdapter()
    }

    private fun setupButtonBackClicked() {
        binding.imgBackHistory.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_history_spp_to_navigation_home_spp)
        }
    }

    private fun setupAdapter() {
        mAdapterHarian = AdapterHystoryHarian {

        }

        mAdapterBulanan = HistoryAdapterBulanan {

        }
    }

    private fun getDataUser() {

        // harian
        viewModel.getUser().observe(viewLifecycleOwner){
            viewModel.getListLaporanHarian(it.getToken).observe(viewLifecycleOwner){ result ->
                when(result){
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        val data = result.data
                        mAdapterHarian.submitList(data.laporan)
                        setupRecyclerView()
                    }
                    is Resource.Error -> {}
                }
            }
        }
        // bulanan
        viewModel.getUser().observe(viewLifecycleOwner){
            viewModel.getListLaporanBulanan(it.getToken).observe(viewLifecycleOwner){ result ->
                when(result){
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        val data = result.data
                        mAdapterBulanan.submitList(data.laporan)
                        setupRecyclerView()
                    }
                    is Resource.Error -> {}
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvHistoryBulanan.apply {
            adapter = mAdapterBulanan
            layoutManager = LinearLayoutManager(requireActivity())
            setHasFixedSize(true)
        }
        binding.rvHistoryHariIni.apply {
            adapter = mAdapterHarian
            layoutManager = LinearLayoutManager(requireActivity())
            setHasFixedSize(true)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}













