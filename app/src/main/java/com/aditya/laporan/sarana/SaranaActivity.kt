package com.aditya.laporan.sarana

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aditya.appsatipadang.R
import com.aditya.appsatipadang.databinding.ActivitySaranaBinding

class SaranaActivity : AppCompatActivity() {

    private lateinit var bindding: ActivitySaranaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindding = ActivitySaranaBinding.inflate(layoutInflater)
        setContentView(bindding.root)

        supportActionBar?.hide()
    }
}