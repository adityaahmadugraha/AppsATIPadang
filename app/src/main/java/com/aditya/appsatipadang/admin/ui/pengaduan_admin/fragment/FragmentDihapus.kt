package com.aditya.appsatipadang.admin.ui.pengaduan_admin.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.aditya.appsatipadang.admin.ui.pengaduan_admin.PengaduanAdminViewModel
import com.aditya.appsatipadang.data.Resource
import com.aditya.appsatipadang.databinding.FragmentDihapusBinding
import com.aditya.appsatipadang.utils.Constant.getToken
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FragmentDihapus : Fragment() {

    private var _binding: FragmentDihapusBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PengaduanAdminViewModel by viewModels()
    private lateinit var mAdapter: AdapterHistoryDihapus

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDihapusBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getData()
        setupList()
    }

    private fun getData() {
        viewModel.getUser().observe(viewLifecycleOwner) { user ->
            viewModel.getHistoryDihapus(user.getToken).observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        val dataItems = result.data.laporan
                        mAdapter.submitList(dataItems)
                        setupRecyclerView()
                    }

                    is Resource.Error -> {}
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvDihapus.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireActivity())
            setHasFixedSize(true)
        }
    }

    private fun setupList() {
        mAdapter = AdapterHistoryDihapus()

    }
}

