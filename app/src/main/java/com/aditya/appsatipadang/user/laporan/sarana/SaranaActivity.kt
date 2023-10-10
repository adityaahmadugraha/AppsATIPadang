package com.aditya.appsatipadang.user.laporan.sarana

import android.Manifest
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.aditya.appsatipadang.BuildConfig
import com.aditya.appsatipadang.R
import com.aditya.appsatipadang.data.Resource
import com.aditya.appsatipadang.data.local.UserLocal
import com.aditya.appsatipadang.data.remote.request.InputLaporanRequest
import com.aditya.appsatipadang.databinding.ActivitySaranaBinding
import com.aditya.appsatipadang.user.ui.pemberitahuan.ActivityPemberitahuan
import com.aditya.appsatipadang.user.ui.pemberitahuan.ActivityPemberitahuan.Companion.TAG_ID_LAPORAN
import com.aditya.appsatipadang.user.MainActivity
import com.aditya.appsatipadang.utils.Constant.bitmapToFile
import com.aditya.appsatipadang.utils.Constant.createCustomTempFile
import com.aditya.appsatipadang.utils.Constant.getRotatedBitmap
import com.aditya.appsatipadang.utils.Constant.getToken
import com.aditya.appsatipadang.utils.Constant.reduceFileImage
import com.aditya.appsatipadang.utils.Constant.setInputError
import com.aditya.appsatipadang.utils.Constant.uriToFile
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@Suppress("DEPRECATION")
@AndroidEntryPoint
class SaranaActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySaranaBinding
    private val viewModel: SaranaViewModel by viewModels()
    private var user: UserLocal? = null

    private var fotoKerusakan: File? = null
    private var fotoKerusakanPath: String? = null

    companion object {
        const val CAMERA_X_RESULT = 200
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySaranaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnKirim.setOnClickListener {
                getUserInput()
            }

            etTanggal.setOnClickListener {
                setTanggal()
            }

            getUserData()
            imgBack.setOnClickListener {
                val intent = Intent(this@SaranaActivity, MainActivity::class.java)
                startActivity(intent)
            }

            chipGroup.setOnCheckedChangeListener { group, checkedId ->
                for (i in 0 until group.childCount) {
                    val chip = group.getChildAt(i) as Chip
                    chip.setChipBackgroundColorResource(android.R.color.background_light)
                }

                if (checkedId != View.NO_ID) {
                    val selectedChip = findViewById<Chip>(checkedId)
                    selectedChip.setChipBackgroundColorResource(R.color.status_bar)
                }
            }

            camera.setOnClickListener { startCamera() }
            galery.setOnClickListener { startGallery() }
        }


        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }
    }

    private fun startCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(this@SaranaActivity.packageManager)

        createCustomTempFile(this@SaranaActivity).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this@SaranaActivity,
                BuildConfig.APPLICATION_ID,
                it
            )
            fotoKerusakanPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
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

    private fun getUserData() {
        viewModel.getUser().observe(this) {
            user = it
        }
    }

    private fun getUserInput() {
        val type = "Sarana"
        val chipGroup = findViewById<ChipGroup>(R.id.chipGroup)
        val selectedChipId = chipGroup.checkedChipId
        val chip: String

        if (selectedChipId != View.NO_ID) {
            val selectedChip = findViewById<Chip>(selectedChipId)
            chip = selectedChip.text.toString()
        } else {
            Toast.makeText(this, "Pilih jenis sarana terlebih dahulu.", Toast.LENGTH_SHORT).show()
            return
        }

        binding.apply {
            val tanggal = etTanggal.text.toString()
            val lokasi = etLokasi.text.toString()
            val deskripsiKerusakan = etDeskripsiKerusakan.text.toString()
            val merk = etMerk.text.toString()

            if (validateInput(tanggal, lokasi, deskripsiKerusakan, fotoKerusakan)) {
                val fileProfilePicture: File = reduceFileImage(fotoKerusakan as File)

                val requestBody: RequestBody = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("type", type)
                    .addFormDataPart("lokasi", lokasi)
                    .addFormDataPart("deskripsi", deskripsiKerusakan)
                    .addFormDataPart("tanggal", tanggal)
                    .addFormDataPart("merek", merk)
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

    private fun validateInput(
        tanggal: String,
        lokasi: String,
        deskripsiKerusakan: String,
        fotoKerusakan: File?
    ): Boolean {
        binding.apply {
            if (tanggal.isEmpty()) {
                return ilTanggal.setInputError(getString(R.string.must_not_empty))
            }
            if (lokasi.isEmpty()) {
                return ilLokasi.setInputError(getString(R.string.must_not_empty))
            }
            if (deskripsiKerusakan.isEmpty()) {
                return ilDeskripsiKerusakan.setInputError(getString(R.string.must_not_empty))
            }
            if (fotoKerusakan == null) {
                Toast.makeText(
                    this@SaranaActivity,
                    getString(R.string.pick_photo_first),
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }
        }
        return true
    }

    private fun insertLaporan(
        requestBody : RequestBody
    ) {
        viewModel.inputLaporan(
            user?.getToken.toString(),
        requestBody
        ).observe(this@SaranaActivity) { result ->
                binding.apply {
                    when (result) {
                        is Resource.Loading -> {
                            showLoadingInput(true)
                        }

                        is Resource.Success -> {

                            showLoadingInput(false)
                            Intent(
                                this@SaranaActivity, ActivityPemberitahuan::class.java
                            )
                                .apply {
//                                Log.d("INTENTDATA:::", TAG_ID_LAPORAN)
//                                putExtra(TAG_ID_LAPORAN, result.data.id)

                                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(this)
                            }
                        }

                        is Resource.Error -> {
                            showLoadingInput(false)
                            Toast.makeText(
                                this@SaranaActivity, result.error,
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
            val rotatedBitmap = getRotatedBitmap(photoShooted)
            lifecycleScope.launch(Dispatchers.IO) {
                val uri = rotatedBitmap?.let { it1 ->
                    bitmapToFile(
                        it1,
                        this@SaranaActivity
                    )
                }
                fotoKerusakan = File(uri?.path.toString())

            }
            Glide.with(this@SaranaActivity)
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
                fotoKerusakan = uriToFile(uri, this@SaranaActivity)
                binding.imgFoto.setImageURI(uri)
            }
        }
    }
}



