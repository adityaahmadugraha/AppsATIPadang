package com.aditya.appsatipadang.ui.pemberitahuan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.aditya.appsatipadang.databinding.ActivityPemberitahuanBinding
import com.aditya.appsatipadang.ui.detailstatuslaporan.DetailStatusLaporanActivity

class ActivityPemberitahuan : AppCompatActivity() {

    private lateinit var binding: ActivityPemberitahuanBinding
    var id : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        id = intent.getIntExtra(TAG_ID_LAPORAN, 0).toString()
//        id = intent.getStringExtra(TAG_ID_LAPORAN) ?: ""


        binding = ActivityPemberitahuanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        Log.d("IKO_ID_PEMBERITAHUAN:::::", id)

        binding.tvIdLaporan.text = id

        binding.btnCekLaporan.setOnClickListener {
            intent = Intent(this@ActivityPemberitahuan, DetailStatusLaporanActivity::class.java)
            intent.putExtra("ID_LAPORAN",id)
            startActivity(intent)
        }
    }

    companion object {
        const val TAG_ID_LAPORAN = "idLaporan"
    }
}