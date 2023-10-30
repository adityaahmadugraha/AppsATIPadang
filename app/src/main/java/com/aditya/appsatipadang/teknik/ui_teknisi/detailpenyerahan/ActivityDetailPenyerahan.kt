package com.aditya.appsatipadang.teknik.ui_teknisi.detailpenyerahan

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.aditya.appsatipadang.data.Resource
import com.aditya.appsatipadang.databinding.ActivityDetailPenyerahanBinding
import com.aditya.appsatipadang.teknik.ActivityTeknik
import com.aditya.appsatipadang.utils.Constant.getToken
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ActivityDetailPenyerahan : AppCompatActivity() {

    private lateinit var binding: ActivityDetailPenyerahanBinding

    private val viewModel: ViewModelDetailPenyerahan by viewModels()

//
//    companion object {
//        const val TAG_NAME = "name"
//        const val TAG_NO = "no"
//        const val TAG_TANGGAL = "tanggal"
//        const val TAG_ID = "id_penyerahan"
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailPenyerahanBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.imgBack.setOnClickListener {
            intent = Intent(this@ActivityDetailPenyerahan, ActivityTeknik::class.java)
            startActivity(intent)
        }

//        getDataLaporan()
    }

    private fun getDataLaporan() {

        viewModel.getUser().observe(this@ActivityDetailPenyerahan) {
            viewModel.getListPenyerahan(it.getToken)
                .observe(this@ActivityDetailPenyerahan) { item ->
                    when (item) {
                        is Resource.Loading -> {

                        }

                        is Resource.Success -> {
                            val penyerahan = item.data.penyerahan
                            binding.apply {
                                etNamePenerima.setText(penyerahan.toString())
//                                etTanggal.setText.(penyerahan?.tglDiserahkan.toString())
                                etNoPenyerahan.setText(penyerahan.toString())


                            }
                        }


                        is Resource.Error -> {


                        }
                    }
                }
        }
    }


}