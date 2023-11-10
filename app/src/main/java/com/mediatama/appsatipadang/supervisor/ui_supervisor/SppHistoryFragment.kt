package com.mediatama.appsatipadang.supervisor.ui_supervisor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.mediatama.appsatipadang.R
import com.mediatama.appsatipadang.adapter.AdapterHystoryHarian
import com.mediatama.appsatipadang.admin.fragment.history_admin.HistoryAdminViewModel
import com.mediatama.appsatipadang.data.Resource
import com.mediatama.appsatipadang.databinding.FragmentSppHistoryBinding
import com.mediatama.appsatipadang.teknik.ui_teknisi.history.HistoryAdapterBulanan
import com.mediatama.appsatipadang.utils.Constant.getToken
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SppHistoryFragment : Fragment() {

    private var _binding: FragmentSppHistoryBinding? = null
    private val viewModel: HistoryAdminViewModel by viewModels()
    private lateinit var mAdapterHarian: AdapterHystoryHarian
    private lateinit var mAdapterBulanan: HistoryAdapterBulanan
    private val binding get() = _binding!!

    private lateinit var lySwip: SwipeRefreshLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSppHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        lySwip = binding.lySwip
        lySwip.setOnRefreshListener {

            getDataUser()
        }


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
                    is Resource.Loading -> {
                        binding.progressBar.isVisible = true
                    }
                    is Resource.Success -> {
                        val data = result.data
                        mAdapterBulanan.submitList(data.laporan)
                        setupRecyclerView()
                        binding.progressBar.isVisible = false
                        lySwip.isRefreshing = false
                    }
                    is Resource.Error -> {
                        binding.progressBar.isVisible = false
                    }
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













