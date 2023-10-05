package com.aditya.appsatipadang.admin.ui.prasarana_admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aditya.appsatipadang.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PrasaranaActivityAdmin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prasarana_admin)
    }
}