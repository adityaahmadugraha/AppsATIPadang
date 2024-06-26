package com.aditya.appsatipadang.admin.ui.sarana_admin

import android.content.Intent
import android.graphics.Rect
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.aditya.appsatipadang.BuildConfig
import com.aditya.appsatipadang.R
import com.aditya.appsatipadang.admin.HomeActivity
import com.aditya.appsatipadang.admin.ui.ActivityPemberitahuanAdmin
import com.aditya.appsatipadang.data.Resource
import com.aditya.appsatipadang.data.remote.request.KirimTeknisiRequest
import com.aditya.appsatipadang.data.remote.response.TeknisiReponse
import com.aditya.appsatipadang.databinding.ActivitySaranaAdminBinding
import com.aditya.appsatipadang.user.ui.pemberitahuan.ActivityPemberitahuan
import com.aditya.appsatipadang.user.ui.pemberitahuan.ActivityPemberitahuan.Companion.ID_LAPORAN_PEMBERITAHUAN
import com.aditya.appsatipadang.user.ui.profile.CustomTypefaceSpan
import com.aditya.appsatipadang.utils.Constant.getToken
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody


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

            imgDelete.setOnClickListener {


                val customView = LayoutInflater.from(this@SaranaActivityAdmin)
                    .inflate(R.layout.custom_delate, null)

                val dialog = AlertDialog.Builder(this@SaranaActivityAdmin)
                    .setView(customView)
                    .create()

                val yesString = getString(R.string.yes)
                val noString = getString(R.string.batal)

                val yesSpannable = SpannableString(yesString)
                val noSpannable = SpannableString(noString)

                val typeface =
                    ResourcesCompat.getFont(this@SaranaActivityAdmin, R.font.poppinssembiold)

                typeface?.let {
                    yesSpannable.setSpan(
                        StyleSpan(Typeface.BOLD),
                        0,
                        yesSpannable.length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    yesSpannable.setSpan(
                        CustomTypefaceSpan(typeface.toString()),
                        0,
                        yesSpannable.length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )

                    noSpannable.setSpan(
                        StyleSpan(Typeface.BOLD),
                        0,
                        noSpannable.length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    noSpannable.setSpan(
                        CustomTypefaceSpan(typeface.toString()),
                        0,
                        noSpannable.length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }

                dialog.setButton(AlertDialog.BUTTON_POSITIVE, yesSpannable) { _, _ ->

                    val customDilog = DialogActivity(this@SaranaActivityAdmin)
                    customDilog.show()
                    customDilog.setOnDismissListener {
                        val alasan = customDilog.getResult()
                        hapusLaporan(alasan.toString())
                    }
                }
                dialog.setButton(AlertDialog.BUTTON_NEGATIVE, noSpannable) { _, _ -> }

                dialog.window?.setBackgroundDrawableResource(R.drawable.rounded_dialog_bg)
                dialog.window?.decorView?.setPadding(24, 0, 24, 0)

                dialog.show()

            }

        }

        getLpaoranData()
        getTeknisiData()
    }

    private fun hapusLaporan(alasan: String) {
        val requestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("alasan", alasan).build()
        viewModel.getUser().observe(this) {
            viewModel.deleteLaporan(it.getToken, id, requestBody).observe(this) { items ->
                when (items) {
                    is Resource.Loading -> {
                        Log.d("DeleteLaporan", "Sedang menghapus laporan...")
                    }

                    is Resource.Success -> {
                        Toast.makeText(this, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                        finish()

                    }

                    is Resource.Error -> {
                        Toast.makeText(this, "Gagal menghapus laporan", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun getLpaoranData() {
        viewModel.getUser().observe(this@SaranaActivityAdmin) {
            viewModel.getDataLaporanId(it.getToken, id).observe(this@SaranaActivityAdmin) { item ->
                when (item) {
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        val dataItem = item.data.laporan
                        binding.apply {
                            etType.setText(dataItem?.jenis)
                            etJenis.setText(dataItem?.type)

                            etNamePelapor.setText(dataItem?.namaPelapor)
                            etMerk.setText(dataItem?.merk)
                            etLokasi.setText(dataItem?.lokasi)
                            etDeskripiKerusakan.setText(dataItem?.deskripsi)
                            etTanggal.setText(dataItem?.tanggal)
                            etWaktu.setText(dataItem?.waktu)
                            val idWithLeadingZeros = String.format("%03d", dataItem?.id)
                            val tanggalId =
                                "${dataItem?.tanggal?.replace(".", "-")}-$idWithLeadingZeros"
                            etNoPengaduan.setText(tanggalId)
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

        val dataKirim = KirimTeknisiRequest(selectedTeknisi, id)

        viewModel.getUser().observe(this@SaranaActivityAdmin) {
            viewModel.inputLaporanTeknisi(it.getToken, dataKirim)
                .observe(this@SaranaActivityAdmin) { result ->
                    when (result) {
                        is Resource.Loading -> {}

                        is Resource.Success -> {

                            val tanggal = binding.etTanggal.text.toString()

                            val intent =
                                Intent(
                                    this@SaranaActivityAdmin,
                                    ActivityPemberitahuanAdmin::class.java
                                )
                            intent.putExtra(ID_LAPORAN_PEMBERITAHUAN, id.toInt())
                            intent.putExtra("tanggalLaporan", tanggal)
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
                        teknisiList.add(
                            0,
                            TeknisiReponse.TeknisiItem("0", "Silahkan Pilih Teknisi")
                        )

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
                                    selectedTeknisi = if (position > 0) {
                                        teknisiList[position]?.id ?: ""
                                    } else {
                                        ""
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
