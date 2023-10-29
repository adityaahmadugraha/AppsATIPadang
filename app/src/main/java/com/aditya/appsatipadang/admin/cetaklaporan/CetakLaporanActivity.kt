package com.aditya.appsatipadang.admin.cetaklaporan

import android.content.Context
import android.os.Bundle
import android.print.PrintAttributes
import android.print.PrintJob
import android.print.PrintManager
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.aditya.appsatipadang.R
import com.aditya.appsatipadang.utils.Constant.STATUS_LAPORAN
import com.aditya.appsatipadang.utils.Constant.TANGGAL_DARI
import com.aditya.appsatipadang.utils.Constant.TANGGAL_SAMPAI
import com.google.android.material.floatingactionbutton.FloatingActionButton
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

        val webView = findViewById<WebView>(R.id.webView)
        val buttonCetak = findViewById<ImageView>(R.id.btn_pdf)

        viewModel.getUser().observe(this@CetakLaporanActivity) {
            val idUser = it.id

            val settings = webView.settings
            settings.javaScriptEnabled = true

            webView.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    // Aktifkan tombol cetak setelah halaman selesai dimuat
                    buttonCetak.isEnabled = true
                }
            }

            buttonCetak.setOnClickListener {
                // Cetak laporan ketika tombol cetak ditekan
                cetakLaporan(webView)
            }

            webView.loadUrl("https://atipadang.diatakasir.com/cetaklaporanapk/$status/$dari/$sampai/$idUser")
        }
    }

    private fun cetakLaporan(webView: WebView) {
        val printManager = getSystemService(Context.PRINT_SERVICE) as PrintManager

        val printAdapter = webView.createPrintDocumentAdapter("Laporan")

        val printJob: PrintJob =
            printManager.print("Laporan", printAdapter, PrintAttributes.Builder().build())


    }
}