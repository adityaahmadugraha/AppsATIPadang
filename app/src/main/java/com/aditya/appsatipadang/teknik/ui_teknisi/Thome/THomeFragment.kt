package com.aditya.appsatipadang.teknik.ui_teknisi.Thome

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.aditya.appsatipadang.BuildConfig
import com.aditya.appsatipadang.R
import com.aditya.appsatipadang.adapter.AdapterLaporanTeknisi
import com.aditya.appsatipadang.data.Resource
import com.aditya.appsatipadang.databinding.FragmentTHomeBinding
import com.aditya.appsatipadang.supervisor.LaporanKeseluruhanActivity
import com.aditya.appsatipadang.teknik.ui_teknisi.laporan.LaporanTeknisiActivity
import com.aditya.appsatipadang.teknik.ui_teknisi.nontifikasi_laporan.ActivityNontofikasiLaporanTeknisi
import com.aditya.appsatipadang.utils.Constant
import com.aditya.appsatipadang.utils.Constant.TAG
import com.aditya.appsatipadang.utils.Constant.getToken
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class THomeFragment : Fragment() {

    private var _binding: FragmentTHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: THomeViewModel by viewModels()
    private lateinit var mAdapter: AdapterLaporanTeknisi
    private lateinit var lySwip: SwipeRefreshLayout

    private val menuArray = arrayOf(
        "Dikerjakan",
        "Selesai"
    )

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


        binding.apply {
            cardLaporan.setOnClickListener {
                val intent = Intent(activity, ActivityNontofikasiLaporanTeknisi::class.java)
                startActivity(intent)
            }
            cardRekapLaporan.setOnClickListener {
                val intent = Intent(activity, LaporanKeseluruhanActivity::class.java)
                startActivity(intent)
            }
        }

        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout

        val adapter = MenuTeknisiAdapter(childFragmentManager, lifecycle)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = menuArray[position]
        }.attach()


        lySwip = binding.lySwip
        lySwip.setOnRefreshListener {

            getDataUser()
        }

        setupList()
        getDataUser()

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
                                .error(R.color.white)
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
                        Log.d(TAG, "getListLaporanTeknisi::::::: ${data.getToken}")
                        binding.progressBar.isVisible = false
                        lySwip.isRefreshing = false


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
        mAdapter = AdapterLaporanTeknisi {
            val intent = Intent(requireContext(), LaporanTeknisiActivity::class.java)
            intent.putExtra(Constant.IDLAPORAN, it.id.toString())
            startActivity(intent)
        }
    }

}


