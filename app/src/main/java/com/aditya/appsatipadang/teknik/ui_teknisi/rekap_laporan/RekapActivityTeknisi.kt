package com.aditya.appsatipadang.teknik.ui_teknisi.rekap_laporan

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.aditya.appsatipadang.adapter.AdapterStatusLaporan
import com.aditya.appsatipadang.data.Resource
import com.aditya.appsatipadang.data.remote.response.ItemLaporaneResponse
import com.aditya.appsatipadang.databinding.ActivityRekapTeknisiBinding
import com.aditya.appsatipadang.user.laporan.status.StatusViewModel
import com.aditya.appsatipadang.user.ui.detailstatuslaporan.DetailStatusLaporanActivity
import com.aditya.appsatipadang.utils.Constant.getToken

class RekapActivityTeknisi : AppCompatActivity() {

    private lateinit var binding: ActivityRekapTeknisiBinding
    private val viewModel: RekapLaporanTeknisiViewModel by viewModels()
    private lateinit var mAdapter: AdapterStatusLaporan

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRekapTeknisiBinding.inflate(layoutInflater)
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
            putString(DetailStatusLaporanActivity.TAG_STATUS, itemLaporaneResponse.status)

        }
        Intent(this@ActivityStatus, DetailStatusLaporanActivity::class.java).apply {
            putExtra(DetailStatusLaporanActivity.TAG_BUNDLE, bundle)
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

                        Log.d(ContentValues.TAG, "listadapter::::::: ${result.data}")

                        val sortedData = result.data.laporan?.sortedByDescending {it.id}

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