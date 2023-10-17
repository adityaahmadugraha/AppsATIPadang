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

        id = intent.getIntExtra(ID_LAPORAN_PEMBERITAHUAN, 0)

        binding.tvIdLaporan.text = id.toString()

        binding.btnCekLaporan.setOnClickListener {
            viewModel.getUser().observe(this@ActivityPemberitahuan){
                if (it.roles == "Admin"){
                    val intent = Intent(this@ActivityPemberitahuan, HomeActivity::class.java)
                    startActivity(intent)
                }else if (it.roles == "Teknisi"){
                    val intent = Intent(this@ActivityPemberitahuan, ActivityTeknik::class.java)
                    startActivity(intent)
                }else if (it.roles == "Pelapor"){
                    val intent = Intent(this@ActivityPemberitahuan, MainActivity::class.java)
                    startActivity(intent)
                }else{
                    val intent = Intent(this@ActivityPemberitahuan, ActivitySupervisor::class.java)
                    startActivity(intent)
                }
            }

        }
    }


}