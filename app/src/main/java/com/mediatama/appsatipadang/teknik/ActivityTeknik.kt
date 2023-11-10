package com.mediatama.appsatipadang.teknik

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.mediatama.appsatipadang.R
import com.mediatama.appsatipadang.databinding.ActivityTeknikBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityTeknik : AppCompatActivity() {

    private lateinit var binding: ActivityTeknikBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTeknikBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        supportActionBar?.hide()

        val navView: BottomNavigationView = binding.navViewTeknik

        val navController = findNavController(R.id.nav_host_fragment_activity_teknik)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home_teknik,
                R.id.navigation_history_teknik,
                R.id.navigation_profile
            )
        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}