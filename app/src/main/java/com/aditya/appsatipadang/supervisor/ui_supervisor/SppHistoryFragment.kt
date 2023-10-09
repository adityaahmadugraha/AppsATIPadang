package com.aditya.appsatipadang.supervisor.ui_supervisor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aditya.appsatipadang.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SppHistoryFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_spp_history, container, false)
    }


}