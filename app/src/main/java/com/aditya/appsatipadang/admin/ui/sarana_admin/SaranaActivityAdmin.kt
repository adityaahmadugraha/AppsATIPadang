package com.aditya.appsatipadang.admin.ui.sarana_admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aditya.appsatipadang.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SaranaActivityAdmin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sarana_admin)

        supportActionBar?.hide()
    }
}