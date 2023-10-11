package com.aditya.appsatipadang.user.ui.detailstatuslaporan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.activity.viewModels
import com.aditya.appsatipadang.user.MainActivity
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
        const val TAG_LOKASI = "lokasi"
        const val TAG_STATUS = "status"
//        const val TAG_FOTO = "foto"


    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailStatusLaporanBinding.inflate(layoutInflater)
        setContentView(binding.root)


        id = intent.getStringExtra("ID_LAPORAN").toString()


        binding.imgBackStatusLaporan.setOnClickListener {
            intent = Intent(this@DetailStatusLaporanActivity, MainActivity::class.java)
            startActivity(intent)
        }


        val bundle = intent.getBundleExtra(TAG_BUNDLE)
        if (bundle != null) {
            binding.apply {

                etType.text = Editable.Factory.getInstance().newEditable(bundle.getString(TAG_TIPE))

                etTanggal.text = Editable.Factory.getInstance().newEditable(bundle.getString(
                    TAG_TANGGAL
                ))

                etLokasi.text = Editable.Factory.getInstance().newEditable(bundle.getString(
                    TAG_LOKASI
                ))

                tvStatus.text = Editable.Factory.getInstance().newEditable(bundle.getString(
                    TAG_STATUS
                ))





                btnBack.setOnClickListener {
                    finish()
                }
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
                                etType.setText(laporan!!.merk.toString())
                            }
                        }

                        is Resource.Error -> {

                        }
                    }
                }
        }
    }


}