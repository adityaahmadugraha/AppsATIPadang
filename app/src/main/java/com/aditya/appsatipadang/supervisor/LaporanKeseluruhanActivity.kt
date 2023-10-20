package com.aditya.appsatipadang.supervisor

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.aditya.appsatipadang.R
import com.aditya.appsatipadang.databinding.ActivityLaporanKeseluruhanBinding
import java.io.ByteArrayOutputStream


class LaporanKeseluruhanActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    private lateinit var binding: ActivityLaporanKeseluruhanBinding
    private lateinit var context: Context


    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLaporanKeseluruhanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        context = this

        webView = findViewById(R.id.webView)

        webView.settings.javaScriptEnabled = true

        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {

            }
        }

        webView.loadUrl("https://atipadang.diatakasir.com/cetaklaporan")
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)

    private fun viewToBitmap(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    private fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }
}


