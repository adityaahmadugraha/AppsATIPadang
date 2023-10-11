package com.aditya.appsatipadang.teknik.ui_teknisi.nontifikasi_laporan

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.aditya.appsatipadang.R
import com.aditya.appsatipadang.databinding.ActivityNontofikasiLaporanTeknisiBinding
import com.aditya.appsatipadang.teknik.ui_teknisi.laporan.LaporanTeknisiActivity
import com.aditya.appsatipadang.utils.Constant
import com.aditya.appsatipadang.utils.Constant.IDLAPORAN
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ActivityNontofikasiLaporanTeknisi : AppCompatActivity() {

    private lateinit var binding: ActivityNontofikasiLaporanTeknisiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNontofikasiLaporanTeknisiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val idLaporan = intent.getStringExtra(Constant.IDLAPORAN)
        Log.i("IDLAPORAN", "onCreate: $idLaporan")
        binding.btnCekDetailLaporan.setOnClickListener {
            intent =
                Intent(this@ActivityNontofikasiLaporanTeknisi, LaporanTeknisiActivity::class.java)
                intent.putExtra(IDLAPORAN, idLaporan)
            startActivity(intent)
        }
    }



}