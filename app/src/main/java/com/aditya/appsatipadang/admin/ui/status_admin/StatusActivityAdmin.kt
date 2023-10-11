package com.aditya.appsatipadang.admin.ui.status_admin

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.aditya.appsatipadang.adapter.AdapterStatusLaporan
import com.aditya.appsatipadang.data.Resource
import com.aditya.appsatipadang.databinding.ActivityStatusAdminBinding
import com.aditya.appsatipadang.utils.Constant.getToken
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class StatusActivityAdmin : AppCompatActivity() {
    private lateinit var binding: ActivityStatusAdminBinding
    private val viewModel: StatusAdminViewModel by viewModels()


    private lateinit var mAdapter: AdapterStatusLaporan

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStatusAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setupList()
        getDataUser()
    }

    private fun setupList() {
        mAdapter = AdapterStatusLaporan(


        )
        setupRecyclerView()
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

                        val sortedData = result.data.laporan?.sortedByDescending { it.id }

                        mAdapter.submitList(sortedData)
                    }

                    is Resource.Error -> {
                        binding.progressBar.isVisible = false
                        Toast.makeText(
                            this@StatusActivityAdmin,
                            result.error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvLaporanAdmin.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(this@StatusActivityAdmin)
            setHasFixedSize(true)
        }
    }
}
