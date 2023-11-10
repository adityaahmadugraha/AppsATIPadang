package com.mediatama.appsatipadang.admin.ui.pelaporan

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mediatama.appsatipadang.admin.HomeActivity
import com.mediatama.appsatipadang.databinding.ActivityPelaporanAdminBinding
import com.mediatama.appsatipadang.user.laporan.kamtibmas.ActivityKamtibmas
import com.mediatama.appsatipadang.user.laporan.prasarana.ActivityPrasarana
import com.mediatama.appsatipadang.user.laporan.sarana.SaranaActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ActivityPelaporanAdmin : AppCompatActivity() {

    private lateinit var binding: ActivityPelaporanAdminBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPelaporanAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            cardSarana.setOnClickListener {
                intent = Intent(this@ActivityPelaporanAdmin, SaranaActivity::class.java)
                startActivity(intent)
            }
            cardPrasarana.setOnClickListener {
                intent = Intent(this@ActivityPelaporanAdmin, ActivityPrasarana::class.java)
                startActivity(intent)
            }
            cardKamtibmas.setOnClickListener {
                intent = Intent(this@ActivityPelaporanAdmin, ActivityKamtibmas::class.java)
                startActivity(intent)
            }
            imgBack.setOnClickListener {
                intent = Intent(this@ActivityPelaporanAdmin, HomeActivity::class.java)
                startActivity(intent)
            }

        }
    }
}