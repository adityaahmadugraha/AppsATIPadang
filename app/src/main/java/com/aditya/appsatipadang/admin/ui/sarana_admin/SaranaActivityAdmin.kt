package com.aditya.appsatipadang.admin.ui.sarana_admin

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.aditya.appsatipadang.R
import com.aditya.appsatipadang.admin.HomeActivity
import com.aditya.appsatipadang.data.Resource
import com.aditya.appsatipadang.data.remote.response.TeknisiReponse
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
//    var teknisi: String = ""

    companion object {
        const val TAG_BUNDLE = "kode" //kode
//        const val TAG_NAMA = "nama" //nama pelapor
        const val TAG_TIPE = "tipe"
        const val TAG_TANGGAL = "tanggal"
        const val TAG_LOKASI = "lokasi"
        const val TAG_FOTO = "foto"
        const val TAG_MERK = "merk"
        const val TAG_DESKRIPSI = "deskripsi"


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySaranaAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)


        getTeknisiData()
        id = intent.getStringExtra("ID_LAPORAN").toString()

        binding.imgBack.setOnClickListener {
            intent = Intent(this@SaranaActivityAdmin, HomeActivity::class.java)
            startActivity(intent)
        }

        val bundle = intent.getBundleExtra(TAG_BUNDLE)
        if (bundle != null) {
            binding.apply {

                etJenis.text =
                    Editable.Factory.getInstance().newEditable(bundle.getString(TAG_TIPE))

                etDeskripiKerusakan.text =
                    Editable.Factory.getInstance().newEditable(bundle.getString(TAG_DESKRIPSI))

                etTanggal.text =
                    Editable.Factory.getInstance().newEditable(bundle.getString(TAG_TANGGAL))

                etLokasi.text =
                    Editable.Factory.getInstance().newEditable(bundle.getString(TAG_LOKASI))

                etMerk.text =
                    Editable.Factory.getInstance().newEditable(bundle.getString(TAG_MERK))

//
//                val imageUrl = bundle.getString(TAG_FOTO)
//                    Glide.with(this@SaranaActivityAdmin)
//                        .load(imageUrl)
//                        .placeholder(R.drawable.no_image)
//                        .into(imgBuktiSarana)

                val data = intent.getStringExtra(TAG_FOTO)
                Log.d("SaranaActivityAdmin", "Data gambar: $data")
                Glide.with(this@SaranaActivityAdmin)
                    .load(data)
                    .into(binding.imgBuktiSarana)

                }


            }
        }

    fun getTeknisiData() {
        viewModel.getUser().observe(this@SaranaActivityAdmin) { it ->
            viewModel.getTeknisiList(it.getToken).observe(this@SaranaActivityAdmin) { result ->
                when (result) {
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        val teknisiList = result.data.teknisi.orEmpty().toMutableList()
                        teknisiList.add(0, TeknisiReponse.TeknisiItem("Silahkan Pilih Teknisi"))

                        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, teknisiList.map { it?.name ?: "" })
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        binding.spinerPosisiSaranaAdmin.adapter = adapter


                        binding.spinerPosisiSaranaAdmin.onItemSelectedListener =
                            object : AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(
                                    parent: AdapterView<*>?,
                                    view: View?,
                                    position: Int,
                                    id: Long
                                ) {
                                    if (position == 0) {
//                                        warna background title spiner
//                                        (view)?.setBackgroundColor(resources.getColor(R.color.system_accent1_200))
                                    } else {

                                        (view)?.setBackgroundColor(0)
                                        val selectedTeknisi = teknisiList[position]
                                        selectedTeknisi?.name
                                    }
                                }

                                override fun onNothingSelected(parent: AdapterView<*>?) {

                                }
                            }

                    }
                    is Resource.Error -> {}
                }
            }
        }
    }


//    private fun getDataLaporan() {
//        Log.d("IKO_ID:::::::", id.toString())
//        viewModel.getUser().observe(this@SaranaActivityAdmin) {
//            viewModel.getDataLaporan(it.getToken, id)
//                .observe(this@SaranaActivityAdmin) { item ->
//                    when (item) {
//                        is Resource.Loading -> {}
//                        is Resource.Success -> {
//                            val laporan = item.data.laporan
//                            binding.apply {
//                                etNamePelapor.setText(laporan!!.merk.toString())
//                            }
//                        }
//
//                        is Resource.Error -> {}
//                    }
//                }
//        }
//    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is AutoCompleteTextView) {
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
