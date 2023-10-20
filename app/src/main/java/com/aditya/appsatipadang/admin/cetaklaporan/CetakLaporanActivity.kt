package com.aditya.appsatipadang.admin.cetaklaporan

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.print.PrintAttributes
import android.print.PrintJob
import android.print.PrintManager
import android.webkit.WebView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.aditya.appsatipadang.R
import com.aditya.appsatipadang.utils.Constant.STATUS_LAPORAN
import com.aditya.appsatipadang.utils.Constant.TANGGAL_DARI
import com.aditya.appsatipadang.utils.Constant.TANGGAL_SAMPAI
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CetakLaporanActivity : AppCompatActivity() {
    private val viewModel: CetakLpaoranViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cetak_laporan)

        val dari = intent.getStringExtra(TANGGAL_DARI).toString()
        val sampai = intent.getStringExtra(TANGGAL_SAMPAI).toString()
        val status = intent.getStringExtra(STATUS_LAPORAN).toString()

        val webView = findViewById<WebView>(com.aditya.appsatipadang.R.id.webView)

        viewModel.getUser().observe(this@CetakLaporanActivity){
            val idUser = it.id

            val settings = webView.settings
            settings.javaScriptEnabled = true

            webView.setDownloadListener { url, userAgent, contentDisposition, mimetype, contentLength ->
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                } else {
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 123)
                }
            }

            webView.loadUrl("https://atipadang.diatakasir.com/cetaklaporanapk/$status/$dari/$sampai/$idUser")

        }

    }

}