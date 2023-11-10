package com.mediatama.appsatipadang.teknik.ui_teknisi.penyerahan

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mediatama.appsatipadang.databinding.FragmentPenyerahanBinding
import com.mediatama.appsatipadang.teknik.ui_teknisi.detailpenyerahan.ActivityDetailPenyerahan
import com.mediatama.appsatipadang.utils.Constant.getToken
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PenyerahanFragment : Fragment() {

    private var _binding: FragmentPenyerahanBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PenyerahanViewModel by viewModels()
    private lateinit var mAdapter: AdapterPenyerahan

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPenyerahanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getData()
        setupList()
    }
    private fun getData() {
        viewModel.getUser().observe(viewLifecycleOwner) { user ->
            viewModel.getListPenyerahan(user.getToken).observe(viewLifecycleOwner) { result ->
                when (result) {
                    is com.mediatama.appsatipadang.data.Resource.Loading -> {}
                    is com.mediatama.appsatipadang.data.Resource.Success -> {
                        val dataItems = result.data.penyerahan
                        val sortedData = dataItems?.sortedByDescending { it?.id }
                        mAdapter.submitList(sortedData)
                        setupRecyclerView()
                    }
                    is com.mediatama.appsatipadang.data.Resource.Error -> {}
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
        mAdapter = AdapterPenyerahan { penyerahanItem ->
            val intent = Intent(requireActivity(), ActivityDetailPenyerahan::class.java)
            intent.putExtra(ActivityDetailPenyerahan.TAG_TANGGAL, penyerahanItem.tglDiserahkan)
            intent.putExtra(ActivityDetailPenyerahan.TAG_NAMA_PENERIMA, penyerahanItem.namaPenerima)
            intent.putExtra(ActivityDetailPenyerahan.TAG_NO, penyerahanItem.noPengaduan)
            intent.putExtra(ActivityDetailPenyerahan.TAG_FOTO, penyerahanItem.foto)

            startActivity(intent)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}