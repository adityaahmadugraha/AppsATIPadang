package com.aditya.appsatipadang.admin.ui.pengaduan_admin.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.aditya.appsatipadang.admin.ui.pengaduan_admin.PengaduanAdapter
import com.aditya.appsatipadang.admin.ui.pengaduan_admin.PengaduanAdminViewModel
import com.aditya.appsatipadang.data.Resource
import com.aditya.appsatipadang.databinding.FragmentDikerjakanBinding
import com.aditya.appsatipadang.teknik.ui_teknisi.laporan.LaporanTeknisiActivity
import com.aditya.appsatipadang.utils.Constant.getToken
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DikerjakanFragment : Fragment() {
    private var _binding: FragmentDikerjakanBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PengaduanAdminViewModel by viewModels()
    private lateinit var mAdapter: PengaduanAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDikerjakanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getData()
        setupList()
    }

    private fun getData() {
        viewModel.getUser().observe(viewLifecycleOwner) {
            viewModel.getLaporanStatus(it.getToken, "sedang dikerjakan")
                .observe(viewLifecycleOwner) { item ->
                    when (item) {
                        is Resource.Loading -> {}

                        is Resource.Success -> {
                            val dataItem = item.data.laporan
                            val filteredDataItem =
                                dataItem?.filter { it.status == "sedang dikerjakan" }
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
            val intent = Intent(requireActivity(), LaporanTeknisiActivity::class.java)
            intent.putExtra(LaporanTeknisiActivity.TAG_ID_TEKNISI, item.id.toString())
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        binding.rvDikerjakan.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireActivity())
            setHasFixedSize(true)
        }
    }


}