package com.aditya.appsatipadang.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.aditya.appsatipadang.R
import com.aditya.appsatipadang.data.Resource
import com.aditya.appsatipadang.databinding.FragmentHomeBinding
import com.aditya.appsatipadang.laporan.kamtibmas.ActivityKamtibmas
import com.aditya.appsatipadang.laporan.prasarana.ActivityPrasarana
import com.aditya.appsatipadang.laporan.sarana.SaranaActivity
import com.aditya.appsatipadang.laporan.status.ActivityStatus
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val homeViewModel =
//            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getDataUser()

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
//menampilkan nama dari database
    private fun getDataUser() {
        viewModel.getUser().observe(viewLifecycleOwner) {
            binding.tvName.text = getString(R.string.hello_user, it.name)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}