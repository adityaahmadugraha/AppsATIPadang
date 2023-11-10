package com.mediatama.appsatipadang.teknik.ui_teknisi.nontifikasi_laporan

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mediatama.appsatipadang.databinding.ActivityNontofikasiLaporanTeknisiBinding
import com.mediatama.appsatipadang.teknik.ActivityTeknik
import com.mediatama.appsatipadang.teknik.ui_teknisi.laporan.LaporanTeknisiActivity
import com.mediatama.appsatipadang.utils.Constant.IDLAPORAN
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityNontofikasiLaporanTeknisi : AppCompatActivity() {

    private lateinit var binding: ActivityNontofikasiLaporanTeknisiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNontofikasiLaporanTeknisiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgBack.setOnClickListener {
            intent = Intent(this@ActivityNontofikasiLaporanTeknisi,ActivityTeknik::class.java)
            startActivity(intent)
        }

        binding.btnCekDetailLaporan.setOnClickListener {
            val idLaporan = intent.getStringExtra(IDLAPORAN).toString()
            Log.i("IDLAPORAN:::", idLaporan)
            if (idLaporan != "null"){
                intent = Intent(this@ActivityNontofikasiLaporanTeknisi, LaporanTeknisiActivity::class.java)
                intent.putExtra(IDLAPORAN, idLaporan)
                startActivity(intent)
            } else{
                Toast.makeText(this, "Belum Ada Pengaduan.", Toast.LENGTH_SHORT).show()
            }
        }
    }



}