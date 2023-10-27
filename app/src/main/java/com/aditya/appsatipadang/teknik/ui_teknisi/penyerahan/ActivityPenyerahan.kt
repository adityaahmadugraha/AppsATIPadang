package com.aditya.appsatipadang.teknik.ui_teknisi.penyerahan

import android.Manifest
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Rect
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import com.aditya.appsatipadang.BuildConfig
import com.aditya.appsatipadang.R
import com.aditya.appsatipadang.adapter.AdapterLaporan
import com.aditya.appsatipadang.data.Resource
import com.aditya.appsatipadang.data.local.UserLocal
import com.aditya.appsatipadang.databinding.ActivityLaporanTeknisiBinding
import com.aditya.appsatipadang.databinding.ActivityPenyerahanBinding
import com.aditya.appsatipadang.teknik.ActivityTeknik
import com.aditya.appsatipadang.teknik.ui_teknisi.laporan.LaporanTeknisiViewModel
import com.aditya.appsatipadang.user.ui.pemberitahuan.ActivityPemberitahuan
import com.aditya.appsatipadang.utils.Constant
import com.aditya.appsatipadang.utils.Constant.getToken
import com.aditya.appsatipadang.utils.Constant.setInputError
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@Suppress("DEPRECATION")
@AndroidEntryPoint
class ActivityPenyerahan : AppCompatActivity() {

    private lateinit var binding: ActivityPenyerahanBinding
    private val viewModel: PenyerahanViewModel by viewModels()

    private var user: UserLocal? = null

    private var fotoKerusakan: File? = null
    private var fotoKerusakanPath: String? = null

    private var idLaporan = ""

//    companion object {
//        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
//        private const val REQUEST_CODE_PERMISSIONS = 10
//
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPenyerahanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnKirim.setOnClickListener {
                getUserInput()
            }

            getUserData()

            imgBack.setOnClickListener {
                val intent = Intent(this@ActivityPenyerahan, ActivityTeknik::class.java)
                startActivity(intent)
            }

            etTanggal.setOnClickListener {
                setTanggal()
            }

            camera.setOnClickListener { startCamera() }
            galery.setOnClickListener { startGallery() }
        }


        laporanPenyerahan()
        getUserData()

    }

    private fun laporanPenyerahan() {
        binding.apply {
            btnKirim.setOnClickListener {
                val nama_penerima = etNamaPenerima.text.toString()
                val no_pengaduan = etNoPengaduan.text.toString()
                val tanggal = etTanggal.text.toString()

                if (validateInput(nama_penerima, no_pengaduan, tanggal, fotoKerusakan)) {

                    viewModel.getUser().observe(this@ActivityPenyerahan) {
                        val fileProfilePicture: File =
                            Constant.reduceFileImage(fotoKerusakan as File)

                        val requestBody: RequestBody = MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("no_pengaduan", no_pengaduan)
                            .addFormDataPart("nama_penerima", nama_penerima)
                            .addFormDataPart("tgl_diserahkan", tanggal)
                            .addFormDataPart(
                                "foto",
                                fileProfilePicture.name,
                                RequestBody.create(
                                    "image/*".toMediaTypeOrNull(),
                                    fileProfilePicture
                                )
                            ).build()
                        viewModel.inputPenyerahan(it.getToken, requestBody)
                            .observe(this@ActivityPenyerahan) { item ->
                                when (item) {
                                    is Resource.Loading -> {}
                                    is Resource.Success -> {
                                        if (item.data.status == 200) {
                                            Toast.makeText(
                                                this@ActivityPenyerahan,
                                                "Laporan Berhasil Dikirim",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }

                                    is Resource.Error -> {}
                                }
                            }
                    }
                }
            }
        }
    }

    private fun getUserData() {
        viewModel.getUser().observe(this) {
            user = it
        }
    }

    private fun getUserInput() {

        binding.apply {

            val nama_penerima = etNamaPenerima.text.toString()
            val no_pengaduan = etNoPengaduan.text.toString()
            val tanggal = etTanggal.text.toString()

            if (validateInput(nama_penerima, no_pengaduan, tanggal, fotoKerusakan)) {
                val fileProfilePicture: File = Constant.reduceFileImage(fotoKerusakan as File)

                val requestBody: RequestBody = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("no_pengaduan", no_pengaduan)
                    .addFormDataPart("nama_penerima", nama_penerima)
                    .addFormDataPart("tgl_diserahkan", tanggal)
                    .addFormDataPart(
                        "foto",
                        fileProfilePicture.name,
                        RequestBody.create(
                            "image/*".toMediaTypeOrNull(),
                            fileProfilePicture
                        )
                    ).build()

                insertLaporan(
                    requestBody
                )
            }
        }
    }

    private fun insertLaporan(requestBody: RequestBody) {
        viewModel.inputPenyerahan(
            user?.getToken.toString(),
            requestBody
        ).observe(this@ActivityPenyerahan) { result ->
            binding.apply {
                when (result) {
                    is Resource.Loading -> {}

                    is Resource.Success -> {

                    }

                    is Resource.Error<*> -> {

                        Toast.makeText(
                            this@ActivityPenyerahan, result.error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

    }

    private fun validateInput(
        namaPenerima: String,
        noPengaduan: String,
        tanggal: String,
        fotoKerusakan: File?
    ):
            Boolean {
        binding.apply {
            if (namaPenerima.isEmpty()) {
                return ilNamaPenerima.setInputError(getString(R.string.must_not_empty))
            }
            if (noPengaduan.isEmpty()) {
                return ilNoPengaduan.setInputError(getString(R.string.must_not_empty))
            }
            if (tanggal.isEmpty()) {
                return ilTanggal.setInputError(getString(R.string.must_not_empty))
            }
            if (fotoKerusakan == null) {
                Toast.makeText(
                    this@ActivityPenyerahan,
                    getString(R.string.pick_photo_first),
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }
        }
        return true
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                fotoKerusakan = Constant.uriToFile(uri, this@ActivityPenyerahan)
                binding.imgFoto.setImageURI(uri)
            }
        }
    }

    private fun startCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(this@ActivityPenyerahan.packageManager)

        Constant.createCustomTempFile(this@ActivityPenyerahan).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this@ActivityPenyerahan,
                BuildConfig.APPLICATION_ID,
                it
            )
            fotoKerusakanPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val photoShooted = File(fotoKerusakanPath.toString())
            val rotatedBitmap = Constant.getRotatedBitmap(photoShooted)
            lifecycleScope.launch(Dispatchers.IO) {
                val uri = rotatedBitmap?.let { it1 ->
                    Constant.bitmapToFile(
                        it1,
                        this@ActivityPenyerahan
                    )
                }
                fotoKerusakan = File(uri?.path.toString())

            }
            Glide.with(this@ActivityPenyerahan)
                .load(rotatedBitmap)
                .into(binding.imgFoto)
        }
    }

    private fun setTanggal() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(selectedYear, selectedMonth, selectedDayOfMonth)

                val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale("in", "ID"))
                val formattedDate = dateFormat.format(selectedDate.time)

                binding.etTanggal.setText(formattedDate)
            },
            year,
            month,
            dayOfMonth
        )
        datePickerDialog.show()
    }

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