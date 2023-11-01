package com.aditya.appsatipadang.admin.ui.pengaduan_admin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aditya.appsatipadang.databinding.ActivityPengaduanBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PengaduanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPengaduanBinding

    private val menuArray = arrayOf(
        "Diterima",
        "Dikerjakan",
        "Selesai",
        "Dihapus"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPengaduanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnBack.setOnClickListener {
                super.onBackPressed()
            }
        }

        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout

        val adapter = MenuAdapter(supportFragmentManager, lifecycle)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = menuArray[position]
        }.attach()
    }
}