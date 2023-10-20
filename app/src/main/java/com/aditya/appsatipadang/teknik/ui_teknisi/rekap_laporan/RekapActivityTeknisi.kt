package com.aditya.appsatipadang.teknik.ui_teknisi.rekap_laporan

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.aditya.appsatipadang.data.Resource
import com.aditya.appsatipadang.databinding.ActivityRekapTeknisiBinding
import com.aditya.appsatipadang.utils.Constant.getToken
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RekapActivityTeknisi : AppCompatActivity() {

    private lateinit var binding: ActivityRekapTeknisiBinding
    private val viewModel: RekapLaporanTeknisiViewModel by viewModels()
//    private lateinit var mAdapter: AdapterRakapLaporanTeknisi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRekapTeknisiBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }

//    private fun setupList() {
//        mAdapter = AdapterRakapLaporanTeknisi ()
//        setupRecyclerView()
//    }

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
//
//                        mAdapter.submitList(sortedData)
                    }

                    is Resource.Error -> {
                        binding.progressBar.isVisible = false
                        Toast.makeText(
                            this@RekapActivityTeknisi,
                            result.error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

//    private fun setupRecyclerView() {
//        binding.rvRekapTeknisi.apply {
//            adapter = mAdapter
//            layoutManager = LinearLayoutManager(this@RekapActivityTeknisi)
//            setHasFixedSize(true)
//        }
//    }
}