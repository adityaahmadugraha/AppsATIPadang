package com.aditya.appsatipadang.ui.home

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.datastore.dataStore
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aditya.appsatipadang.R
import com.aditya.appsatipadang.adapter.AdapterLaporan
import com.aditya.appsatipadang.data.Resource
import com.aditya.appsatipadang.databinding.FragmentHomeBinding
import com.aditya.appsatipadang.di.Constant.getToken
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

    private lateinit var mAdapter: AdapterLaporan

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    )
            : View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.cardSarana.setOnClickListener {
            val intent = Intent(activity, SaranaActivity::class.java)
            startActivity(intent)
        }
        binding.cardPrasarana.setOnClickListener {
            val intent = Intent(activity, ActivityPrasarana::class.java)
            startActivity(intent)
        }
        binding.cardKamtibmas.setOnClickListener {
            val intent = Intent(activity, ActivityKamtibmas::class.java)
            startActivity(intent)
        }
        binding.cardStatus.setOnClickListener {
            val intent = Intent(activity, ActivityStatus::class.java)
            startActivity(intent)
        }

//        getDataUser()
//        setupList()
    }

//    private fun getDataUser() {
//        viewModel.getUser().observe(viewLifecycleOwner) { userLocal ->
//            binding.tvName.text = userLocal.name
//
//
//            viewModel.getListLaporan(userLocal.getToken).observe(viewLifecycleOwner) { result ->
//                when (result) {
//                    is Resource.Loading -> {
//                        binding.progressBar.isVisible = true
//                    }
//
//                    is Resource.Success -> {
//                        binding.progressBar.isVisible = false
//
//
//                        Log.d(TAG, "listadapter::::::: ${result.data}")
//
//                        mAdapter.submitList(result.data.laporan)
//                        setupRecyclerView()
//                    }
//
//                    is Resource.Error -> {
//                        binding.progressBar.isVisible = false
//                        Toast.makeText(
//                            requireActivity(),
//                            result.error,
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//
//                }
//            }
//
//        }
//    }


//    private fun setupList() {
//        mAdapter = AdapterLaporan { }
//    }

//    private fun setupRecyclerView() {
//        binding.rvLaporanHome.apply {
//            adapter = mAdapter
//            layoutManager = LinearLayoutManager(requireActivity())
//            setHasFixedSize(true)
//        }
//    }
}