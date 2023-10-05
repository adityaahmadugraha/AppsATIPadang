package com.aditya.appsatipadang.admin.ui.status_admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aditya.appsatipadang.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class StatusActivityAdmin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_status_admin)
    }
}