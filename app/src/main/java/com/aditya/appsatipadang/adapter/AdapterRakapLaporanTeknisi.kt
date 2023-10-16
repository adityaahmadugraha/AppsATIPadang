package com.aditya.appsatipadang.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aditya.appsatipadang.data.remote.response.ItemLaporaneResponse
import com.aditya.appsatipadang.databinding.ListLaporanBinding

class AdapterRakapLaporanTeknisi

    : ListAdapter<ItemLaporaneResponse, AdapterRakapLaporanTeknisi.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListLaporanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ListLaporanBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ItemLaporaneResponse) {
            binding.apply {
                tvTipe.text = data.type
                tvTanggal.text = data.tanggal
                tvNoLaporan.text = data.id.toString()
                tvLokasi.text = data.lokasi
                tvBiaya.text = data.biaya
                tvKegiatanPerbaikan.text = data.kegiatan_perbaikan
                tvPihakYangTerlibat.text = data.pihak_terlibat

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