package com.aditya.appsatipadang.teknik.ui_teknisi.history

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.aditya.appsatipadang.adapter.AdapterHystoryHarian
import com.aditya.appsatipadang.data.Resource
import com.aditya.appsatipadang.databinding.FragmentHistoryBinding
import com.aditya.appsatipadang.teknik.ui_teknisi.laporan.LaporanTeknisiActivity
import com.aditya.appsatipadang.utils.Constant
import com.aditya.appsatipadang.utils.Constant.getToken
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class THistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HistoryViewModel by viewModels()

    private lateinit var mAdapterHarian: AdapterHystoryHarian
    private lateinit var mAdapterBulanan: HistoryAdapterBulanan

    private lateinit var lySwip: SwipeRefreshLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lySwip = binding.lySwip
        lySwip.setOnRefreshListener {

            getDatalaporan()
        }

        setupList()
        getDatalaporan()
    }

    private fun getDatalaporan() {
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
                    else -> {}
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
                        binding.progressBar.isVisible = false
                        lySwip.isRefreshing = false
                        val data = result.data
                        mAdapterBulanan.submitList(data.laporan)
                        setupRecyclerView()
                    }
                    is Resource.Error -> {
                        binding.progressBar.isVisible = false
                    }

                    else -> {}
                }
            }
        }
    }

    private fun setupList() {
        mAdapterHarian = AdapterHystoryHarian {
            val intent = Intent(requireContext(), LaporanTeknisiActivity::class.java)
            intent.putExtra(Constant.IDLAPORAN, it.id.toString())
            startActivity(intent)
        }

        mAdapterBulanan = HistoryAdapterBulanan {
            val intent = Intent(requireContext(), LaporanTeknisiActivity::class.java)
            intent.putExtra(Constant.IDLAPORAN, it.id.toString())
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        binding.rvHistoryHariIni.apply {
            adapter = mAdapterHarian
            layoutManager = LinearLayoutManager(requireActivity())
            setHasFixedSize(true)
        }

        binding.rvHistoryBulanan.apply {
            adapter = mAdapterBulanan
            layoutManager = LinearLayoutManager(requireActivity())
            setHasFixedSize(true)
        }
    }

}