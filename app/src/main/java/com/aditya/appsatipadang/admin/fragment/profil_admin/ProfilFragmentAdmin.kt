package com.aditya.appsatipadang.admin.fragment.profil_admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aditya.appsatipadang.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProfilFragmentAdmin : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profil_admin, container, false)
    }


}