package com.mediatama.appsatipadang.admin.ui.pengaduan_admin.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mediatama.appsatipadang.admin.ui.pengaduan_admin.PengaduanAdapter
import com.mediatama.appsatipadang.admin.ui.pengaduan_admin.PengaduanAdminViewModel
import com.mediatama.appsatipadang.admin.ui.sarana_admin.SaranaActivityAdmin
import com.mediatama.appsatipadang.databinding.FragmentDiterimaBinding
import com.mediatama.appsatipadang.data.Resource
import com.mediatama.appsatipadang.utils.Constant.getToken
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DiterimaFragment : Fragment() {
    private var _binding: FragmentDiterimaBinding? = null
    private val binding get() = _binding!!

    private val viewModel : PengaduanAdminViewModel by viewModels()
    private lateinit var mAdapter: PengaduanAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiterimaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
        setupList()
    }

    private fun getData() {
        viewModel.getUser().observe(viewLifecycleOwner){
            viewModel.getLaporanStatus(it.getToken,"sudah diterima admin").observe(viewLifecycleOwner){ item ->
                when(item){
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        val dataItem = item.data.laporan
                        val filteredDataItem =
                            dataItem?.filter { it.status == "sudah diterima admin" }
                        mAdapter.submitList(filteredDataItem)
                        setupRecyclerView()
                    }
                    is Resource.Error -> {}

                }
            }
        }
    }
    private fun setupList() {
        mAdapter = PengaduanAdapter { item ->
            val intent = Intent(requireActivity(), SaranaActivityAdmin::class.java)
            intent.putExtra(SaranaActivityAdmin.TAG_ID_PENGADUAN, item.id.toString())
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        binding.rvDiterima.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireActivity())
            setHasFixedSize(true)
        }
    }

}