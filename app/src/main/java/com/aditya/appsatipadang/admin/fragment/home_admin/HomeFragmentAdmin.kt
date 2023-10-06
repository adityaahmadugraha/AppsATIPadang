package com.aditya.appsatipadang.admin.fragment.home_admin

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.aditya.appsatipadang.R
import com.aditya.appsatipadang.admin.ui.kamtibmas_admin.KamtibmasActivityAdmin
import com.aditya.appsatipadang.admin.ui.prasarana_admin.PrasaranaActivityAdmin
import com.aditya.appsatipadang.admin.ui.sarana_admin.SaranaActivityAdmin
import com.aditya.appsatipadang.admin.ui.status_admin.StatusActivityAdmin
import com.aditya.appsatipadang.databinding.FragmentHomeAdminBinding
import com.aditya.appsatipadang.databinding.FragmentHomeBinding
import com.aditya.appsatipadang.di.Constant.getToken
import com.aditya.appsatipadang.user.adapter.AdapterLaporan
import com.aditya.appsatipadang.data.Resource
import com.aditya.appsatipadang.data.remote.response.ItemLaporaneResponse
import com.aditya.appsatipadang.user.laporan.kamtibmas.ActivityKamtibmas
import com.aditya.appsatipadang.user.laporan.prasarana.ActivityPrasarana
import com.aditya.appsatipadang.user.laporan.sarana.SaranaActivity
import com.aditya.appsatipadang.user.laporan.status.ActivityStatus
import com.aditya.appsatipadang.ui.detailstatuslaporan.DetailStatusLaporanActivity
import com.aditya.appsatipadang.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragmentAdmin : Fragment() {

    private var _binding: FragmentHomeAdminBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeAdminViewModel by viewModels()

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
        binding.cardPrasaranaAdmin.setOnClickListener {
            val intent = Intent(activity, PrasaranaActivityAdmin::class.java)
            startActivity(intent)
        }
        binding.cardKamtibmasAdmin.setOnClickListener {
            val intent = Intent(activity, KamtibmasActivityAdmin::class.java)
            startActivity(intent)
        }
        binding.cardStatusAdmin.setOnClickListener {
            val intent = Intent(activity, StatusActivityAdmin::class.java)
            startActivity(intent)
        }

        getDataUser()
        setupList()
    }

    private fun getDataUser() {
        viewModel.getUser().observe(viewLifecycleOwner) { userLocal ->
            binding.tvName.text = userLocal.name


            viewModel.getListLaporan(userLocal.getToken).observe(viewLifecycleOwner) { result ->
                Log.d(ContentValues.TAG, "getDataUser: ${userLocal.getToken}")
                when (result) {
                    is Resource.Loading -> {
                        binding.progressBar.isVisible = true
                    }

                    is Resource.Success -> {
                        binding.progressBar.isVisible = false


                        Log.d(ContentValues.TAG, "listadapter::::::: ${result.data}")

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
            goToDetailScreen(it)
        }
    }

    private fun goToDetailScreen(itemLaporaneResponse: ItemLaporaneResponse) {

        val bundle = Bundle().apply {
//            putString(TAG_KODE, pesanan.kdPesanan)
            putString(DetailStatusLaporanActivity.TAG_NAMA, itemLaporaneResponse.type)
            putString(DetailStatusLaporanActivity.TAG_STATUS, itemLaporaneResponse.merk)
            putString(DetailStatusLaporanActivity.TAG_TIPE, itemLaporaneResponse.lokasi)


        }
        Intent(requireActivity(), DetailStatusLaporanActivity::class.java).apply {
            putExtra(DetailStatusLaporanActivity.TAG_BUNDLE, bundle)
            startActivity(this)
        }
    }

    private fun setupRecyclerView() {
        binding?.rvLaporanHome?.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireActivity())
            setHasFixedSize(true)
        }
    }
}
