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
import com.aditya.appsatipadang.BuildConfig
import com.aditya.appsatipadang.R
import com.aditya.appsatipadang.adapter.AdapterLaporan
import com.aditya.appsatipadang.adapter.AdapterLaporanTeknisi
import com.aditya.appsatipadang.adapter.AdapterRakapLaporanTeknisi
import com.aditya.appsatipadang.adapter.AdapterStatusLaporan
import com.aditya.appsatipadang.admin.ui.status_admin.StatusActivityAdmin
import com.aditya.appsatipadang.data.Resource
import com.aditya.appsatipadang.databinding.FragmentSppHomeBinding
import com.aditya.appsatipadang.databinding.FragmentTHomeBinding
import com.aditya.appsatipadang.teknik.ui_teknisi.Thome.THomeViewModel
import com.aditya.appsatipadang.teknik.ui_teknisi.nontifikasi_laporan.ActivityNontofikasiLaporanTeknisi
import com.aditya.appsatipadang.teknik.ui_teknisi.rekap_laporan.RekapActivityTeknisi
import com.aditya.appsatipadang.utils.Constant.getToken
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SppHomeFragment : Fragment() {

    private var _binding: FragmentSppHomeBinding? = null

    private val binding get() = _binding!!
    private val viewModel: THomeViewModel by viewModels()


    private lateinit var mAdapter: AdapterLaporan
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
            val intent = Intent(activity, StatusActivityAdmin::class.java)
            startActivity(intent)
        }


        getDataUser()
        setupList()

    }



    private fun getDataUser() {
        viewModel.getUser().observe(viewLifecycleOwner) { userLocal ->
            binding.tvName.text = userLocal.name
            binding.let {
                Glide.with(requireContext())
                    .load(BuildConfig.IMAGE_URL + userLocal.foto)
                    .error(android.R.color.darker_gray)
                    .into(it.imgProfil) }

            viewModel.getListLaporan(userLocal.getToken).observe(viewLifecycleOwner) { result ->
                Log.d(ContentValues.TAG, "getDataUser: ${userLocal.getToken}")
                when (result) {
                    is Resource.Loading -> {
                        binding.progressBar.isVisible = true
                    }

                    is Resource.Success -> {
                        binding.progressBar.isVisible = false


                        Log.d(ContentValues.TAG, "listadapter::::::: ${result.data}")

                        mAdapter.submitList(result.data.laporan)
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

