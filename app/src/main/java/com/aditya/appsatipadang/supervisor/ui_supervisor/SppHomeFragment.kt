package com.aditya.appsatipadang.supervisor.ui_supervisor

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.datastore.preferences.protobuf.Internal.MapAdapter
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.aditya.appsatipadang.R
import com.aditya.appsatipadang.adapter.AdapterLaporanTeknisi
import com.aditya.appsatipadang.adapter.AdapterRakapLaporanTeknisi
import com.aditya.appsatipadang.adapter.AdapterStatusLaporan
import com.aditya.appsatipadang.data.Resource
import com.aditya.appsatipadang.databinding.FragmentSppHomeBinding
import com.aditya.appsatipadang.databinding.FragmentTHomeBinding
import com.aditya.appsatipadang.teknik.ui_teknisi.Thome.THomeViewModel
import com.aditya.appsatipadang.teknik.ui_teknisi.nontifikasi_laporan.ActivityNontofikasiLaporanTeknisi
import com.aditya.appsatipadang.teknik.ui_teknisi.rekap_laporan.RekapActivityTeknisi
import com.aditya.appsatipadang.utils.Constant.getToken
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SppHomeFragment : Fragment() {

    private var _binding: FragmentSppHomeBinding? = null

    private val binding get() = _binding!!
    private val viewModel: THomeViewModel by viewModels()
    private lateinit var mAdapter: AdapterRakapLaporanTeknisi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSppHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.cardRekapLaporan.setOnClickListener {
            val intent = Intent(activity, RekapActivityTeknisi::class.java)
            startActivity(intent)
        }


        getDataUser()
        setupList()

    }

    private fun setupList() {
        mAdapter = AdapterRakapLaporanTeknisi(

        )
    }

    private fun getDataUser() {
        viewModel.getUser().observe(viewLifecycleOwner) { userLocal ->
            binding.tvName.text = userLocal.name


            viewModel.getListPengerjaan(userLocal.getToken).observe(viewLifecycleOwner) { result ->
                Log.d(ContentValues.TAG, "getDataUser: ${userLocal.getToken}")
                when (result) {
                    is Resource.Loading -> {
                        binding.progressBar.isVisible = true
                    }

                    is Resource.Success -> {
                        binding.progressBar.isVisible = false


                        Log.d(ContentValues.TAG, "listadapter::::::: ${result.data}")


                        val latestFiveData = if (result.data.laporan?.size!! > 5)
                            result.data.laporan?.let {
                                result.data.laporan.subList(
                                    result.data.laporan.size - 5,
                                    it.size
                                )
                            }
                        else
                            result.data.laporan


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


    private fun setupRecyclerView() {
        binding?.rvLaporanHome?.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireActivity())
            setHasFixedSize(true)
        }
    }

}

