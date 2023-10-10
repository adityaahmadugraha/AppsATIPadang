package com.aditya.appsatipadang.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aditya.appsatipadang.data.remote.response.ItemLaporaneResponse
import com.aditya.appsatipadang.databinding.ListPelaporanBinding
import com.bumptech.glide.Glide

class AdapterLaporanTeknisi
    (
    private val onItemClick: (ItemLaporaneResponse) -> Unit
) : ListAdapter<ItemLaporaneResponse, AdapterLaporanTeknisi.ViewHolder>(DIFF_CALLBACK) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListPelaporanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun submitListReversed(list: List<ItemLaporaneResponse>?) {
        val reversedList = list?.reversed()
        submitList(reversedList)
    }


    inner class ViewHolder(private val binding: ListPelaporanBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ItemLaporaneResponse) {
            binding.apply {
                tvTitleLaporan.text = data.type
                tvTglLaporanSarana.text = data.tanggal
                tvNameAlat.text = data.merk
                tvStatusLaporan.text = data.status
                Glide.with(itemView.context)
                    .load(data.foto)
                    .into(binding.imgPelaporan)

                Log.d("cekfoto:::::::", data.foto.toString())

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
                    return oldItem.id_pelapor == newItem.id_pelapor
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