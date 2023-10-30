package com.aditya.appsatipadang.teknik.ui_teknisi.detailpenyerahan

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.aditya.appsatipadang.BuildConfig
import com.aditya.appsatipadang.data.Resource
import com.aditya.appsatipadang.databinding.ActivityDetailPenyerahanBinding
import com.aditya.appsatipadang.teknik.ActivityTeknik
import com.aditya.appsatipadang.utils.Constant.getToken
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityDetailPenyerahan : AppCompatActivity() {

    private lateinit var binding: ActivityDetailPenyerahanBinding
    private val viewModel: ViewModelDetailPenyerahan by viewModels()

    companion object {
        const val TAG_TANGGAL = "tanggal"
        const val TAG_NAMA_PENERIMA = "namaPenerima"
        const val TAG_NO = "noPengaduan"
        const val TAG_FOTO = "foto"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailPenyerahanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        val tanggal = intent.getStringExtra(TAG_TANGGAL)

        binding.imgBack.setOnClickListener {
            val intent = Intent(this@ActivityDetailPenyerahan, ActivityTeknik::class.java)
            startActivity(intent)
        }

        getDataPenyerahan(tanggal)
    }

    private fun getDataPenyerahan(tanggal: String?) {
        viewModel.getUser().observe(this) { user ->
            viewModel.getListPenyerahan(user.getToken).observe(this) { item ->
                when (item) {
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {

                        val penyerahanList = item.data.penyerahan

                        val penyerahan = penyerahanList?.firstOrNull { it?.tglDiserahkan == tanggal }

                        penyerahan?.let {
                            binding.etTanggal.setText(it.tglDiserahkan.toString())
                            binding.etNamePenerima.setText(it.namaPenerima.toString())
                            binding.etNoPenyerahan.setText(it.noPengaduan.toString())
                            Glide.with(this@ActivityDetailPenyerahan)
                                .load(BuildConfig.IMAGE_URL + penyerahan?.foto)
                                .into(binding.imgBukti)

                        }
                    }
                    is Resource.Error -> {

                    }
                }
            }
        }
    }
}
