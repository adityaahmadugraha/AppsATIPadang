package com.aditya.appsatipadang.admin.ui.sarana_admin

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.aditya.appsatipadang.BuildConfig
import com.aditya.appsatipadang.admin.HomeActivity
import com.aditya.appsatipadang.data.Resource
import com.aditya.appsatipadang.data.remote.request.KirimTeknisiRequest
import com.aditya.appsatipadang.data.remote.response.TeknisiReponse
import com.aditya.appsatipadang.databinding.ActivitySaranaAdminBinding
import com.aditya.appsatipadang.user.ui.pemberitahuan.ActivityPemberitahuan
import com.aditya.appsatipadang.user.ui.pemberitahuan.ActivityPemberitahuan.Companion.ID_LAPORAN_PEMBERITAHUAN
import com.aditya.appsatipadang.utils.Constant.getToken
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SaranaActivityAdmin : AppCompatActivity() {

    private lateinit var binding: ActivitySaranaAdminBinding
    private val viewModel: SaranaAdminViewModel by viewModels()
    var id = ""

    private var selectedTeknisi: String = ""

    companion object {
        const val TAG_ID_PENGADUAN = "id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySaranaAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        id = intent.getStringExtra(TAG_ID_PENGADUAN).toString()

        binding.apply {
            btnKirim.setOnClickListener {
                inputNamaTeknisi()
            }

            imgBack.setOnClickListener {
                intent = Intent(this@SaranaActivityAdmin, HomeActivity::class.java)
                startActivity(intent)
            }
        }

        getLpaoranData()
        getTeknisiData()
    }

    private fun getLpaoranData() {
        viewModel.getUser().observe(this@SaranaActivityAdmin){
            viewModel.getDataLaporanId(it.getToken,id).observe(this@SaranaActivityAdmin){ item ->
                when(item){
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        val dataItem = item.data.laporan
                        binding.apply {
                            etType.setText(dataItem?.type)
                            etNamePelapor.setText(dataItem?.namaPelapor)
                            etNamePelapor.setText(dataItem?.namaPelapor)
                            etMerk.setText(dataItem?.merk)
                            etLokasi.setText(dataItem?.lokasi)
                            etDeskripiKerusakan.setText(dataItem?.deskripsi)
                            etTanggal.setText(dataItem?.tanggal)
                            etNoPengaduan.setText(dataItem?.idPelapor.toString())

                            Glide.with(this@SaranaActivityAdmin)
                                .load(BuildConfig.IMAGE_URL + dataItem?.foto)
                                .into(imgBuktiSarana)
                        }
                    }
                    is Resource.Error -> {}
                }
            }
        }
    }

    private fun inputNamaTeknisi() {

        if (selectedTeknisi == "0") {
            Toast.makeText(this, "Pilih teknisi terlebih dahulu.", Toast.LENGTH_SHORT).show()
            return
        }

        val dataKirim = KirimTeknisiRequest(selectedTeknisi,id)

        viewModel.getUser().observe(this@SaranaActivityAdmin){
            viewModel.inputLaporanTeknisi(it.getToken, dataKirim).observe(this@SaranaActivityAdmin) { result ->
                when (result) {
                    is Resource.Loading -> {}

                    is Resource.Success -> {
                        val intent = Intent(this@SaranaActivityAdmin, ActivityPemberitahuan::class.java)
                        intent.putExtra(ID_LAPORAN_PEMBERITAHUAN, id.toInt())
                        startActivity(intent)
                    }

                    is Resource.Error -> {
                        val errorMessage = result.error
                        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun getTeknisiData() {
        viewModel.getUser().observe(this@SaranaActivityAdmin) { it ->
            viewModel.getTeknisiList(it.getToken).observe(this@SaranaActivityAdmin) { result ->
                when (result) {
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        val teknisiList = result.data.teknisi.orEmpty().toMutableList()
                        teknisiList.add(0, TeknisiReponse.TeknisiItem("0", "Silahkan Pilih Teknisi"))

                        val adapter = ArrayAdapter(
                            this,
                            android.R.layout.simple_spinner_item,
                            teknisiList.map { it?.name ?: "" })
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        binding.spinerPosisiSaranaAdmin.adapter = adapter

                        binding.spinerPosisiSaranaAdmin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                parent: AdapterView<*>?,
                                view: View?,
                                position: Int,
                                id: Long
                            ) {
                                if (position > 0) {
                                    selectedTeknisi = teknisiList[position]?.id ?: ""
                                } else {
                                    selectedTeknisi = ""
                                }
                            }

                            override fun onNothingSelected(parent: AdapterView<*>?) {
                                selectedTeknisi = ""
                            }
                        }
                    }

                    is Resource.Error -> {}
                }
            }
        }
    }

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
