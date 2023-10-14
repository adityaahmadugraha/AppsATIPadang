package com.aditya.appsatipadang.supervisor.ui_supervisor

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aditya.appsatipadang.R
import com.aditya.appsatipadang.adapter.AdapterHistoryLaporan
import com.aditya.appsatipadang.admin.fragment.history_admin.HistoryAdminViewModel
import com.aditya.appsatipadang.data.Resource
import com.aditya.appsatipadang.databinding.FragmentSppHistoryBinding
import com.aditya.appsatipadang.utils.Constant.getToken
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SppHistoryFragment : Fragment() {

    private var _binding: FragmentSppHistoryBinding? = null
    private val viewModel: HistoryAdminViewModel by viewModels()
    private lateinit var mAdapter: AdapterHistoryLaporan
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
        setupRecyclerView()
        getDataUser()
    }

    private fun setupButtonBackClicked() {
        binding.imgBackHistory.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_history_spp_to_navigation_home_spp)
        }
    }

    private fun getDataUser() {

        viewModel.getUser().observe(viewLifecycleOwner) { userLocal ->
            viewModel.getListLaporan(userLocal.getToken).observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Resource.Loading -> {
                        binding.progressBar.isVisible = true
                    }
                    is Resource.Success -> {
                        binding.progressBar.isVisible = false

                        Log.d(ContentValues.TAG, "listHistory::::::: ${result.data}")

                        mAdapter.submitList(result.data.laporan)
                    }
                    is Resource.Error -> {
                        binding.progressBar.isVisible = false
                        Toast.makeText(
                            requireActivity(),
                            result.error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            viewModel.getListLaporanHarian(userLocal.getToken).observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Resource.Loading -> {
                        binding.progressBar.isVisible = true
                    }
                    is Resource.Success -> {
                        binding.progressBar.isVisible = false

                        Log.d(ContentValues.TAG, "listHistory::::::: ${result.data}")

                        mAdapter.submitList(result.data.laporan)
                    }
                    is Resource.Error -> {
                        binding.progressBar.isVisible = false
                        Toast.makeText(
                            requireActivity(),
                            result.error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

        }
    }

    private fun setupRecyclerView() {
        mAdapter = AdapterHistoryLaporan {

        }
        binding.rvHistoryBulanan.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireActivity())
            setHasFixedSize(true)
        }
        binding.rvHistoryHariIni.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireActivity())
            setHasFixedSize(true)
        }
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}













