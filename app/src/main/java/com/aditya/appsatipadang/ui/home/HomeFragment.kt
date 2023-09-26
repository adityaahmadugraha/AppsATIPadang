package com.aditya.appsatipadang.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.aditya.appsatipadang.databinding.FragmentHomeBinding
import com.aditya.appsatipadang.laporan.kamtibmas.ActivityKamtibmas
import com.aditya.appsatipadang.laporan.prasarana.ActivityPrasarana
import com.aditya.appsatipadang.laporan.sarana.SaranaActivity
import com.aditya.appsatipadang.laporan.status.ActivityStatus

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding?.cardSarana?.setOnClickListener {
            val intent = Intent(activity, SaranaActivity::class.java)
            startActivity(intent)
        }
        binding?.cardPrasarana?.setOnClickListener {
            val intent = Intent(activity, ActivityPrasarana::class.java)
            startActivity(intent)
        }
        binding?.cardKamtibmas?.setOnClickListener {
            val intent = Intent(activity, ActivityKamtibmas::class.java)
            startActivity(intent)
        }
        binding?.cardStatus?.setOnClickListener {
            val intent = Intent(activity, ActivityStatus::class.java)
            startActivity(intent)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}