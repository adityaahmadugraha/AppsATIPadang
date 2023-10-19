package com.aditya.appsatipadang.user.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.aditya.appsatipadang.BuildConfig
import com.aditya.appsatipadang.adapter.AdapterLaporan
import com.aditya.appsatipadang.data.Resource
import com.aditya.appsatipadang.databinding.FragmentHomeBinding
import com.aditya.appsatipadang.user.laporan.kamtibmas.ActivityKamtibmas
import com.aditya.appsatipadang.user.laporan.prasarana.ActivityPrasarana
import com.aditya.appsatipadang.user.laporan.sarana.SaranaActivity
import com.aditya.appsatipadang.user.laporan.status.ActivityStatus
import com.aditya.appsatipadang.user.ui.detailstatuslaporan.DetailStatusLaporanActivity
import com.aditya.appsatipadang.user.ui.detailstatuslaporan.DetailStatusLaporanActivity.Companion.TAG_IDLAPORAN
import com.aditya.appsatipadang.utils.Constant.getToken
import com.bumptech.glide.Glide
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
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.apply {
            cardSarana.setOnClickListener {
                val intent = Intent(activity, SaranaActivity::class.java)
                startActivity(intent)
            }
            cardPrasarana.setOnClickListener {
                val intent = Intent(activity, ActivityPrasarana::class.java)
                startActivity(intent)
            }
            cardKamtibmas.setOnClickListener {
                val intent = Intent(activity, ActivityKamtibmas::class.java)
                startActivity(intent)
            }
            cardStatus.setOnClickListener {
                val intent = Intent(activity, ActivityStatus::class.java)
                startActivity(intent)
            }
        }


        getDataUser()
        setupList()

    }

    private fun getDataUser() {

        viewModel.getUser().observe(viewLifecycleOwner) { data ->
            viewModel.getDataUser(data.getToken).observe(viewLifecycleOwner) { item ->
                when (item) {
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        val dataIem = item.data.user
                        binding.apply {
                            tvName.text = dataIem?.name
                            Glide.with(requireContext())
                                .load(BuildConfig.IMAGE_URL + dataIem?.foto)
                                .into(imgProfil)
                        }
                    }

                    is Resource.Error -> {}
                }
            }

            viewModel.getListLaporan(data.getToken).observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Resource.Loading -> {
                        binding.progressBar.isVisible = true
                    }

                    is Resource.Success -> {
                        binding.progressBar.isVisible = false


                        val allData = result.data.laporan
                        val latest5Data = allData?.take(5)

                        mAdapter.submitList(latest5Data)
                        setupRecyclerView()

                    }

                    is Resource.Error -> {
                        binding.progressBar.isVisible = false
                        Toast.makeText(
                            requireActivity(),
                            result.error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }
            }

        }
    }


    private fun setupList() {
        mAdapter = AdapterLaporan {
            Intent(requireActivity(), DetailStatusLaporanActivity::class.java).apply {
                putExtra(TAG_IDLAPORAN, it.id.toString())
                startActivity(this)
            }
        }
    }


    private fun setupRecyclerView() {
        binding.rvLaporanHome.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireActivity())
            setHasFixedSize(true)
        }
    }
}