package com.aditya.appsatipadang.admin.ui.kamtibmas_admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aditya.appsatipadang.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KamtibmasActivityAdmin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kamtibmas_admin)
    }
}