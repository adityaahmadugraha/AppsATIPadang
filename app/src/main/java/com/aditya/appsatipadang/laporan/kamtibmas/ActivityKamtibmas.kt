package com.aditya.appsatipadang.laporan.kamtibmas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aditya.appsatipadang.MainActivity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import java.text.SimpleDateFormat
import java.util.Calendar

import com.aditya.appsatipadang.databinding.ActivityKamtibmasBinding
import com.aditya.appsatipadang.ui.pemberitahuan.ActivityPemberitahuan

class ActivityKamtibmas : AppCompatActivity() {
    private lateinit var binding: ActivityKamtibmasBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityKamtibmasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.etTanggalKamtibmas.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(selectedYear, selectedMonth, selectedDayOfMonth)

                    val dateFormat = SimpleDateFormat("dd-MM-yyyy")
                    val formattedDate = dateFormat.format(selectedDate.time)

                    binding.etTanggalKamtibmas.setText(formattedDate)
                },
                year,
                month,
                dayOfMonth
            )

            datePickerDialog.show()
        }

        binding.etWaktuKamtibmas.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(
                this,
                { _, selectedHour, selectedMinute ->
                    val formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute)

                    binding.etWaktuKamtibmas.setText(formattedTime)
                },
                hour,
                minute,
                true
            )

            timePickerDialog.show()
        }

        supportActionBar?.hide()

        binding.imgBackKamtibmas.setOnClickListener() {
            val intent = Intent(this@ActivityKamtibmas, MainActivity::class.java)
            startActivity(intent)
        }
        binding.btnKirimKamtibmas.setOnClickListener {
            intent = Intent(this@ActivityKamtibmas, ActivityPemberitahuan::class.java)
            startActivity(intent)
        }
    }
}