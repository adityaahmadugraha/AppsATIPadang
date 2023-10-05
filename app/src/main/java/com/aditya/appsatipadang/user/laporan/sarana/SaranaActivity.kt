package com.aditya.appsatipadang.user.laporan.sarana

import android.Manifest
import android.app.DatePickerDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.aditya.appsatipadang.user.MainActivity
import com.aditya.appsatipadang.R
import com.aditya.appsatipadang.user.data.Resource
import com.aditya.appsatipadang.user.data.local.UserLocal
import com.aditya.appsatipadang.user.data.remote.request.InputLaporanRequest
import com.aditya.appsatipadang.databinding.ActivitySaranaBinding
import com.aditya.appsatipadang.di.Constant.getToken
import com.aditya.appsatipadang.user.ui.camera.CameraActivity
import com.aditya.appsatipadang.user.ui.camera.rotateFile
import com.aditya.appsatipadang.user.ui.camera.uriToFile
import com.aditya.appsatipadang.user.ui.pemberitahuan.ActivityPemberitahuan
import com.aditya.appsatipadang.user.ui.pemberitahuan.ActivityPemberitahuan.Companion.TAG_ID_LAPORAN
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar


@AndroidEntryPoint
class SaranaActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySaranaBinding
    private val viewModel: SaranaViewModel by viewModels()

    private var user: UserLocal? = null

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


        binding.btnKirim.setOnClickListener {

            getUserInput()
            val chipGroup = findViewById<ChipGroup>(R.id.chipGroup)
            val selectedChipId = chipGroup.checkedChipId

            if (selectedChipId != View.NO_ID) {
                val selectedChip = findViewById<Chip>(selectedChipId)
                val selectedChipText = selectedChip.text.toString()

                Log.d(TAG, "Chip yang dipilih: $selectedChipText")

            } else {
                Toast.makeText(this, "Pilih jenis sarana terlebih dahulu.", Toast.LENGTH_SHORT)
                    .show()
            }
        }


        binding.etTanggal.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(selectedYear, selectedMonth, selectedDayOfMonth)

                    val dateFormat = SimpleDateFormat("dd-MM-yyyy")
                    val formattedDate = dateFormat.format(selectedDate.time)

                    binding.etTanggal.setText(formattedDate)
                },
                year,
                month,
                dayOfMonth
            )

            datePickerDialog.show()
        }



        supportActionBar?.hide()


        getUserData()
        binding.imgBack.setOnClickListener {
            val intent = Intent(this@SaranaActivity, MainActivity::class.java)
            startActivity(intent)
        }



        binding.chipGroup.setOnCheckedChangeListener { group, checkedId ->
            for (i in 0 until group.childCount) {
                val chip = group.getChildAt(i) as Chip
                chip.setChipBackgroundColorResource(android.R.color.background_light)
            }

            if (checkedId != View.NO_ID) {
                val selectedChip = findViewById<Chip>(checkedId)
                selectedChip.setChipBackgroundColorResource(R.color.status_bar)
            }
        }


        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        binding.camera.setOnClickListener { startCameraX() }
        binding.galery.setOnClickListener { startGallery() }
        binding.imgFoto.setOnClickListener { uploadImage() }


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
            val type = "Sarana"
            val tanggal = etTanggal.text.toString()
            val lokasi = etLokasi.text.toString()
            val deskripsiKerusakan = etDeskripsiKerusakan.text.toString()
            val camera = imgFoto.toString()

            val inputLaporanRequest = InputLaporanRequest(
                type,
                tanggal,
                lokasi,
                chip,
                deskripsiKerusakan,
                camera
            )

            Log.d(TAG, "LAPORAN::: $inputLaporanRequest")

            if (validateInput(inputLaporanRequest)) {
                viewModel.inputLaporan(
                    user?.getToken.toString(),
                    inputLaporanRequest
                ).observe(this@SaranaActivity) { item ->
                    when (item) {
                        is Resource.Loading -> {

                        }

                        is Resource.Success -> {
                            Log.d(TAG, "simpanData: ${item.data.id}")
                            if (item.data.status == 200) {
                                val intent =
                                    Intent(this@SaranaActivity, ActivityPemberitahuan::class.java)
                                intent.putExtra(TAG_ID_LAPORAN, item.data.id)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)
                            }
                        }

                        is Resource.Error -> {
                        }
                    }
                }

            }
        }
    }

    private fun validateInput(inputLaporanRequest: InputLaporanRequest): Boolean {
        binding.apply {
            if (inputLaporanRequest.tanggal?.isEmpty() == true) {
                ilTanggal.isErrorEnabled = true
                ilTanggal.error = getString(R.string.must_not_empty)
                return false
            }
            if (inputLaporanRequest.lokasi?.isEmpty() == true) {
                ilLokasi.isErrorEnabled = true
                ilLokasi.error = getString(R.string.must_not_empty)
                return false
            }
            if (inputLaporanRequest.merk?.isEmpty() == true) {
                ilJenisKerusakan.isErrorEnabled = true
                ilJenisKerusakan.error = getString(R.string.must_not_empty)
                return false
            }
            if (binding.etTanggal.text?.isEmpty() == true) {
                binding.ilTanggal.isErrorEnabled = true
                binding.ilTanggal.error = getString(R.string.must_not_empty)
                return false
            }

            if (inputLaporanRequest.deskripsi?.isEmpty() == true) {
                ilDeskripsiKerusakan.isErrorEnabled = true
                ilDeskripsiKerusakan.error = getString(R.string.must_not_empty)
                return false
            }

            return true
        }
    }

    private fun startCameraX() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private fun uploadImage() {
        Toast.makeText(this, "Fitur ini belum tersedia", Toast.LENGTH_SHORT).show()
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.data?.getSerializableExtra("picture", File::class.java)
            } else {
                @Suppress("DEPRECATION")
                it.data?.getSerializableExtra("picture")
            } as? File

            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean

            myFile?.let { file ->
                rotateFile(file, isBackCamera)
                binding.imgFoto.setImageBitmap(BitmapFactory.decodeFile(file.path))
            }
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                val myFile = uriToFile(uri, this@SaranaActivity)
                binding.imgFoto.setImageURI(uri)
            }
        }
    }
}



