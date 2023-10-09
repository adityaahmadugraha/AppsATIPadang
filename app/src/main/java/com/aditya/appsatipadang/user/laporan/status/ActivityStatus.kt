package com.aditya.appsatipadang.user.laporan.status

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.aditya.appsatipadang.adapter.AdapterStatusLaporan
import com.aditya.appsatipadang.data.Resource
import com.aditya.appsatipadang.data.remote.response.ItemLaporaneResponse
import com.aditya.appsatipadang.databinding.ActivityStatusBinding
import com.aditya.appsatipadang.user.utils.Constant.getToken
import com.aditya.appsatipadang.ui.detailstatuslaporan.DetailStatusLaporanActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ActivityStatus : AppCompatActivity() {

    private lateinit var binding: ActivityStatusBinding
    private val viewModel: StatusViewModel by viewModels()
    private lateinit var mAdapter: AdapterStatusLaporan

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStatusBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.imgBackStatusLaporan.setOnClickListener {
            finish()
        }

        setupList()
        getDataUser()
    }

    private fun setupList() {
        mAdapter = AdapterStatusLaporan {
            goToDetailScreen(it)

        }
        setupRecyclerView()
    }

    private fun goToDetailScreen(itemLaporaneResponse: ItemLaporaneResponse) {

        val bundle = Bundle().apply {
            putString(DetailStatusLaporanActivity.TAG_TIPE, itemLaporaneResponse.type)
            putString(DetailStatusLaporanActivity.TAG_TANGGAL, itemLaporaneResponse.tanggal)
            putString(DetailStatusLaporanActivity.TAG_LOKASI, itemLaporaneResponse.lokasi)

        }
        Intent(this@ActivityStatus, DetailStatusLaporanActivity::class.java).apply {
            putExtra(DetailStatusLaporanActivity.TAG_BUNDLE, bundle)
            startActivity(this)
        }
    }

    private fun getDataUser() {

        viewModel.getUser().observe(this) {
            viewModel.getListLaporan(it.getToken).observe(this) { result ->
                Log.d(ContentValues.TAG, "getDataUseroken::::: $.it.getToken")
                when (result) {
                    is Resource.Loading -> {
                        binding.progressBar.isVisible = true
                    }

                    is Resource.Success -> {
                        binding.progressBar.isVisible = false
                        Log.d(ContentValues.TAG, "listadapter::::::: ${result.data}")
                        mAdapter.submitList(result.data.laporan)
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