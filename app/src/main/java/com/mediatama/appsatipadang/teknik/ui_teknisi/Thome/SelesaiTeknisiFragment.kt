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
import com.mediatama.appsatipadang.databinding.FragmentSelesaiBinding
import com.mediatama.appsatipadang.teknik.ui_teknisi.laporan.LaporanTeknisiActivity
import com.mediatama.appsatipadang.utils.Constant.IDLAPORAN
import com.mediatama.appsatipadang.utils.Constant.getToken
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelesaiTeknisiFragment : Fragment() {
    private var _binding: FragmentSelesaiBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PengaduanAdminViewModel by viewModels()
    private lateinit var mAdapter: PengaduanAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSelesaiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getData()
        setupList()
    }

    private fun getData() {
        viewModel.getUser().observe(viewLifecycleOwner) {
            viewModel.getLaporanStatus(it.getToken, "selesai").observe(viewLifecycleOwner) { item ->
                when (item) {
                    is com.mediatama.appsatipadang.data.Resource.Loading -> {}
                    is com.mediatama.appsatipadang.data.Resource.Success -> {
                        val dataItem = item.data.laporan
                        mAdapter.submitList(dataItem)
                        setupRecyclerView()
                    }

                    is com.mediatama.appsatipadang.data.Resource.Error -> {}

                }
            }
        }
    }

    private fun setupList() {
        mAdapter = PengaduanAdapter {
            val intent = Intent(requireActivity(), LaporanTeknisiActivity::class.java)
            intent.putExtra(IDLAPORAN, it.id.toString())
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        binding.rvSelesai.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireActivity())
            setHasFixedSize(true)
        }
    }


}