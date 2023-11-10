package com.mediatama.appsatipadang.teknik.ui_teknisi.detailpenyerahan

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.mediatama.appsatipadang.BuildConfig
import com.mediatama.appsatipadang.data.Resource
import com.mediatama.appsatipadang.databinding.ActivityDetailPenyerahanBinding
import com.mediatama.appsatipadang.teknik.ActivityTeknik
import com.mediatama.appsatipadang.utils.Constant.getToken
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityDetailPenyerahan : AppCompatActivity() {

    private lateinit var binding: ActivityDetailPenyerahanBinding
    private val viewModel: ViewModelDetailPenyerahan by viewModels()

    companion object {
        const val TAG_TANGGAL = "tanggal"
        const val TAG_NAMA_PENERIMA = "namaPenerima"
        const val TAG_NO = "noPengaduan"
        const val TAG_FOTO = "foto"
    }

    var id = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailPenyerahanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        val tanggal = intent.getStringExtra(TAG_TANGGAL)

        binding.imgBack.setOnClickListener {
            val intent = Intent(this@ActivityDetailPenyerahan, ActivityTeknik::class.java)
            startActivity(intent)
        }

        binding.btnKirim.setOnClickListener {
//            val intent = Intent(this@ActivityDetailPenyerahan, ActivityTeknik::class.java)
//            startActivity(intent)
            finish()
        }

//        binding.imgDelete.setOnClickListener {
//
//            val customView = LayoutInflater.from(this@ActivityDetailPenyerahan)
//                .inflate(R.layout.custom_delate, null)
//
//            val dialog = AlertDialog.Builder(this@ActivityDetailPenyerahan)
//                .setView(customView)
//                .create()
//
//            val yesString = getString(R.string.yes)
//            val noString = getString(R.string.batal)
//
//            val yesSpannable = SpannableString(yesString)
//            val noSpannable = SpannableString(noString)
//
//            val typeface =
//                ResourcesCompat.getFont(this@ActivityDetailPenyerahan, R.font.poppinssembiold)
//
//            typeface?.let {
//                yesSpannable.setSpan(
//                    StyleSpan(Typeface.BOLD),
//                    0,
//                    yesSpannable.length,
//                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//                )
//                yesSpannable.setSpan(
//                    CustomTypefaceSpan(typeface.toString()),
//                    0,
//                    yesSpannable.length,
//                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//                )
//
//                noSpannable.setSpan(
//                    StyleSpan(Typeface.BOLD),
//                    0,
//                    noSpannable.length,
//                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//                )
//                noSpannable.setSpan(
//                    CustomTypefaceSpan(typeface.toString()),
//                    0,
//                    noSpannable.length,
//                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//                )
//            }
//
//            dialog.setButton(AlertDialog.BUTTON_POSITIVE, yesSpannable) { _, _ ->
//                hapusLaporan()
//            }
//            dialog.setButton(AlertDialog.BUTTON_NEGATIVE, noSpannable) { _, _ -> }
//
//            dialog.window?.setBackgroundDrawableResource(R.drawable.rounded_dialog_bg)
//            dialog.window?.decorView?.setPadding(24, 0, 24, 0)
//
//            dialog.show()
//
//        }

        getDataPenyerahan(tanggal)
    }

    private fun hapusLaporan() {
        viewModel.getUser().observe(this) {
            viewModel.deletePenyerahan(it.getToken,id).observe(this) { items ->
                when (items) {
                    is Resource.Loading -> {
                        Log.d("DeleteLaporan", "Sedang menghapus laporan...")
                    }

                    is Resource.Success -> {
                        Toast.makeText(this, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this, ActivityTeknik::class.java)
                        startActivity(intent)
                        finish()

                    }

                    is Resource.Error -> {
                        Toast.makeText(this, "Gagal menghapus laporan", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }


    private fun getDataPenyerahan(tanggal: String?) {
        viewModel.getUser().observe(this) { user ->
            viewModel.getListPenyerahan(user.getToken).observe(this) { item ->
                when (item) {
                    is Resource.Loading -> {

                    }

                    is Resource.Success -> {

                        val penyerahanList = item.data.penyerahan

                        val penyerahan =
                            penyerahanList?.firstOrNull { it?.tglDiserahkan == tanggal }

                        penyerahan?.let {
                            binding.etTanggal.setText(it.tglDiserahkan.toString())
                            binding.etNamePenerima.setText(it.namaPenerima.toString())
                            binding.etNoPenyerahan.setText(it.noPengaduan.toString())
                            Glide.with(this@ActivityDetailPenyerahan)
                                .load(BuildConfig.IMAGE_URL + penyerahan?.foto)
                                .into(binding.imgBukti)

                        }
                    }

                    is Resource.Error -> {

                    }
                }
            }
        }
    }
}
