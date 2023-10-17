package com.aditya.appsatipadang.user.ui.profile

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.aditya.appsatipadang.BuildConfig.IMAGE_URL
import com.aditya.appsatipadang.R
import com.aditya.appsatipadang.data.Resource
import com.aditya.appsatipadang.databinding.FragmentProfileBinding
import com.aditya.appsatipadang.user.ui.login.LoginActivity
import com.aditya.appsatipadang.utils.Constant
import com.aditya.appsatipadang.utils.Constant.getToken
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var binding: FragmentProfileBinding? = null
    private val viewModel: ProfileViewModel by viewModels()

    private var profilePicture: File? = null
    private var fotoProfilPath: String? = null

    companion object {
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
                    requireContext(),
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.imgEdit?.setOnClickListener { startGallery() }

        binding?.imgBackProfil?.setOnClickListener {
            activity?.onBackPressed()
        }

        MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart(
                "foto",
                fotoProfilPath,
                RequestBody.create("image/*".toMediaTypeOrNull(), fotoProfilPath.toString())

            ).build()

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        getDataUser()

        if (activity != null) {
            checkUserLogin()
            binding?.apply {
                btnLogout.setOnClickListener {
                    showAlertLogout()
                }
            }
        }

        val permission = Manifest.permission.READ_EXTERNAL_STORAGE
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                permission
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            binding?.imgEdit?.setOnClickListener {
                val intent = Intent()
                intent.action = Intent.ACTION_GET_CONTENT
                intent.type = "image/*"
                val chooser = Intent.createChooser(intent, "Choose a Picture")
                launcherIntentGallery.launch(chooser)

            }
        } else {
            requestPermissions(arrayOf(permission), hashCode())
        }

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
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                profilePicture = Constant.uriToFile(uri, requireContext())
                binding?.imgProfil?.setImageURI(uri)

                val requestBody = RequestBody.create("image/*".toMediaType(), profilePicture!!)
                MultipartBody.Part.createFormData("image", profilePicture!!.name, requestBody)

                uploadFotoProfil()
            }


        }
    }




    private fun uploadFotoProfil() {
        val requestBody = profilePicture?.let { RequestBody.create("image/*".toMediaType(), it) }
        val imagePart = MultipartBody.Part.createFormData("image", profilePicture!!.name, requestBody!!)


        MaterialAlertDialogBuilder(requireContext())
            .setMessage(getString(R.string.saveimage))
            .setPositiveButton(getString(R.string.yes)) { dialog, _ ->
                viewModel.getUser().observe(viewLifecycleOwner) { user ->
                    viewModel.insertFoto(user.id, user.getToken, imagePart).observe(viewLifecycleOwner) { data ->
                        when (data) {
                            is Resource.Loading -> {
                                Log.d("UploadFoto", "Sedang mengunggah foto profil...")
                            }
                            is Resource.Success -> {
                                if (data.data.status == 200) {
                                    Log.d("UploadFoto", "Foto profil berhasil diunggah")
                                    Toast.makeText(requireContext(), "Foto profil berhasil disimpan", Toast.LENGTH_SHORT).show()
                                } else {
                                    Log.e("UploadFoto", "Gagal mengunggah foto profil: Status tidak valid")
                                }
                            }
                            is Resource.Error -> {
                                Log.e("UploadFoto", "Gagal mengunggah foto profil: ${data.error}")
                            }
                        }
                    }
                    dialog.dismiss()
                }
            }
            .setNegativeButton(getString(R.string.no)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }





    private fun getDataUser() {
        viewModel.getUser().observe(viewLifecycleOwner) { data ->

            binding?.apply {
                tvNameProfil.text = data.name
                tvJabatanProfil.text = data.roles
                etEmail.setText(data.email)
                etNotlp.setText(data.no_telp)
                etAlamatProfil.setText(data.alamat)
                let {
                    Glide.with(requireContext())
                        .load(IMAGE_URL + data?.foto)
                        .error(android.R.color.darker_gray)
                        .into(it.imgProfil)
                }

            }

        }
    }

    private fun showAlertLogout() {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(getString(R.string.logoutMessage))
            .setPositiveButton(getString(R.string.yes)) { dialog, _ ->
                viewModel.deleteUser()
                checkUserLogin()
            }
            .setNegativeButton(getString(R.string.no)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()

    }

    private fun checkUserLogin() {
        viewModel.getUser().observe(viewLifecycleOwner) { user ->
            if (user.username.isEmpty()) {
                Intent(requireActivity(), LoginActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(this)
                }
            }
        }
    }

}