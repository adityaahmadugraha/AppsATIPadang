package com.aditya.appsatipadang.laporan.sarana

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
import com.aditya.appsatipadang.MainActivity
import com.aditya.appsatipadang.R
import com.aditya.appsatipadang.data.Resource
import com.aditya.appsatipadang.data.remote.response.ItemLaporaneResponse
import com.aditya.appsatipadang.databinding.ActivitySaranaBinding
import com.aditya.appsatipadang.di.Constant.getToken
import com.aditya.appsatipadang.ui.camera.CameraActivity
import com.aditya.appsatipadang.ui.camera.rotateFile
import com.aditya.appsatipadang.ui.camera.uriToFile
import com.aditya.appsatipadang.ui.pemberitahuan.ActivityPemberitahuan
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar


@AndroidEntryPoint
class SaranaActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySaranaBinding
    private val viewModel: SaranaViewModel by viewModels()

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
            checkInput()
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


        binding.imgBack.setOnClickListener {
            val intent = Intent(this@SaranaActivity, MainActivity::class.java)
            startActivity(intent)
        }

        binding.chipGroup.setOnCheckedChangeListener { group, checkedId ->
            for (i in 0 until group.childCount) {
                val chip = group.getChildAt(i) as Chip
                chip.setChipBackgroundColorResource(android.R.color.white)
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

    private fun checkInput() {
        binding.apply {
            val id = taskId
            val chip = chipGroup.toString()
            val tanggal = etTanggal.toString()
            val lokasi = etLokasi.toString()
            val jenisKerusakan = etJenisKerusakan.toString()
            val desakripsiKerusakan = etDeskripsiKerusakan.toString()
            val camera = imgFoto.toString()

            val itemLaporanResponse = ItemLaporaneResponse(id, chip, tanggal,  lokasi, jenisKerusakan, desakripsiKerusakan, camera)
            if (validateInput(itemLaporanResponse)) {

            }
                viewModel.getUser().observe(this@SaranaActivity) {
                viewModel.inputLaporan(it.getToken,itemLaporanResponse).observe(this@SaranaActivity) { item ->
                        when (item) {
                            is Resource.Loading -> {

                            }

                            is Resource.Success -> {
                                Log.d(TAG, "simpanData: ${item.data}")
                                if (item.data.status == 200) {
                                    Intent(
                                        this@SaranaActivity,
                                        ActivityPemberitahuan::class.java
                                    ).also { mov ->
                                        mov.flags =
                                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                        startActivity(mov)
                                    }
                                }
                            }

                            is Resource.Error -> {

                            }
                        }
                    }

            }
        }
    }

    private fun validateInput(inputLaporanRequest: ItemLaporaneResponse): Boolean {
        binding.apply {
            if (inputLaporanRequest.tanggal!!.isEmpty()) {
                ilTanggal.isErrorEnabled = true
                ilTanggal.error = getString(R.string.must_not_empty)
                return false
            }
            if (inputLaporanRequest.lokasi!!.isEmpty()) {
                ilLokasi.isErrorEnabled = true
                ilLokasi.error = getString(R.string.must_not_empty)
                return false
            }
            if (inputLaporanRequest.merk!!.isEmpty()) {
                ilJenisKerusakan.isErrorEnabled = true
                ilJenisKerusakan.error = getString(R.string.must_not_empty)
                return false
            }
            if (inputLaporanRequest.deskripsi!!.isEmpty()) {
                ilDeskripsiKerusakan.isErrorEnabled = true
                ilDeskripsiKerusakan.error = getString(R.string.must_not_empty)
                return false
            }
//            if (inputLaporanRequest.gambar.toString().isEmpty()) {
//                imgFoto.isErrorEnabled = true
//                imgFoto.error = getString(R.string.must_not_empty)
//                return false
//            }
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



