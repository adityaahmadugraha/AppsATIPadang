package com.aditya.appsatipadang.admin.ui.sarana_admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.activity.viewModels
import com.aditya.appsatipadang.admin.HomeActivity
import com.aditya.appsatipadang.data.Resource
import com.aditya.appsatipadang.databinding.ActivitySaranaAdminBinding
import com.aditya.appsatipadang.utils.Constant.getToken
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SaranaActivityAdmin : AppCompatActivity() {


    private lateinit var binding: ActivitySaranaAdminBinding
    private val viewModel: SaranaAdminViewModel by viewModels()
    var id: String = ""

    companion object {
        const val TAG_BUNDLE = "kode" //kode
        const val TAG_NAMA = "nama" //nama pelapor
        const val TAG_LOKASI = "lokasi" // lokasi
        const val TAG_DESKRIPSI = "deskripsi" //deskripsi
        const val TAG_TIPE = "tipe" // sarana,prasarana,kamtibmas
        const val TAG_TANGGAL = "tanggal" //tanggal
        const val TAG_JENIS = "jenis" //chip
        const val TAG_FOTO = "foto" //foto
        const val TAG_ID_LAPORAN = "ID_LAPORAN" //id
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySaranaAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.imgBack.setOnClickListener {
            intent = Intent(this@SaranaActivityAdmin, HomeActivity::class.java)
            startActivity(intent)
        }

        id = intent.getStringExtra(TAG_ID_LAPORAN).toString()


        val bundle = intent.getBundleExtra(TAG_BUNDLE)
        if (bundle != null) {
            binding.apply {
                etMerk.text = Editable.Factory.getInstance().newEditable(bundle.getString(TAG_NAMA))

//                etType.text = Editable.Factory.getInstance().newEditable(bundle.getString(TAG_JENIS))

                etJenis.text =
                    Editable.Factory.getInstance().newEditable(bundle.getString(TAG_TIPE))

                etJenisKerusakan.text =
                    Editable.Factory.getInstance().newEditable(bundle.getString(TAG_DESKRIPSI))

                etLokasi.text =
                    Editable.Factory.getInstance().newEditable(bundle.getString(TAG_LOKASI))

                etTanggal.text =
                    Editable.Factory.getInstance().newEditable(bundle.getString(TAG_TANGGAL))


//                val imageUrl = bundle.getString(TAG_FOTO)
//                Picasso.get().load(imageUrl).into(imgBuktiSarana)
//                val drawableResourceId = resources.getIdentifier(bundle.getString(TAG_FOTO), "drawable", packageName)
//                imgBuktiSarana.setImageResource(drawableResourceId)

            }
        }

        getDataLaporan()
    }


    private fun getDataLaporan() {
        Log.d("IKO_ID:::::::", id.toString())
        viewModel.getUser().observe(this@SaranaActivityAdmin) {
            viewModel.getDataLaporan(it.getToken, id)
                .observe(this@SaranaActivityAdmin) { item ->
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


}