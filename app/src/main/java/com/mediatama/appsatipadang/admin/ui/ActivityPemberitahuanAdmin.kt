package com.mediatama.appsatipadang.admin.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.mediatama.appsatipadang.admin.HomeActivity
import com.mediatama.appsatipadang.databinding.ActivityPemberitahuanAdminBinding
import com.mediatama.appsatipadang.supervisor.ActivitySupervisor
import com.mediatama.appsatipadang.teknik.ActivityTeknik
import com.mediatama.appsatipadang.user.MainActivity
import com.mediatama.appsatipadang.user.ui.pemberitahuan.PemberitahuanViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ActivityPemberitahuanAdmin : AppCompatActivity() {
    companion object {
        const val ID_LAPORAN_PEMBERITAHUAN = "idLaporan"
    }

    private lateinit var binding: ActivityPemberitahuanAdminBinding
    var id: Int = 0

    private val viewModel: PemberitahuanViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPemberitahuanAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val id = intent.getIntExtra(ID_LAPORAN_PEMBERITAHUAN, 0)
        val formattedId = String.format("%03d", id)
        val tanggalLaporan = intent.getStringExtra("tanggalLaporan")
        val nomorLaporan = "$tanggalLaporan-$formattedId"
        binding.tvTanggal.text = nomorLaporan

        viewModel.getUser().observe(this@ActivityPemberitahuanAdmin){
            when (it.roles) {
//                "Supervisor" -> {
//                    binding.tvPemberitahuan1.text = "Berhasil Diteruskan ke Teknisi"
//                }
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
            viewModel.getUser().observe(this@ActivityPemberitahuanAdmin){

                when (it.roles) {
                    "Supervisor" -> {
                        val intent = Intent(this@ActivityPemberitahuanAdmin, HomeActivity::class.java)
                        startActivity(intent)
                    }
                    "Teknisi" -> {
                        val intent = Intent(this@ActivityPemberitahuanAdmin, ActivityTeknik::class.java)
                        startActivity(intent)
                    }
                    "Pelapor" -> {
                        val intent = Intent(this@ActivityPemberitahuanAdmin, MainActivity::class.java)
                        startActivity(intent)
                    }
                    else -> {
                        val intent = Intent(this@ActivityPemberitahuanAdmin, ActivitySupervisor::class.java)
                        startActivity(intent)
                    }
                }
            }

        }
    }


}