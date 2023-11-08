package com.aditya.appsatipadang.user.ui.pemberitahuan

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.aditya.appsatipadang.admin.HomeActivity
import com.aditya.appsatipadang.databinding.ActivityPemberitahuanBinding
import com.aditya.appsatipadang.supervisor.ActivitySupervisor
import com.aditya.appsatipadang.teknik.ActivityTeknik
import com.aditya.appsatipadang.user.MainActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ActivityPemberitahuan : AppCompatActivity() {

    companion object {
        const val ID_LAPORAN_PEMBERITAHUAN = "idLaporan"
    }

    private lateinit var binding: ActivityPemberitahuanBinding
    var id: Int = 0

    private val viewModel: PemberitahuanViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPemberitahuanBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val id = intent.getIntExtra(ID_LAPORAN_PEMBERITAHUAN, 0)
        val formattedId = String.format("%03d", id)
        val tanggalLaporan = intent.getStringExtra("tanggalLaporan")
        val nomorLaporan = "$tanggalLaporan-$formattedId"
        binding.tvTanggal.text = nomorLaporan

        viewModel.getUser().observe(this@ActivityPemberitahuan){
            when (it.roles) {
                "Supervisor" -> {
                    binding.tvPemberitahuan.text = "Berhasil Diterima Supervisor"
                }
                "Teknisi" -> {
                    binding.tvPemberitahuan.text = "Berhasil Diterima Supervisor"
                }
                "Pelapor" -> {
                    binding.tvPemberitahuan.text = "Berhasil Diterima Supervisor"
                }
                else -> {

                }
            }


        }

        binding.btnCekLaporan.setOnClickListener {
            viewModel.getUser().observe(this@ActivityPemberitahuan){

                when (it.roles) {
                    "Supervisor" -> {
                        val intent = Intent(this@ActivityPemberitahuan, HomeActivity::class.java)
                        startActivity(intent)
                    }
                    "Teknisi" -> {
                        val intent = Intent(this@ActivityPemberitahuan, ActivityTeknik::class.java)
                        startActivity(intent)
                    }
                    "Pelapor" -> {
                        val intent = Intent(this@ActivityPemberitahuan, MainActivity::class.java)
                        startActivity(intent)
                    }
                    else -> {
                        val intent = Intent(this@ActivityPemberitahuan, ActivitySupervisor::class.java)
                        startActivity(intent)
                    }
                }
            }

        }
    }


}