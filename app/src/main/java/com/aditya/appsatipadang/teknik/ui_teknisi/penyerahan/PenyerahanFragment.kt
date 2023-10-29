package com.aditya.appsatipadang.teknik.ui_teknisi.penyerahan

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.aditya.appsatipadang.R
import com.aditya.appsatipadang.admin.ui.pengaduan_admin.PengaduanAdapter
import com.aditya.appsatipadang.admin.ui.pengaduan_admin.PengaduanAdminViewModel
import com.aditya.appsatipadang.databinding.FragmentPenyerahanBinding
import com.aditya.appsatipadang.teknik.ui_teknisi.laporan.LaporanTeknisiActivity
import com.aditya.appsatipadang.utils.Constant
import com.aditya.appsatipadang.utils.Constant.getToken
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PenyerahanFragment : Fragment() {

    private var _binding: FragmentPenyerahanBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PengaduanAdminViewModel by viewModels()
    private lateinit var mAdapter: PengaduanAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_penyerahan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        getData()
        setupList()
    }

    private fun getData() {
        viewModel.getUser().observe(viewLifecycleOwner){
            viewModel.getLaporanStatus(it.getToken,"sedang dikerjakan").observe(viewLifecycleOwner){ item ->
                when(item){
                    is com.aditya.appsatipadang.data.Resource.Loading -> {}
                    is com.aditya.appsatipadang.data.Resource.Success -> {
                        val dataItem = item.data.laporan
                        mAdapter.submitList(dataItem)
                        setupRecyclerView()
                    }
                    is com.aditya.appsatipadang.data.Resource.Error -> {}

                }
            }
        }
    }


    private fun setupRecyclerView() {
        binding.rvPenyerahan.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireActivity())
            setHasFixedSize(true)
        }
    }

    private fun setupList() {
//        mAdapter = PengaduanAdapter {item ->
//            val intent = Intent(requireActivity(), LaporanTeknisiActivity::class.java)
//            intent.putExtra(Constant.IDLAPORAN, item.id.toString())
//            startActivity(intent)
//        }
    }




}