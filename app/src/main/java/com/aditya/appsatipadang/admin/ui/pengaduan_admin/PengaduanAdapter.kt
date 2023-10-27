package com.aditya.appsatipadang.admin.ui.pengaduan_admin

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aditya.appsatipadang.BuildConfig
import com.aditya.appsatipadang.R
import com.aditya.appsatipadang.data.remote.response.ItemLaporaneResponse
import com.aditya.appsatipadang.databinding.ListHistoryLaporanBinding
import com.aditya.appsatipadang.databinding.ListPelaporanBinding
import com.aditya.appsatipadang.utils.Constant
import com.bumptech.glide.Glide

class PengaduanAdapter(
    private val onItemClick: (ItemLaporaneResponse) -> Unit
) : ListAdapter<ItemLaporaneResponse, PengaduanAdapter.ViewHolder>(DIFF_CALLBACK) {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListHistoryLaporanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ListHistoryLaporanBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ItemLaporaneResponse) {
            binding.apply {

                tvTitleLaporan.text = data.type
                tvTglLaporanSarana.text = Constant.convertDateFormat(data.tanggal.toString())
                tvLokasi.text = data.lokasi
                tvNameUser.text = data.namePelapor
                Glide.with(itemView.context)
                    .load(BuildConfig.IMAGE_URL + data.foto)
                    .into(binding.imgPelaporan)

                tvStatusLaporan.text = data.status

                when (data.status) {
                    "sudah diterima admin" -> tvStatusLaporan.setTextColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.blue
                        )
                    )

                    "sedang dikerjakan" -> tvStatusLaporan.setTextColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.orange
                        )
                    )

                    "selesai" -> tvStatusLaporan.setTextColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.selesai
                        )
                    )
                    else -> tvStatusLaporan.setTextColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.black
                        )
                    )
                }



                itemView.setOnClickListener {
                    onItemClick(data)
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<ItemLaporaneResponse> =
            object : DiffUtil.ItemCallback<ItemLaporaneResponse>() {
                override fun areItemsTheSame(
                    oldItem: ItemLaporaneResponse,
                    newItem: ItemLaporaneResponse
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(
                    oldItem: ItemLaporaneResponse,
                    newItem: ItemLaporaneResponse
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}