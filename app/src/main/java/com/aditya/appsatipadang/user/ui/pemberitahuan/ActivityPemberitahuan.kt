package com.aditya.appsatipadang.user.ui.pemberitahuan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.aditya.appsatipadang.user.MainActivity
import com.aditya.appsatipadang.databinding.ActivityPemberitahuanBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ActivityPemberitahuan : AppCompatActivity() {

    private lateinit var binding: ActivityPemberitahuanBinding
    var id: String = ""

    private val viewModel: PemberitahuanViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPemberitahuanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        id = intent.getIntExtra(TAG_ID_LAPORAN, 0).toString()
        Log.d("IKO_ID_PEMBERITAHUAN:::::", id)

        binding.tvIdLaporan.text = id

        binding.btnCekLaporan.setOnClickListener {
            intent = Intent(this@ActivityPemberitahuan, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    companion object {
        const val TAG_ID_LAPORAN = "idLaporan"
    }
}