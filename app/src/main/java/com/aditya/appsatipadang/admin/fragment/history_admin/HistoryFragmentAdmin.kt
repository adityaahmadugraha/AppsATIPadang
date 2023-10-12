package com.aditya.appsatipadang.admin.fragment.history_admin

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aditya.appsatipadang.R
import com.aditya.appsatipadang.databinding.FragmentHistoryAdminBinding
import com.aditya.appsatipadang.utils.Constant.getToken
import com.aditya.appsatipadang.adapter.AdapterHistoryLaporan
import com.aditya.appsatipadang.admin.ui.sarana_admin.SaranaActivityAdmin
import com.aditya.appsatipadang.data.Resource
import com.aditya.appsatipadang.data.remote.response.ItemLaporaneResponse
import com.aditya.appsatipadang.user.ui.detailstatuslaporan.DetailStatusLaporanActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragmentAdmin : Fragment() {

    private var _binding: FragmentHistoryAdminBinding? = null
    private val viewModel: HistoryAdminViewModel by viewModels()
    private val binding get() = _binding!!

    private lateinit var mAdapter: AdapterHistoryLaporan

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryAdminBinding.inflate(inflater, container, false)
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
            findNavController().navigate(R.id.action_navigation_history_admin_to_navigation_home_admin)
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
            goToDetailScreen(it)
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

    private fun goToDetailScreen(itemLaporaneResponse: ItemLaporaneResponse) {

        val bundle = Bundle().apply {
            putString(DetailStatusLaporanActivity.TAG_TIPE, itemLaporaneResponse.type)
            putString(DetailStatusLaporanActivity.TAG_TANGGAL, itemLaporaneResponse.tanggal)
//            putString(SaranaActivityAdmin.TAG_LOKASI, itemLaporaneResponse.lokasi)






        }
        Intent(requireActivity(), DetailStatusLaporanActivity::class.java).apply {
            putExtra(DetailStatusLaporanActivity.TAG_BUNDLE, bundle)
            startActivity(this)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}













