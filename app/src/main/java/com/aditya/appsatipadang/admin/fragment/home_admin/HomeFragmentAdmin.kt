package com.aditya.appsatipadang.admin.fragment.home_admin

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.aditya.appsatipadang.BuildConfig
import com.aditya.appsatipadang.R
import com.aditya.appsatipadang.adapter.AdapterHomeLaporan
import com.aditya.appsatipadang.admin.fragment.history_admin.HistoryAdminViewModel
import com.aditya.appsatipadang.admin.ui.pengaduan_admin.PengaduanActivity
import com.aditya.appsatipadang.admin.ui.sarana_admin.SaranaActivityAdmin
import com.aditya.appsatipadang.admin.ui.sarana_admin.SaranaActivityAdmin.Companion.TAG_ID_PENGADUAN
import com.aditya.appsatipadang.data.Resource
import com.aditya.appsatipadang.databinding.FragmentHomeAdminBinding
import com.aditya.appsatipadang.supervisor.LaporanKeseluruhanActivity
import com.aditya.appsatipadang.user.ui.profile.CustomTypefaceSpan
import com.aditya.appsatipadang.utils.Constant.getToken
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragmentAdmin : Fragment() {

    private var _binding: FragmentHomeAdminBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HistoryAdminViewModel by viewModels()

    private lateinit var mAdapter: AdapterHomeLaporan

    private lateinit var lySwip: SwipeRefreshLayout
    var idLaporan = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeAdminBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.apply {
            cardLaporan.setOnClickListener {
                val intent = Intent(activity, LaporanKeseluruhanActivity::class.java)
                startActivity(intent)
            }
            cardSaranaAdmin.setOnClickListener {
                val intent = Intent(activity, PengaduanActivity::class.java)
                startActivity(intent)
            }
        }

        lySwip = binding.lySwip
        lySwip.setOnRefreshListener {
            getDataUser()
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
                        val dataItem = item.data.user
                        binding.apply {
                            tvName.text = dataItem?.name
                            Glide.with(requireContext())
                                .load(BuildConfig.IMAGE_URL + dataItem?.foto)
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

                        val allData = result.data.laporan
                        val sortedData = allData?.sortedByDescending { it.id }
                        setupRecyclerView()

                        mAdapter.submitList(sortedData)

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
        mAdapter = AdapterHomeLaporan(
            onItemClick = { item ->
                val intent = Intent(requireActivity(), SaranaActivityAdmin::class.java)
                intent.putExtra(TAG_ID_PENGADUAN, item.id.toString())
                startActivity(intent)
            },
            onLongClick = { item ->
                idLaporan = item.id.toString()

                val customView =
                    LayoutInflater.from(requireContext()).inflate(R.layout.custom_delate, null)

                val yesString = getString(R.string.yes)
                val noString = getString(R.string.batal)

                val yesSpannable = SpannableString(yesString)
                val noSpannable = SpannableString(noString)

                val typeface = ResourcesCompat.getFont(requireContext(), R.font.poppinssembiold)
                typeface?.let {
                    yesSpannable.setSpan(
                        StyleSpan(Typeface.BOLD),
                        0,
                        yesSpannable.length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    yesSpannable.setSpan(
                        CustomTypefaceSpan(""),
                        0,
                        yesSpannable.length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )

                    noSpannable.setSpan(
                        StyleSpan(Typeface.BOLD),
                        0,
                        noSpannable.length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    noSpannable.setSpan(
                        CustomTypefaceSpan(""),
                        0,
                        noSpannable.length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }

                MaterialAlertDialogBuilder(requireContext())
                    .setView(customView)
                    .setPositiveButton(yesSpannable) { dialog, _ ->
                        hapusLaporan()
                    }
                    .setNegativeButton(noSpannable) { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()

            }
        )
    }


    private fun hapusLaporan() {
        viewModel.getUser().observe(viewLifecycleOwner) {
            viewModel.deleteLaporan(it.getToken, idLaporan).observe(viewLifecycleOwner) { items ->
                when (items) {
                    is Resource.Loading -> {
                        Log.d("DeleteLaporan", "Sedang menghapus laporan...")
                    }

                    is Resource.Success -> {
                        Toast.makeText(
                            requireContext(),
                            "Data Berhasil Dihapus",
                            Toast.LENGTH_SHORT
                        ).show()
                        getDataUser()
                    }

                    is Resource.Error -> {
                        Toast.makeText(
                            requireContext(),
                            "Gagal menghapus laporan",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }


    private fun setupRecyclerView() {
        binding.rvLaporanHome.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            setHasFixedSize(true)

            viewModel.getUser().observe(viewLifecycleOwner) { userLocal ->
                binding.tvName.text = userLocal.name
                binding.let {
                    Glide.with(requireContext())
                        .load(BuildConfig.IMAGE_URL + userLocal.foto)
                        .error(R.color.white)
                        .into(it.imgProfil)
                }

                viewModel.getListLaporan(userLocal.getToken).observe(viewLifecycleOwner) { result ->
                    when (result) {
                        is Resource.Loading -> {
                            binding.progressBar.isVisible = true
                        }

                        is Resource.Success -> {
                            binding.progressBar.isVisible = false

                            val sortedData = result.data.laporan?.sortedByDescending { it.id }
                            mAdapter.submitList(sortedData)

                            adapter = mAdapter
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
    }

}
