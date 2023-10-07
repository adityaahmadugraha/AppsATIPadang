package com.aditya.appsatipadang.user.laporan.prasarana

import android.Manifest
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
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
import com.aditya.appsatipadang.databinding.ActivityPrasaranaBinding
import com.aditya.appsatipadang.ui.pemberitahuan.ActivityPemberitahuan
import com.aditya.appsatipadang.user.MainActivity
import com.aditya.appsatipadang.user.utils.Constant
import com.aditya.appsatipadang.user.utils.Constant.getToken
import com.aditya.appsatipadang.user.utils.Constant.setInputError
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


@AndroidEntryPoint
class ActivityPrasarana : AppCompatActivity() {

    private lateinit var binding: ActivityPrasaranaBinding
    private val viewModel: PrasaranaViewModel by viewModels()
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

        binding = ActivityPrasaranaBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.apply {
            btnKirim.setOnClickListener {
                getUserInput()
            }
            etTanggal.setOnClickListener {
                setTanggal()
            }

            getUserData()
            imgBackLaporanPrasarana.setOnClickListener {
                val intent = Intent(this@ActivityPrasarana, MainActivity::class.java)
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
        intent.resolveActivity(this@ActivityPrasarana.packageManager)

        Constant.createCustomTempFile(this@ActivityPrasarana).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this@ActivityPrasarana,
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


            if (validateInput(tanggal, lokasi, deskripsiKerusakan, fotoKerusakan)) {
                val fileProfilePicture: File = Constant.reduceFileImage(fotoKerusakan as File)

                val rType = type.toRequestBody("text/plain".toMediaType())
                val rLokasi = lokasi.toRequestBody("text/plain".toMediaType())
                val rDeskripsi = deskripsiKerusakan.toRequestBody("text/plain".toMediaType())
                val rTanggal = tanggal.toRequestBody("text/plain".toMediaType())

                val fotoAlat =
                    fileProfilePicture.asRequestBody("image/jpeg".toMediaTypeOrNull())
                val foto: MultipartBody.Part = MultipartBody.Part.createFormData(
                    "foto",
                    fileProfilePicture.name,
                    fotoAlat
                )

                insertLaporan(
                    foto, rType, rTanggal, rLokasi, rDeskripsi,
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
                    this@ActivityPrasarana,
                    getString(R.string.pick_photo_first),
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }
        }
        return true
    }


    private fun getDataLaporan() {
        binding.apply {

            val lokasi = etLokasi.text.toString()
            val deskripsi = etDeskripsiKerusakan.text.toString()
            val tanggal = etTanggal.text.toString()

        }

    }


    private fun insertLaporan(
        foto: MultipartBody.Part,
        type: RequestBody,
        tanggal: RequestBody,
        lokasi: RequestBody,
        deskripsi: RequestBody
    ) {
        viewModel.inputLaporanPrasana(
            user?.getToken.toString(),
            type,
            tanggal,
            lokasi,
            deskripsi,
            foto
        )
            .observe(this@ActivityPrasarana) { result ->
                binding.apply {
                    when (result) {
                        is Resource.Loading -> {
                            showLoadingInput(true)
                        }

                        is Resource.Success -> {
                            showLoadingInput(false)
                            Intent(
                                this@ActivityPrasarana,
                                ActivityPemberitahuan::class.java
                            ).apply {
                                putExtra(ActivityPemberitahuan.TAG_ID_LAPORAN, result.data.id)
                                flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(this)
                            }
                        }

                        is Resource.Error -> {
                            showLoadingInput(false)
                            Toast.makeText(
                                this@ActivityPrasarana, result.error,
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
                        this@ActivityPrasarana
                    )
                }
                fotoKerusakan = File(uri?.path.toString())

            }
            Glide.with(this@ActivityPrasarana)
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
                fotoKerusakan = Constant.uriToFile(uri, this@ActivityPrasarana)
                binding.imgFoto.setImageURI(uri)
            }
        }
    }
}


