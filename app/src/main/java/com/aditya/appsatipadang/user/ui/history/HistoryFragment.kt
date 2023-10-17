package com.aditya.appsatipadang.user.ui.history

import android.content.Intent
import android.os.Bundle
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
import com.aditya.appsatipadang.adapter.AdapterHystoryHarian
import com.aditya.appsatipadang.adapter.AdapterLaporanBulanan
import com.aditya.appsatipadang.data.Resource
import com.aditya.appsatipadang.databinding.FragmentHistoryBinding
import com.aditya.appsatipadang.user.ui.detailstatuslaporan.DetailStatusLaporanActivity
import com.aditya.appsatipadang.user.ui.home.HomeViewModel
import com.aditya.appsatipadang.utils.Constant.getToken
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val viewModel: HomeViewModel by viewModels()
    private val binding get() = _binding!!

    private lateinit var mAdapterBulanan: AdapterLaporanBulanan
    private lateinit var mAdapterHarian: AdapterHystoryHarian

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupButtonBackClicked()
        getDataUser()
        goSettup()
    }

    private fun setupButtonBackClicked() {
        binding.imgBackHistory.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_history_to_navigation_home)
        }
    }

    private fun getDataUser() {

        viewModel.getUser().observe(viewLifecycleOwner) { userLocal ->

            viewModel.getListLaporanHarian(userLocal.getToken).observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Resource.Loading -> {
                        binding.progressBar.isVisible = true
                    }

                    is Resource.Success -> {
                        binding.progressBar.isVisible = false
                        val sortedData = result.data.laporan?.sortedByDescending { it.id }
                        mAdapterHarian.submitList(sortedData)
                        setupRecyclerView()
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


            viewModel.getListLaporanBulanan(userLocal.getToken).observe(viewLifecycleOwner) { result ->
                    when (result) {
                        is Resource.Loading -> {
                            binding.progressBar.isVisible = true
                        }

                        is Resource.Success -> {
                            binding.progressBar.isVisible = false
                            val sortedData = result.data.laporan?.sortedByDescending { it.id }
                            mAdapterBulanan.submitList(sortedData)
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

    private fun goSettup(){
        mAdapterBulanan = AdapterLaporanBulanan {
            Intent(requireActivity(), DetailStatusLaporanActivity::class.java).apply {
                putExtra(DetailStatusLaporanActivity.TAG_IDLAPORAN, it.id.toString())
                startActivity(this)
            }
        }

        mAdapterHarian = AdapterHystoryHarian {
            Intent(requireActivity(), DetailStatusLaporanActivity::class.java).apply {
                putExtra(DetailStatusLaporanActivity.TAG_IDLAPORAN, it.id.toString())
                startActivity(this)
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}













