package com.aditya.appsatipadang.admin.ui.sarana_admin

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.AutoCompleteTextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.aditya.appsatipadang.R
import com.aditya.appsatipadang.admin.HomeActivity
import com.aditya.appsatipadang.data.Resource
import com.aditya.appsatipadang.databinding.ActivitySaranaAdminBinding
import com.aditya.appsatipadang.utils.Constant.getToken
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SaranaActivityAdmin : AppCompatActivity() {


    private lateinit var binding: ActivitySaranaAdminBinding
    private val viewModel: SaranaAdminViewModel by viewModels()
    var id: String = ""
    var teknisi: String = ""

    companion object {
        const val TAG_BUNDLE = "kode" //kode
        const val TAG_NAMA = "nama" //nama pelapor
        const val TAG_TIPE = "tipe"
        const val TAG_TANGGAL = "tanggal"
        const val TAG_LOKASI = "lokasi"
        const val TAG_FOTO = "foto"
        const val TAG_MERK = "merk"
        const val TAG_DESKRIPSI = "deskripsi"

//        const val TAG_LOKASI = "lokasi" // lokasi
//        const val TAG_DESKRIPSI = "deskripsi" //deskripsi
//        const val TAG_TIPE = "tipe" // sarana,prasarana,kamtibmas
//        const val TAG_TANGGAL = "tanggal" //tanggal
//        const val TAG_JENIS = "jenis" //chip
//        const val TAG_FOTO = "foto" //foto
//        const val TAG_ID_LAPORAN = "ID_LAPORAN"
//        const val TAG_MERK = "merk" //merk
//        const val TAG_NAMA_PELAPOR = "nama_pelapor" //nama_pelapor
//        const val TAG_NO_PENGADUAN = "no_pengaduan"  //no_pengaduan
//        const val TAG_SPINER = "spiner"

        //id
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySaranaAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        id = intent.getStringExtra("ID_LAPORAN").toString()

        binding.imgBack.setOnClickListener {
            intent = Intent(this@SaranaActivityAdmin, HomeActivity::class.java)
            startActivity(intent)
        }

        val bundle = intent.getBundleExtra(TAG_BUNDLE)
        if (bundle != null) {
            binding.apply {




                etJenis.text = Editable.Factory.getInstance().newEditable(bundle.getString(TAG_TIPE))

//                etNamePelapor.text =
//                    Editable.Factory.getInstance().newEditable(bundle.getString(TAG_NAMA_PELAPOR))
//
                etDeskripiKerusakan.text =
                    Editable.Factory.getInstance().newEditable(bundle.getString(TAG_DESKRIPSI))
//
                etTanggal.text =
                    Editable.Factory.getInstance().newEditable(bundle.getString(TAG_TANGGAL))




//
                etLokasi.text =
                    Editable.Factory.getInstance().newEditable(bundle.getString(TAG_LOKASI))
//
                etMerk.text =
                    Editable.Factory.getInstance().newEditable(bundle.getString(TAG_MERK))
//
//                etDeskripiKerusakan.text =
//                    Editable.Factory.getInstance().newEditable(bundle.getString(TAG_DESKRIPSI))

//                spinerPosisiSaranaAdmin.textAlignment =
//                    Spinner(bundle.getString(TAG_SPINER))
//
//
//                val imageUrl = bundle.getString(TAG_FOTO)
//                    Glide.with(this@SaranaActivityAdmin)
//                        .load(imageUrl)
//                        .placeholder(R.drawable.no_image)
//                        .into(imgBuktiSarana)

//                }


            }
        }

        getDataLaporan()
//        getTeknisiList()

//        val adapterPemilihanTeknisi = ArrayAdapter.createFromResource(
//            this,, android.R.layout.simple_spinner_item
//        )
//        adapterPemilihanTeknisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        binding.spinerPosisiSaranaAdmin.adapter = adapterPemilihanTeknisi

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

//    private fun getTeknisiList() {
//
//        viewModel.getUser().observe(this@SaranaActivityAdmin) {
//
//            viewModel.getTeknisiList(it.getToken, teknisi)
//                .observe(this@SaranaActivityAdmin) { item ->
//                    when (item) {
//                        is Resource.Loading -> {
//
//                        }
//
//                        is Resource.Success -> {
//                            val laporan = item.data.laporan
//                            binding.apply {
//                                spinerPosisiSaranaAdmin.dropDownWidth
//                            }
//                        }
//
//                        is Resource.Error -> {
//
//                        }
//                    }
//                }
//        }
//    }


    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is TextInputEditText || v is AutoCompleteTextView) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(ev.rawX.toInt(), ev.rawY.toInt())) {
                    v.clearFocus()
                    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.windowToken, 0)
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }


}