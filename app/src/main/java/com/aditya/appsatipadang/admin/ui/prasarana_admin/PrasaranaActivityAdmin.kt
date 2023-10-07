package com.aditya.appsatipadang.admin.ui.prasarana_admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.activity.viewModels
import com.aditya.appsatipadang.admin.HomeActivity
import com.aditya.appsatipadang.data.Resource
import com.aditya.appsatipadang.databinding.ActivityPrasaranaAdminBinding
import com.aditya.appsatipadang.user.utils.Constant.getToken
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PrasaranaActivityAdmin : AppCompatActivity() {

    private lateinit var binding: ActivityPrasaranaAdminBinding
    private val viewModel: PrasaranaViewModel by viewModels()
    var id: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPrasaranaAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.imgBack.setOnClickListener {
            intent = Intent(this@PrasaranaActivityAdmin, HomeActivity::class.java)
            startActivity(intent)
        }

        id = intent.getStringExtra("ID_LAPORAN").toString()


        val bundle = intent.getBundleExtra(TAG_BUNDLE)
        if (bundle != null) {
            binding.apply {
                etMerk.text = Editable.Factory.getInstance().newEditable(bundle.getString(TAG_NAMA))

//                etJenis.text = Editable.Factory.getInstance().newEditable(bundle.getString(TAG_STATUS))

                etJenis.text = Editable.Factory.getInstance().newEditable(bundle.getString(TAG_TIPE))

                etJenisKerusakan.text = Editable.Factory.getInstance().newEditable(bundle.getString(TAG_DESKRIPSI))

                etLokasi.text = Editable.Factory.getInstance().newEditable(bundle.getString(TAG_LOKASI))

                etTanggal.text = Editable.Factory.getInstance().newEditable(bundle.getString(TAG_TANGGAL))

            }
        }

        getDataLaporan()
    }


    private fun getDataLaporan() {
        Log.d("IKO_ID:::::::", id.toString())
        viewModel.getUser().observe(this@PrasaranaActivityAdmin) {
            viewModel.getDataLaporan(it.getToken, id)
                .observe(this@PrasaranaActivityAdmin) { item ->
                    when (item) {
                        is Resource.Loading -> {

                        }

                        is Resource.Success -> {
                            val laporan = item.data.laporan
                            binding.apply {
                                etNamePelapor.setText(laporan!!.merk.toString())
                            }
                        }

                        is Resource.Error -> {

                        }
                    }
                }
        }
    }

    companion object {
        const val TAG_BUNDLE = "kode"
        const val TAG_NAMA = "nama"
        const val TAG_LOKASI = "lokasi"
        const val TAG_DESKRIPSI = "deskripsi"
        const val TAG_TIPE = "tipe"
        const val TAG_TANGGAL = "tanggal"
        const val TAG_JENIS = "jenis"
//        const val TAG_GAMBAR = "gambar"

    }
}