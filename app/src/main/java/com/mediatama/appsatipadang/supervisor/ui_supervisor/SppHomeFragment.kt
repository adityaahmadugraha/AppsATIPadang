package com.mediatama.appsatipadang.supervisor.ui_supervisor

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
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.mediatama.appsatipadang.BuildConfig
import com.mediatama.appsatipadang.R
import com.mediatama.appsatipadang.adapter.AdapterLaporan
import com.mediatama.appsatipadang.data.Resource
import com.mediatama.appsatipadang.databinding.FragmentSppHomeBinding
import com.mediatama.appsatipadang.supervisor.LaporanKeseluruhanActivity
import com.mediatama.appsatipadang.utils.Constant.getToken
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SppHomeFragment : Fragment() {

    private var _binding: FragmentSppHomeBinding? = null

    private val binding get() = _binding!!
    private val viewModel: SppHomeViewMoel by viewModels()

    private lateinit var lySwip: SwipeRefreshLayout
    private lateinit var mAdapter: AdapterLaporan
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSppHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.cardRekapLaporan.setOnClickListener {
            val intent = Intent(activity, LaporanKeseluruhanActivity::class.java)
            startActivity(intent)
        }

        lySwip = binding.lySwip
        lySwip.setOnRefreshListener {


            getDataUser()
        }

        getDataUser()
        setupList()

    }


    private fun getDataUser() {
        viewModel.getUser().observe(viewLifecycleOwner) { userLocal ->
            viewModel.getDataUser(userLocal.getToken).observe(viewLifecycleOwner) { item ->
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
//                binding.tvName.text = userLocal.name
//                binding.let {
//                    Glide.with(requireContext())
//                        .load(BuildConfig.IMAGE_URL + userLocal.foto)
//                        .error(android.R.color.darker_gray)
//                        .into(it.imgProfil)
//                }
            }

            viewModel.getListLaporan(userLocal.getToken).observe(viewLifecycleOwner) { result ->
                Log.d(ContentValues.TAG, "getDataUser: ${userLocal.getToken}")
                when (result) {
                    is Resource.Loading -> {
                        binding.progressBar.isVisible = true
                    }

                    is Resource.Success -> {
                        binding.progressBar.isVisible = false

                        lySwip.isRefreshing = false
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
        binding.rvLaporanHome.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireActivity())
            setHasFixedSize(true)
        }
    }

}

