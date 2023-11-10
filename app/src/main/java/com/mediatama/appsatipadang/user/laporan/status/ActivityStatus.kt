package com.mediatama.appsatipadang.user.laporan.status

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.mediatama.appsatipadang.adapter.AdapterLaporan
import com.mediatama.appsatipadang.data.Resource
import com.mediatama.appsatipadang.data.remote.response.ItemLaporaneResponse
import com.mediatama.appsatipadang.databinding.ActivityStatusBinding
import com.mediatama.appsatipadang.user.ui.detailstatuslaporan.DetailStatusLaporanActivity
import com.mediatama.appsatipadang.utils.Constant.getToken
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ActivityStatus : AppCompatActivity() {

    private lateinit var binding: ActivityStatusBinding
    private val viewModel: StatusViewModel by viewModels()
    private lateinit var mAdapter: AdapterLaporan
    private lateinit var lySwip: SwipeRefreshLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStatusBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgBackStatusLaporan.setOnClickListener {
            finish()
        }

        lySwip = binding.lySwip
        lySwip.setOnRefreshListener {

            getDataUser()
        }


        setupList()
        getDataUser()
    }

    private fun setupList() {
        mAdapter = AdapterLaporan() {
            goToDetailScreen(it)

        }
        setupRecyclerView()
    }

    private fun goToDetailScreen(itemLaporaneResponse: ItemLaporaneResponse) {
        Log.d("ActivityStatus::::::::::::::::::", "ID Laporan: ${itemLaporaneResponse.id}")
        val bundle = Bundle().apply {
            putString(DetailStatusLaporanActivity.TAG_IDLAPORAN, itemLaporaneResponse.id.toString())
            putString(DetailStatusLaporanActivity.TAG_TIPE, itemLaporaneResponse.type)
            putString(DetailStatusLaporanActivity.TAG_TANGGAL, itemLaporaneResponse.tanggal)
            putString(DetailStatusLaporanActivity.TAG_JENIS, itemLaporaneResponse.jenis)
            putString(DetailStatusLaporanActivity.TAG_STATUS, itemLaporaneResponse.status)
        }
        Intent(this@ActivityStatus, DetailStatusLaporanActivity::class.java).apply {
            putExtras(bundle)
            startActivity(this)
        }


    }


    private fun getDataUser() {

        viewModel.getUser().observe(this) { it ->
            viewModel.getListLaporan(it.getToken).observe(this) { result ->
                Log.d(ContentValues.TAG, "getDataUseroken::::: $.it.getToken")
                when (result) {
                    is Resource.Loading -> {
                        binding.progressBar.isVisible = true
                    }

                    is Resource.Success -> {
                        binding.progressBar.isVisible = false
                        lySwip.isRefreshing = false
                        Log.d(ContentValues.TAG, "listadapter::::::: ${result.data}")

                        val sortedData = result.data.laporan?.sortedByDescending { it.id }

                        mAdapter.submitList(sortedData)
                    }

                    is Resource.Error -> {
                        binding.progressBar.isVisible = false
                        Toast.makeText(
                            this@ActivityStatus,
                            result.error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvStatusLaporan.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(this@ActivityStatus)
            setHasFixedSize(true)
        }
    }
}