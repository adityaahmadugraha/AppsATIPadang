package com.aditya.appsatipadang.teknik.ui_teknisi

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.aditya.appsatipadang.R
import com.aditya.appsatipadang.adapter.AdapterLaporan
import com.aditya.appsatipadang.databinding.FragmentHomeBinding
import com.aditya.appsatipadang.databinding.FragmentTHomeBinding
import com.aditya.appsatipadang.teknik.ui_teknisi.laporan.LaporanTeknisiActivity
import com.aditya.appsatipadang.teknik.ui_teknisi.nontifikasi_laporan.ActivityNontofikasiLaporanTeknisi
import com.aditya.appsatipadang.user.laporan.kamtibmas.ActivityKamtibmas
import com.aditya.appsatipadang.user.laporan.prasarana.ActivityPrasarana
import com.aditya.appsatipadang.user.laporan.sarana.SaranaActivity
import com.aditya.appsatipadang.user.laporan.status.ActivityStatus
import com.aditya.appsatipadang.user.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class THomeFragment : Fragment() {

    private var _binding: FragmentTHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTHomeBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.cardLaporan.setOnClickListener {
            val intent = Intent(activity, ActivityNontofikasiLaporanTeknisi::class.java)
            startActivity(intent)
        }
        binding.cardRekapLaporan.setOnClickListener {
            val intent = Intent(activity, LaporanTeknisiActivity::class.java)
            startActivity(intent)
        }

    }



}