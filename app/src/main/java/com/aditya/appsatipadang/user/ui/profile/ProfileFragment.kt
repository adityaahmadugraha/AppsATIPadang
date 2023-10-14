package com.aditya.appsatipadang.user.ui.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.aditya.appsatipadang.R
import com.aditya.appsatipadang.data.local.UserLocal
import com.aditya.appsatipadang.databinding.FragmentProfileBinding
import com.aditya.appsatipadang.user.ui.login.LoginActivity
import com.aditya.appsatipadang.utils.Constant
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var binding: FragmentProfileBinding? = null
    private val viewModel: ProfileViewModel by viewModels()

    private var editMode: Boolean = false

    private var profilePicture: File? = null

    private var user: UserLocal? = null

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

        //add foto profil
        binding?.imgEdit?.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            val chooser = Intent.createChooser(intent, "Choose a Picture")
            launcherIntentGallery.launch(chooser)
        }

//        btnSave.setOnClickListener {
//
//        }



        setupButtonBackClicked()
        getDataUser()

        if (activity != null) {
            checkUserLogin()
            binding?.apply {
                btnLogout.setOnClickListener {
                    showAlertLogout()
                }
            }
        }
    }


    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri

            profilePicture = Constant.uriToFile(selectedImg, requireContext())
            binding?.apply {
                imgEdit.setImageURI(selectedImg)
            }
        }
    }
    //menampilkan data profil
    private fun getDataUser() {
        viewModel.getUser().observe(viewLifecycleOwner) { data ->
            binding?.tvNameProfil?.text = data.name
            binding?.tvJabatanProfil?.text = data.roles
            binding?.etEmail?.setText(data.email)
            binding?.etNotlp?.setText(data.no_telp)
            binding?.etAlamatProfil?.setText(data.alamat)

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
        viewModel.getUser().observe(viewLifecycleOwner) {
            if (it.username.isEmpty()) {
                Intent(requireActivity(), LoginActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(this)
                }
            }
        }
    }

    private fun setupButtonBackClicked() {

        binding?.imgBackProfil?.setOnClickListener {
            activity?.onBackPressed()
        }

    }




}