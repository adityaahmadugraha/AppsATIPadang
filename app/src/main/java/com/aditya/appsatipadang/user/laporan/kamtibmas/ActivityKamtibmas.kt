package com.aditya.appsatipadang.user.laporan.kamtibmas

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aditya.appsatipadang.user.MainActivity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.aditya.appsatipadang.R
import com.aditya.appsatipadang.data.local.UserLocal
import com.aditya.appsatipadang.data.remote.request.InputKamtibmasRequest
import java.text.SimpleDateFormat
import java.util.Calendar

import com.aditya.appsatipadang.databinding.ActivityKamtibmasBinding
import com.aditya.appsatipadang.user.laporan.sarana.SaranaActivity
import com.aditya.appsatipadang.ui.camera.CameraActivity
import com.aditya.appsatipadang.ui.pemberitahuan.ActivityPemberitahuan
import com.aditya.appsatipadang.user.utils.Constant.rotateFile
import com.aditya.appsatipadang.user.utils.Constant.uriToFile
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class ActivityKamtibmas : AppCompatActivity() {

    private lateinit var binding: ActivityKamtibmasBinding
    private val viewModel: KamtibmasViewModel by viewModels()
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

        binding = ActivityKamtibmasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.etTanggalKamtibmas.setOnClickListener {
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

                    binding.etTanggalKamtibmas.setText(formattedDate)
                },
                year,
                month,
                dayOfMonth
            )

            datePickerDialog.show()
        }

        binding.etWaktuKamtibmas.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(
                this,
                { _, selectedHour, selectedMinute ->
                    val formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute)

                    binding.etWaktuKamtibmas.setText(formattedTime)
                },
                hour,
                minute,
                true
            )

            timePickerDialog.show()
        }

        supportActionBar?.hide()

        binding.imgBackKamtibmas.setOnClickListener() {
            val intent = Intent(this@ActivityKamtibmas, MainActivity::class.java)
            startActivity(intent)
        }
        binding.btnKirimKamtibmas.setOnClickListener {
            intent = Intent(this@ActivityKamtibmas, ActivityPemberitahuan::class.java)
            startActivity(intent)
        }

        getUserData()

        binding.btnKirimKamtibmas.setOnClickListener {

            getUserInput()
        }

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }
        binding.cameraKamtibmas.setOnClickListener { startCameraX() }
        binding.galeryKamtibmas.setOnClickListener { startGallery() }
        binding.imgFotoKamtibmas.setOnClickListener { uploadImage() }
    }


    private fun getUserData() {
        viewModel.getUser().observe(this) {
            user = it
        }
    }

    private fun getUserInput() {

        val type = "Kamtibmas"


        binding.apply {
            val type = "Kamtibmas"
            val lokasi = etLokasiKamtibmas.text.toString()
            val deskripsi = etDeskripsiKerusakanKamtibmas.text.toString()
            val tanggal = etTanggalKamtibmas.text.toString()
            val waktu = etWaktuKamtibmas.text.toString()
            val camera = imgFotoKamtibmas.toString()

            val inputKamtibmasRequest = InputKamtibmasRequest(
                type,
                lokasi,
                deskripsi,
                tanggal,
                waktu,
//                camera
            )

//            Log.d(ContentValues.TAG, "LAPORAN::: $inputKamtibmasRequest")
//
//            if (validateInput(inputKamtibmasRequest)) {
//                viewModel.inputLaporanKamtibmas(
//                    user?.getToken.toString(),
//                    inputKamtibmasRequest
//                ).observe(this@ActivityKamtibmas) { item ->
//                    when (item) {
//                        is Resource.Loading -> {
//
//                        }
//
//                        is Resource.Success -> {
//                            Log.d(ContentValues.TAG, "simpanData: ${item.data.id}")
//                            if (item.data.status == 200) {
//                                val intent = Intent(
//                                    this@ActivityKamtibmas,
//                                    ActivityPemberitahuan::class.java
//                                )
//                                intent.putExtra(ActivityPemberitahuan.TAG_ID_LAPORAN, item.data.id)
//                                intent.flags =
//                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                                startActivity(intent)
//                            }
//                        }
//
//                        is Resource.Error -> {
//                        }
//                    }
//                }
//
//            }

        }
    }

    private fun validateInput(inputLaporanRequest: InputKamtibmasRequest): Boolean {
        binding.apply {
            if (inputLaporanRequest.lokasi?.isEmpty() == true) {
                ilLokasiKamtibmas.isErrorEnabled = true
                ilLokasiKamtibmas.error = getString(R.string.must_not_empty)
                return false
            }
            if (inputLaporanRequest.deskripsi?.isEmpty() == true) {
                ilDeskripsiKerusakanKambtimas.isErrorEnabled = true
                ilDeskripsiKerusakanKambtimas.error = getString(R.string.must_not_empty)
                return false
            }
            if (binding.etTanggalKamtibmas.text?.isEmpty() == true) {
                binding.ilTanggalKamtibmas.isErrorEnabled = true
                binding.ilTanggalKamtibmas.error = getString(R.string.must_not_empty)
                return false
            }
            if (binding.etWaktuKamtibmas.text?.isEmpty() == true) {
                binding.ilWaktuKamtibmas.isErrorEnabled = true
                binding.ilWaktuKamtibmas.error = getString(R.string.must_not_empty)
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
        if (it.resultCode == SaranaActivity.CAMERA_X_RESULT) {
            val myFile = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.data?.getSerializableExtra("picture", File::class.java)
            } else {
                @Suppress("DEPRECATION")
                it.data?.getSerializableExtra("picture")
            } as? File

            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean

            myFile?.let { file ->
                rotateFile(file, isBackCamera)
                binding.imgFotoKamtibmas.setImageBitmap(BitmapFactory.decodeFile(file.path))
            }
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                val myFile = uriToFile(uri, this@ActivityKamtibmas)
                binding.imgFotoKamtibmas.setImageURI(uri)
            }
        }
    }
}



