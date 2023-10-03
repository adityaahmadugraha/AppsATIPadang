package com.aditya.appsatipadang.ui.detailstatuslaporan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import com.aditya.appsatipadang.databinding.ActivityDetailStatusLaporanBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailStatusLaporanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailStatusLaporanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailStatusLaporanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()


        val bundle = intent.getBundleExtra(TAG_BUNDLE)
        if (bundle != null) {
            binding.apply {
                etNameStatus.text =
                    Editable.Factory.getInstance().newEditable(bundle.getString(TAG_NAMA))
                etJenisStatus.text =
                    Editable.Factory.getInstance().newEditable(bundle.getString(TAG_STATUS))
                etTipe.text = Editable.Factory.getInstance().newEditable(bundle.getString(TAG_TIPE))

                btnBack.setOnClickListener {
                    finish()
                }
            }
        }
    }

    companion object {
        const val TAG_BUNDLE = "kode"
        const val TAG_NAMA = "nama"
        const val TAG_STATUS = "status"
        const val TAG_TIPE = "tipe"
    }
}