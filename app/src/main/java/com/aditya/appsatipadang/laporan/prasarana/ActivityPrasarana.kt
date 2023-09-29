package com.aditya.appsatipadang.laporan.prasarana

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aditya.appsatipadang.MainActivity
import android.app.DatePickerDialog
import android.view.View
import android.widget.EditText
import java.text.SimpleDateFormat
import java.util.Calendar

import com.aditya.appsatipadang.databinding.ActivityPrasaranaBinding

class ActivityPrasarana : AppCompatActivity() {

    private lateinit var binding: ActivityPrasaranaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPrasaranaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.etTanggal.setOnClickListener {
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

                    binding.etTanggal.setText(formattedDate)
                },
                year,
                month,
                dayOfMonth
            )

            datePickerDialog.show()
        }



        supportActionBar?.hide()

        binding.imgBackLaporanPrasarana.setOnClickListener() {
            val intent = Intent(this@ActivityPrasarana, MainActivity::class.java)
            startActivity(intent)
        }
    }
}