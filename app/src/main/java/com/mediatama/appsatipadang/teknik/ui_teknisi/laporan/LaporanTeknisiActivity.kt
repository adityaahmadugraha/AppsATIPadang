package com.mediatama.appsatipadang.teknik.ui_teknisi.laporan

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.mediatama.appsatipadang.BuildConfig
import com.mediatama.appsatipadang.R
import com.mediatama.appsatipadang.data.Resource
import com.mediatama.appsatipadang.data.local.UserLocal
import com.mediatama.appsatipadang.databinding.ActivityLaporanTeknisiBinding
import com.mediatama.appsatipadang.teknik.ActivityTeknik
import com.mediatama.appsatipadang.user.ui.pemberitahuan.ActivityPemberitahuan
import com.mediatama.appsatipadang.utils.Constant
import com.mediatama.appsatipadang.utils.Constant.getToken
import com.mediatama.appsatipadang.utils.Constant.setInputError
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


@Suppress("DEPRECATION")
@AndroidEntryPoint
class LaporanTeknisiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLaporanTeknisiBinding
    private val viewModel: LaporanTeknisiViewModel by viewModels()

    private var user: UserLocal? = null

    private var fotoKerusakan: File? = null
    private var fotoKerusakanPath: String? = null

    private var idLaporan = ""
//    private var tanggal = ""

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
        const val TAG_ID_TEKNISI = "idTeknisi"
        const val IDLAPORAN = "idLaporan"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLaporanTeknisiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        idLaporan = intent.getStringExtra(Constant.IDLAPORAN).toString()

        binding.apply {
            btnKirim.setOnClickListener {
                getUserInput()
            }

            getUserData()
            imgBack.setOnClickListener {
                val intent = Intent(this@LaporanTeknisiActivity, ActivityTeknik::class.java)
                startActivity(intent)
            }

            camera.setOnClickListener { startCamera() }
            galery.setOnClickListener { startGallery() }
        }

        getDataLaporan()
        kirimLaporanPerbaikan()

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        Log.d("idLaporan:::::", "onCreate: $idLaporan")
    }

    private fun kirimLaporanPerbaikan() {
        binding.apply {
            btnKirim.setOnClickListener {
                val biaya = etBiaya.text.toString()
                val kegiatan = etKegiatanPerbaikan.text.toString()
                val tindakan = etPihakTerlibat.text.toString()
                if (validateInput(biaya, tindakan, kegiatan, fotoKerusakan)) {

                    viewModel.getUser().observe(this@LaporanTeknisiActivity) {
                        val fileProfilePicture: File =
                            Constant.reduceFileImage(fotoKerusakan as File)

                        val requestBody: RequestBody = MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("biaya", biaya)
                            .addFormDataPart("kegiatan_perbaikan", kegiatan)
                            .addFormDataPart("pihak_terlibat", tindakan)
                            .addFormDataPart("id_laporan", idLaporan)
                            .addFormDataPart("id_teknisi", it.id)
                            .addFormDataPart(
                                "foto",
                                fileProfilePicture.name,
                                RequestBody.create(
                                    "image/*".toMediaTypeOrNull(),
                                    fileProfilePicture
                                )
                            ).build()
                        viewModel.kirimLaporanPerbaikan(it.getToken, requestBody)
                            .observe(this@LaporanTeknisiActivity) { item ->
                                when (item) {
                                    is Resource.Loading -> {}
                                    is Resource.Success -> {
                                        if (item.data.status == 200) {
                                            Toast.makeText(
                                                this@LaporanTeknisiActivity,
                                                "Data Berhasil Dikirm",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            Intent(
                                                this@LaporanTeknisiActivity,
                                                ActivityTeknik::class.java
                                            ).apply {
                                                flags =
                                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                                startActivity(this)
                                            }
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

    private fun validateInput(
        biaya: String,
        tindakan: String,
        kegiatan: String,
        fotoKerusakan: File?
    ):
            Boolean {
        binding.apply {
            if (biaya.isEmpty()) {
                return ilBiaya.setInputError(getString(R.string.must_not_empty))
            }
            if (tindakan.isEmpty()) {
                return ilPihakTerlibat.setInputError(getString(R.string.must_not_empty))
            }
            if (kegiatan.isEmpty()) {
                return ilKegiatanPerbaikan.setInputError(getString(R.string.must_not_empty))
            }
            if (fotoKerusakan == null) {
                Toast.makeText(
                    this@LaporanTeknisiActivity,
                    getString(R.string.pick_photo_first),
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun getDataLaporan() {
        viewModel.getUser().observe(this@LaporanTeknisiActivity) {
            // masukan data berdasarkan id
            viewModel.getLaporanId(it.getToken, idLaporan)
                .observe(this@LaporanTeknisiActivity) { item ->
                    when (item) {
                        is Resource.Loading -> {}
                        is Resource.Success -> {
                            val dataItem = item.data.laporan
                            // kirim data ke dalam tekview
                            binding.apply {
                                etNamaPelapor.setText(dataItem?.name.toString())

                                val idWithLeadingZeros = String.format("%03d", dataItem?.id)
                                val tanggalId = "${dataItem?.tanggal?.replace(".", "-")}-$idWithLeadingZeros"
                                etPengaduan.setText(tanggalId)
                                etType.setText(dataItem?.type)
                                etTangal.setText(dataItem?.tanggal)
                                etLokasi.setText(dataItem?.lokasi)
                                etKegiatanPerbaikan.setText(dataItem?.kegiatanPerbaikan)
                                etPihakTerlibat.setText(dataItem?.pihakTerlibat)
                                etBiaya.setText(dataItem?.biaya.toString())
                            }


                        }

                        is Resource.Error -> {}
                    }
                }
        }
    }

    private fun startCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(this@LaporanTeknisiActivity.packageManager)

        Constant.createCustomTempFile(this@LaporanTeknisiActivity).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this@LaporanTeknisiActivity,
                BuildConfig.APPLICATION_ID,
                it
            )
            fotoKerusakanPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    private fun getUserData() {
        viewModel.getUser().observe(this) {
            user = it
        }
    }

    private fun getUserInput() {

        binding.apply {

            val kegiatan_perbaikan = etKegiatanPerbaikan.text.toString()
            val pihak_terlibat = etPihakTerlibat.text.toString()
            val biaya = etBiaya.text.toString()

            if (validateInput(kegiatan_perbaikan, pihak_terlibat, biaya, fotoKerusakan)) {
                val fileProfilePicture: File = Constant.reduceFileImage(fotoKerusakan as File)

                val requestBody: RequestBody = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("status", "perbaikan")
                    .addFormDataPart("kegiatan_perbaikan", kegiatan_perbaikan)
                    .addFormDataPart("pihak_terlibat", pihak_terlibat)
                    .addFormDataPart("biaya", biaya)
                    .addFormDataPart(
                        "foto",
                        fileProfilePicture.name,
                        RequestBody.create("image/*".toMediaTypeOrNull(), fileProfilePicture)
                    ).build()

                insertLaporan(
                    requestBody
                )
            }
        }
    }

    private fun insertLaporan(requestBody: RequestBody) {
        viewModel.inputLaporan(
            user?.getToken.toString(),
            requestBody
        ).observe(this@LaporanTeknisiActivity) { result ->
            binding.apply {
                when (result) {
                    is Resource.Loading -> {
                        showLoadingInput(true)
                    }

                    is Resource.Success -> {

                        showLoadingInput(false)

                        val intent =
                            Intent(this@LaporanTeknisiActivity, ActivityPemberitahuan::class.java)
                        intent.putExtra(
                            ActivityPemberitahuan.ID_LAPORAN_PEMBERITAHUAN,
                            result.data.id
                        )
                        intent.putExtra("tanggalLaporan", etTangal.text.toString())
                        startActivity(intent)
                        Log.d(
                            "LaporanTeknisiActivity::::::",
                            "Intent ke ActivityPemberitahuan berhasil dilakukan"
                        )


                    }

                    is Resource.Error -> {
                        showLoadingInput(false)
                        Toast.makeText(
                            this@LaporanTeknisiActivity, result.error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

    }

    private fun showLoadingInput(condition: Boolean) {
        binding.apply {
            progressBar.isVisible = condition
            btnKirim.isEnabled = !condition
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
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
                        this@LaporanTeknisiActivity
                    )
                }
                fotoKerusakan = File(uri?.path.toString())

            }
            Glide.with(this@LaporanTeknisiActivity)
                .load(rotatedBitmap)
                .into(binding.imgFoto)
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                fotoKerusakan = Constant.uriToFile(uri, this@LaporanTeknisiActivity)
                binding.imgFoto.setImageURI(uri)
            }
        }
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



