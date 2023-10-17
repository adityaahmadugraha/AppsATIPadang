package com.aditya.appsatipadang.admin.fragment.home_admin

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.aditya.appsatipadang.BuildConfig
import com.aditya.appsatipadang.BuildConfig.BASE_URL
import com.aditya.appsatipadang.adapter.AdapterLaporan
import com.aditya.appsatipadang.admin.fragment.history_admin.HistoryAdminViewModel
import com.aditya.appsatipadang.admin.ui.kamtibmas_admin.AddUserActivity

import com.aditya.appsatipadang.admin.ui.sarana_admin.SaranaActivityAdmin
import com.aditya.appsatipadang.admin.ui.sarana_admin.SaranaActivityAdmin.Companion.TAG_ID_PENGADUAN
import com.aditya.appsatipadang.admin.ui.status_admin.StatusActivityAdmin
import com.aditya.appsatipadang.data.Resource
import com.aditya.appsatipadang.databinding.FragmentHomeAdminBinding
import com.aditya.appsatipadang.supervisor.LaporanKeseluruhanActivity
import com.aditya.appsatipadang.utils.Constant.getToken
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import java.net.URL


@AndroidEntryPoint
class HomeFragmentAdmin : Fragment() {

    private var _binding: FragmentHomeAdminBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HistoryAdminViewModel by viewModels()

    private lateinit var mAdapter: AdapterLaporan

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeAdminBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cardSaranaAdmin.setOnClickListener {
            val intent = Intent(activity, SaranaActivityAdmin::class.java)
            startActivity(intent)
        }
        binding.cardAddUser.setOnClickListener {
            val intent = Intent(activity, AddUserActivity::class.java)
            startActivity(intent)
        }
        binding.cardLaporan.setOnClickListener {
            val intent = Intent(activity, LaporanKeseluruhanActivity::class.java)
            startActivity(intent)
        }

        getDataUser()
        setupList()
    }

    private fun getDataUser() {
        viewModel.getUser().observe(viewLifecycleOwner) { userLocal ->
            binding.tvName.text = userLocal.name
            binding.let {
                Glide.with(requireContext())
                    .load(BuildConfig.IMAGE_URL + userLocal.foto)
                    .error(android.R.color.darker_gray)
                    .into(it.imgProfil) }


            viewModel.getListLaporan(userLocal.getToken).observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Resource.Loading -> {
                        binding.progressBar.isVisible = true
                    }

                    is Resource.Success -> {
                        binding.progressBar.isVisible = false

                        mAdapter.submitList(result.data.laporan)
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

    private fun setupList() {
        mAdapter = AdapterLaporan {
            val intent = Intent(requireActivity(), SaranaActivityAdmin::class.java)
            intent.putExtra(TAG_ID_PENGADUAN, it.id.toString())
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        binding.rvLaporanHome.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            setHasFixedSize(true)

            viewModel.getUser().observe(viewLifecycleOwner) { userLocal ->
                binding.tvName.text = userLocal.name
                binding.let {
                    Glide.with(requireContext())
                        .load(BuildConfig.IMAGE_URL + userLocal.foto)
                        .error(android.R.color.darker_gray)
                        .into(it.imgProfil)
                }

                viewModel.getListLaporan(userLocal.getToken).observe(viewLifecycleOwner) { result ->
                    when (result) {
                        is Resource.Loading -> {
                            binding.progressBar.isVisible = true
                        }

                        is Resource.Success -> {
                            binding.progressBar.isVisible = false

                            val sortedData = result.data.laporan?.sortedByDescending { it.id }
                            mAdapter.submitList(sortedData)

                            adapter = mAdapter
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

}
