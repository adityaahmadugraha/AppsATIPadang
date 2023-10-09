package com.aditya.appsatipadang.supervisor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.aditya.appsatipadang.R
import com.aditya.appsatipadang.databinding.ActivitySupervisorBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivitySupervisor : AppCompatActivity() {

    private lateinit var binding: ActivitySupervisorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySupervisorBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navView: BottomNavigationView = binding.navViewSpp

        val navController = findNavController(R.id.nav_host_fragment_activity_spp)

        AppBarConfiguration(
            setOf(
                R.id.navigation_home_spp,
                R.id.navigation_history_spp,
                R.id.navigation_profile
            )
        )

        navView.setupWithNavController(navController)
    }
}