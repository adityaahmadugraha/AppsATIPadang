package com.aditya.appsatipadang.supervisor

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import com.aditya.appsatipadang.R
import com.aditya.appsatipadang.admin.cetaklaporan.CetakLaporanActivity
import com.aditya.appsatipadang.databinding.ActivityLaporanKeseluruhanBinding
import com.aditya.appsatipadang.utils.Constant.STATUS_LAPORAN
import com.aditya.appsatipadang.utils.Constant.TANGGAL_DARI
import com.aditya.appsatipadang.utils.Constant.TANGGAL_SAMPAI
import java.io.ByteArrayOutputStream
import java.util.Calendar


class LaporanKeseluruhanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLaporanKeseluruhanBinding

    var tanggalDari = ""
    var tanggalSamapi = ""
    var statusLaporan = ""
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLaporanKeseluruhanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

            etDari.setOnClickListener {
                val calendar = Calendar.getInstance()
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DAY_OF_MONTH)

                val datePickerDialog = DatePickerDialog(this@LaporanKeseluruhanActivity, { _, selectedYear, selectedMonth, selectedDay ->
                    tanggalDari = "$selectedYear-${selectedMonth + 1}-$selectedDay" // Adjust month index to match human-readable format
                    etDari.setText(tanggalDari)
                }, year, month, day)

                datePickerDialog.show()
            }

            etSampai.setOnClickListener {
                val calendar = Calendar.getInstance()
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DAY_OF_MONTH)

                val datePickerDialog = DatePickerDialog(this@LaporanKeseluruhanActivity, { _, selectedYear, selectedMonth, selectedDay ->
                    tanggalSamapi = "$selectedYear-${selectedMonth + 1}-$selectedDay" // Adjust month index to match human-readable format
                    etSampai.setText(tanggalSamapi)
                }, year, month, day)

                datePickerDialog.show()
            }

            btnCetak.setOnClickListener {
                val intent = Intent(this@LaporanKeseluruhanActivity, CetakLaporanActivity::class.java)
                intent.putExtra(TANGGAL_DARI, tanggalDari)
                intent.putExtra(TANGGAL_SAMPAI, tanggalSamapi)
                intent.putExtra(STATUS_LAPORAN, statusLaporan)
                startActivity(intent)
            }

        }

        val spinner = findViewById<Spinner>(R.id.spinner)
        val items = resources.getStringArray(R.array.spinner_items)

        val adapter = ArrayAdapter(this@LaporanKeseluruhanActivity, android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                statusLaporan = items[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do something when nothing is selected
            }
        }
    }

}


