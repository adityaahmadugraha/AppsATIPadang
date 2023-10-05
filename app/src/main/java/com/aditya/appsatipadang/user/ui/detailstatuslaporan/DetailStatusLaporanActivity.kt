package com.aditya.appsatipadang.user.ui.detailstatuslaporan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.activity.viewModels
import com.aditya.appsatipadang.user.MainActivity
import com.aditya.appsatipadang.user.data.Resource
import com.aditya.appsatipadang.databinding.ActivityDetailStatusLaporanBinding
import com.aditya.appsatipadang.di.Constant.getToken
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailStatusLaporanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailStatusLaporanBinding
    private val viewModel: DetailViewModel by viewModels()
    var id: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailStatusLaporanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        id = intent.getStringExtra("ID_LAPORAN").toString()

        binding.imgBackStatusLaporan.setOnClickListener {
            intent = Intent(this@DetailStatusLaporanActivity, MainActivity::class.java)
            startActivity(intent)
        }


        val bundle = intent.getBundleExtra(TAG_BUNDLE)
        if (bundle != null) {
            binding.apply {
                etNameStatus.text =
                    Editable.Factory.getInstance().newEditable(bundle.getString(TAG_NAMA))
                etJenisStatus.text =
                    Editable.Factory.getInstance().newEditable(bundle.getString(TAG_STATUS))
                etTipe.text = Editable.Factory.getInstance().newEditable(bundle.getString(TAG_TIPE))

                btnBack.setOnClickListener {
                    finish()
                }
            }
        }

        getDataLaporan()
    }


    private fun getDataLaporan() {
        Log.d("IKO_ID:::::::", id.toString())
        viewModel.getUser().observe(this@DetailStatusLaporanActivity) {
            viewModel.getDataLaporan(it.getToken, id)
                .observe(this@DetailStatusLaporanActivity) { item ->
                    when (item) {
                        is Resource.Loading -> {

                        }

                        is Resource.Success -> {
                            val laporan = item.data.laporan
                            binding.apply {
                                etNameStatus.setText(laporan!!.merk.toString())
                            }
                        }

                        is Resource.Error -> {

                        }
                    }
                }
        }
    }

    companion object {
        const val TAG_BUNDLE = "kode"
        const val TAG_NAMA = "nama"
        const val TAG_STATUS = "status"
        const val TAG_TIPE = "tipe"
    }
}