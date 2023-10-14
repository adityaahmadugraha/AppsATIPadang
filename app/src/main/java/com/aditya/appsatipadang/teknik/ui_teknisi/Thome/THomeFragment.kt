package com.aditya.appsatipadang.teknik.ui_teknisi.Thome

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.aditya.appsatipadang.adapter.AdapterLaporanTeknisi
import com.aditya.appsatipadang.admin.ui.status_admin.StatusActivityAdmin
import com.aditya.appsatipadang.data.Resource
import com.aditya.appsatipadang.databinding.FragmentTHomeBinding
import com.aditya.appsatipadang.teknik.ui_teknisi.nontifikasi_laporan.ActivityNontofikasiLaporanTeknisi
import com.aditya.appsatipadang.utils.Constant.getToken
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class THomeFragment : Fragment() {

    private var _binding: FragmentTHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: THomeViewModel by viewModels()
    private lateinit var mAdapter: AdapterLaporanTeknisi

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cardLaporan.setOnClickListener {
            val intent = Intent(activity, ActivityNontofikasiLaporanTeknisi::class.java)
            startActivity(intent)
        }
        binding.cardRekapLaporan.setOnClickListener {
            val intent = Intent(activity, StatusActivityAdmin::class.java)
            startActivity(intent)
        }

        getDataUser()
        setupList()

    }

    private fun setupList() {
        mAdapter = AdapterLaporanTeknisi {

        }
    }

    private fun setupRecyclerView() {
        binding?.rvLaporanHome?.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireActivity())
            setHasFixedSize(true)
        }
    }

    private fun getDataUser() {
        viewModel.getUser().observe(viewLifecycleOwner) { userLocal ->
            binding.tvName.text = userLocal.name


            viewModel.getListPengerjaan(userLocal.getToken).observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Resource.Loading -> {
                        binding.progressBar.isVisible = true
                    }

                    is Resource.Success -> {
                        binding.progressBar.isVisible = false

                        val latestFiveData = if (result.data.laporan?.size!! > 5)
                            result.data.laporan?.let {
                                result.data.laporan.subList(
                                    result.data.laporan.size - 5,
                                    it.size
                                )
                            }
                        else
                            result.data.laporan

                        mAdapter.submitListReversed(latestFiveData)
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

        }
    }
}