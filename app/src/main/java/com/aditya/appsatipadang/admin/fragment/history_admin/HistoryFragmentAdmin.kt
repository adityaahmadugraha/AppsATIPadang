package com.aditya.appsatipadang.admin.fragment.history_admin

import android.content.Intent
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
import com.aditya.appsatipadang.R
import com.aditya.appsatipadang.adapter.AdapterHystoryHarian
import com.aditya.appsatipadang.admin.ui.sarana_admin.SaranaActivityAdmin
import com.aditya.appsatipadang.admin.ui.sarana_admin.SaranaActivityAdmin.Companion.TAG_ID_PENGADUAN
import com.aditya.appsatipadang.data.Resource
import com.aditya.appsatipadang.databinding.FragmentHistoryAdminBinding
import com.aditya.appsatipadang.teknik.ui_teknisi.history.HistoryAdapterBulanan
import com.aditya.appsatipadang.utils.Constant.getToken
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragmentAdmin : Fragment() {

    private var _binding: FragmentHistoryAdminBinding? = null
    private val viewModel: HistoryAdminViewModel by viewModels()
    private val binding get() = _binding!!

    private lateinit var mAdapterHarian: AdapterHystoryHarian
    private lateinit var mAdapterBulanan: HistoryAdapterBulanan

    private lateinit var lySwip: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryAdminBinding.inflate(inflater, container, false)
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


    private fun setupAdapter() {
        mAdapterHarian = AdapterHystoryHarian {
            val intent = Intent(requireActivity(), SaranaActivityAdmin::class.java)
            intent.putExtra(TAG_ID_PENGADUAN, it.id.toString())
            startActivity(intent)
        }

        mAdapterBulanan = HistoryAdapterBulanan {
            val intent = Intent(requireActivity(), SaranaActivityAdmin::class.java)
            intent.putExtra(TAG_ID_PENGADUAN, it.id.toString())
            startActivity(intent)
        }
    }

    private fun setupButtonBackClicked() {
        binding.imgBackHistory.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_history_admin_to_navigation_home_admin)
        }
    }

    private fun getDataUser() {
        // harian
        viewModel.getUser().observe(viewLifecycleOwner) { userLocal ->
            viewModel.getListLaporanHarian(userLocal.getToken)
                .observe(viewLifecycleOwner) { result ->
                    when (result) {
                        is Resource.Loading -> {}
                        is Resource.Success -> {
                            val data = result.data
                            mAdapterHarian.submitList(data.laporan)
                            setupRecyclerViewHarian()
                            val sortedData = result.data.laporan?.sortedByDescending { it.id }
                            mAdapterHarian.submitList(sortedData)

                            mAdapterHarian = mAdapterHarian
                        }

                        is Resource.Error -> {}
                    }
                }
        }

        // bulanan
        viewModel.getUser().observe(viewLifecycleOwner) { userLocal ->
            viewModel.getListLaporanBulanan(userLocal.getToken)
                .observe(viewLifecycleOwner) { result ->
                    when (result) {
                        is Resource.Loading -> {
                            binding.progressBar.isVisible = true
                        }

                        is Resource.Success -> {
                            binding.progressBar.isVisible = false
                            lySwip.isRefreshing = false
                            val data = result.data
                            mAdapterBulanan.submitList(data.laporan)
                            setupRecyclerViewBulanan()
                            val sortedData = result.data.laporan?.sortedByDescending { it.id }
                            mAdapterBulanan.submitList(sortedData)

                            mAdapterBulanan = mAdapterBulanan
                        }

                        is Resource.Error -> {
                            binding.progressBar.isVisible = false
                        }
                    }
                }
        }
    }

    private fun setupRecyclerViewHarian() {
        binding.rvHistoryHariIni.apply {
            adapter = mAdapterHarian
            layoutManager = LinearLayoutManager(requireActivity())
            setHasFixedSize(true)
        }
    }

    private fun setupRecyclerViewBulanan() {
        binding.rvHistoryBulanan.apply {
            adapter = mAdapterBulanan
            layoutManager = LinearLayoutManager(requireActivity())
            setHasFixedSize(true)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}













