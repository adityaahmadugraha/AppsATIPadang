package com.aditya.appsatipadang.teknik.ui_teknisi.nontifikasi_laporan

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.aditya.appsatipadang.R
import com.aditya.appsatipadang.databinding.ActivityNontofikasiLaporanTeknisiBinding
import com.aditya.appsatipadang.teknik.ui_teknisi.laporan.LaporanTeknisiActivity

class ActivityNontofikasiLaporanTeknisi : AppCompatActivity() {

    private lateinit var binding: ActivityNontofikasiLaporanTeknisiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNontofikasiLaporanTeknisiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCekDetailLaporan.setOnClickListener {
            intent =
                Intent(this@ActivityNontofikasiLaporanTeknisi, LaporanTeknisiActivity::class.java)
            startActivity(intent)
        }


    }


}