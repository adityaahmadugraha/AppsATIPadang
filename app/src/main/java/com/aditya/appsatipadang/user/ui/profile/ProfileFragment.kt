package com.aditya.appsatipadang.user.ui.profile

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
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

    private lateinit var lySwip: SwipeRefreshLayout

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

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        lySwip = binding!!.lySwip
        lySwip.setOnRefreshListener {

            getDataUser()
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
                profilePicture = Constant.uriToFile(
                    uri, requireContext()
                )
                //menambahkan ini disini, agar tidak refresh agar foto barubah
                uploadFotoProfil()
            }
        }
    }


    private fun uploadFotoProfil() {
        val fileProfilePicture: File = Constant.reduceFileImage(profilePicture as File)
        val requestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)

            .addFormDataPart(
                "foto",
                fileProfilePicture.name,
                RequestBody.create("image/*".toMediaTypeOrNull(), fileProfilePicture)
            ).build()

        MaterialAlertDialogBuilder(requireContext())
            .setMessage(getString(R.string.saveimage))

            .setPositiveButton(getString(R.string.yes)) { dialog, _ ->

                viewModel.getUser().observe(viewLifecycleOwner) { user ->


                    viewModel.insertFoto(
                        user?.getToken.toString(), requestBody
                    ).observe(viewLifecycleOwner) { data ->
                        when (data) {
                            is Resource.Loading -> {
                                Log.d("UploadFoto", "Sedang mengunggah foto profil...")
                            }

                            is Resource.Success -> {
                                if (data.data.status == 200) {
                                    Log.d("UploadFoto", "Foto profil berhasil diunggah")
                                    Toast.makeText(
                                        requireContext(),
                                        "Foto profil berhasil disimpan",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    getDataUser()
                                } else {
                                    Log.e(
                                        "UploadFoto",
                                        "Gagal mengunggah foto profil: Status tidak valid"
                                    )
                                }
                            }

                            is Resource.Error -> {
                                Log.e(
                                    "UploadFoto",
                                    "Gagal mengunggah foto profil: ${data.error}"
                                )
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


            viewModel.getDataUser(data.getToken).observe(viewLifecycleOwner) { item ->


                when (item) {
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        val dataIem = item.data.user
                        binding?.apply {
                            tvNameProfil.text = dataIem?.name
                            tvJabatanProfil.text = dataIem?.roles
                            etEmail.setText(dataIem?.email)
                            etNotlp.setText(dataIem?.noTelp)
                            etAlamatProfil.setText(dataIem?.alamat)

                            Glide.with(requireContext())
                                .load(IMAGE_URL + dataIem?.foto)
                                .error(R.color.status_bar)
                                .into(imgProfil)

                            lySwip.isRefreshing = false
                        }

                    }

                    is Resource.Error -> {}
                }
            }
        }
    }

    private fun showAlertLogout() {


        val customView = LayoutInflater.from(requireContext()).inflate(R.layout.custom_logout, null)

        val yesString = getString(R.string.yes)
        val noString = getString(R.string.no)

        val yesSpannable = SpannableString(yesString)
        val noSpannable = SpannableString(noString)

        val typeface = ResourcesCompat.getFont(requireContext(), R.font.poppinssembiold) // Ganti dengan font yang Anda inginkan
        typeface?.let {
            yesSpannable.setSpan(StyleSpan(Typeface.BOLD), 0, yesSpannable.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            yesSpannable.setSpan(CustomTypefaceSpan(""), 0, yesSpannable.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            noSpannable.setSpan(StyleSpan(Typeface.BOLD), 0, noSpannable.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            noSpannable.setSpan(CustomTypefaceSpan(""), 0, noSpannable.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        MaterialAlertDialogBuilder(requireContext())
            .setView(customView)
            .setPositiveButton(yesSpannable) { dialog, _ ->
                viewModel.deleteUser()
                checkUserLogin()
            }
            .setNegativeButton(noSpannable) { dialog, _ ->
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