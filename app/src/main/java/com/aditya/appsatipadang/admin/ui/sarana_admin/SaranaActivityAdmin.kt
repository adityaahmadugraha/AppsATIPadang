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
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.aditya.appsatipadang.BuildConfig
import com.aditya.appsatipadang.admin.HomeActivity
import com.aditya.appsatipadang.data.Resource
import com.aditya.appsatipadang.data.local.UserLocal
import com.aditya.appsatipadang.data.remote.response.TeknisiReponse
import com.aditya.appsatipadang.databinding.ActivitySaranaAdminBinding
import com.aditya.appsatipadang.user.ui.pemberitahuan.ActivityPemberitahuan
import com.aditya.appsatipadang.user.ui.pemberitahuan.ActivityPemberitahuan.Companion.TAG_ID_LAPORAN
import com.aditya.appsatipadang.utils.Constant.getToken
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SaranaActivityAdmin : AppCompatActivity() {


    private lateinit var binding: ActivitySaranaAdminBinding
    private val viewModel: SaranaAdminViewModel by viewModels()
    var id: String = ""

    private var selectedTeknisi: String = ""

    private var user: UserLocal? = null


    companion object {
        const val TAG_BUNDLE = "kode"
        //        const val TAG_NAMA = "nama"
        const val TAG_TIPE = "tipe"
        const val TAG_TANGGAL = "tanggal"
        const val TAG_LOKASI = "lokasi"
        const val TAG_FOTO = "foto"
        const val TAG_MERK = "merk"
        const val TAG_DESKRIPSI = "deskripsi"
        const val TAG_ID_PENGADUAN = "id"
        const val TAG_JENIS = "jenis"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySaranaAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnKirim.setOnClickListener {
                inputNamaTeknisi(it)
            }
        }

        getUserData()
    }

        private fun inputNamaTeknisi(
            requestBody: View
    ) {

        val selectedTeknisi = binding.spinerPosisiSaranaAdmin.selectedItem.toString()
        if (selectedTeknisi == "Silahkan Pilih Teknisi") {
            Toast.makeText(this, "Pilih teknisi terlebih dahulu.", Toast.LENGTH_SHORT).show()
            return
        }

            Log.d("KirimLaporan:::::::", "Mengirim laporan ke teknisi: $selectedTeknisi")

        viewModel.inputLaporanTeknisi(
            user?.getToken.toString(),
           selectedTeknisi, requestBody
        )
            .observe(this@SaranaActivityAdmin) { result ->
                when (result) {
                    is Resource.Loading -> {
//                        showLoadingInput(true)
                    }

                    is Resource.Success -> {
                        val laporanId = result.data.id
                        Log.d("DataTerkirim::::::", "Laporan berhasil dikirim. ID Laporan: $laporanId")
                        val intent = Intent(
                            this@SaranaActivityAdmin,
                            ActivityPemberitahuan::class.java
                        ).apply {
                            putExtra(TAG_ID_LAPORAN, laporanId)
                        }
                        startActivity(intent)
                    }

                    is Resource.Error -> {
                        val errorMessage = result.error
                        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    private fun getUserData() {
        viewModel.getUser().observe(this) {
            user = it
        }


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

                etNoPengaduan.text =
                    Editable.Factory.getInstance().newEditable(bundle.getString(TAG_ID_PENGADUAN))

                etType.text =
                    Editable.Factory.getInstance().newEditable(bundle.getString(TAG_JENIS))

                etTanggal.text =
                    Editable.Factory.getInstance().newEditable(bundle.getString(TAG_TANGGAL))

                etLokasi.text =
                    Editable.Factory.getInstance().newEditable(bundle.getString(TAG_LOKASI))

                etMerk.text =
                    Editable.Factory.getInstance().newEditable(bundle.getString(TAG_MERK))

                val data = intent.getStringExtra(TAG_FOTO)
                Log.d("fotoSaranaAdmin", "Data gambar: $data")
                Glide.with(this@SaranaActivityAdmin)
                    .load(BuildConfig.IMAGE_URL+data)
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

                        val adapter = ArrayAdapter(
                            this,
                            android.R.layout.simple_spinner_item,
                            teknisiList.map { it?.name ?: "" })
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
                                    if (position > 0) {
                                        selectedTeknisi = teknisiList[position]?.name ?: ""
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
