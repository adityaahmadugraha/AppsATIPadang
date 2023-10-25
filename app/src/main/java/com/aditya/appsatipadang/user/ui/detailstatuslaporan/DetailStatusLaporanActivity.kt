package com.aditya.appsatipadang.user.ui.detailstatuslaporan

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.aditya.appsatipadang.R
import com.aditya.appsatipadang.data.Resource
import com.aditya.appsatipadang.databinding.ActivityDetailStatusLaporanBinding
import com.aditya.appsatipadang.utils.Constant.getToken
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailStatusLaporanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailStatusLaporanBinding
    private val viewModel: DetailViewModel by viewModels()
    var id: String = ""

    companion object {
        const val TAG_BUNDLE = "kode"
        const val TAG_TIPE = "tipe"
        const val TAG_TANGGAL = "tanggal"
        const val TAG_JENIS = "jenis"
        const val TAG_STATUS = "status"
        const val TAG_IDLAPORAN = "id_laporan"


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailStatusLaporanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        id = intent.getStringExtra(TAG_IDLAPORAN).toString()

        binding.apply {
            imgBackStatusLaporan.setOnClickListener {
                finish()
            }
            btnBack.setOnClickListener {
                finish()
            }
        }

        getDataLaporan()
    }


    private fun getDataLaporan() {
        Log.d("IKO_ID:::::::", id)
        viewModel.getUser().observe(this@DetailStatusLaporanActivity) {
            viewModel.getDataLaporan(it.getToken, id)
                .observe(this@DetailStatusLaporanActivity) { item ->
                    when (item) {
                        is Resource.Loading -> {

                        }

                        is Resource.Success -> {
                            val laporan = item.data.laporan
                            binding.apply {
                                etType.setText(laporan?.type.toString())
                                etTanggal.setText(laporan?.tanggal.toString())
                                etLokasi.setText(laporan?.lokasi.toString())
                                val status = laporan?.status.toString()
                                tvStatus.text = status

                                when (status) {
                                    "sudah diterima admin" -> {
                                        tvStatus.setTextColor(resources.getColor(R.color.blue))
                                    }

                                    "sedang dikerjakan" -> {
                                        tvStatus.setTextColor(resources.getColor(R.color.orange))
                                    }

                                    "selesai" -> {
                                        tvStatus.setTextColor(resources.getColor(R.color.selesai))
                                    }

                                    else -> {
                                        tvStatus.setTextColor(Color.BLACK)
                                    }
                                }
                            }
                        }


                        is Resource.Error -> {

                        }
                    }
                }
        }
    }


}