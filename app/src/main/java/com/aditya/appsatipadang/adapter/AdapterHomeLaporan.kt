package com.aditya.appsatipadang.adapter

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
import com.aditya.appsatipadang.admin.ui.sarana_admin.SaranaAdminViewModel
import com.aditya.appsatipadang.data.remote.response.ItemLaporaneResponse
import com.aditya.appsatipadang.databinding.ListHistoryLaporanBinding
import com.bumptech.glide.Glide

class AdapterHomeLaporan(
    private val onItemClick: (ItemLaporaneResponse) -> Unit,
    private val onLongClick: (ItemLaporaneResponse) -> Unit
) : ListAdapter<ItemLaporaneResponse, AdapterHomeLaporan.ViewHolder>(DIFF_CALLBACK) {

    private val maxItemCount = 5

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListHistoryLaporanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }


    fun submitListReversed(list: List<ItemLaporaneResponse>?) {
        val reversedList = list?.toMutableList()
        reversedList?.add(0, getItem(0))
        val limitedList = reversedList?.take(maxItemCount)
        submitList(limitedList)
    }

    inner class ViewHolder(private val binding: ListHistoryLaporanBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("ResourceAsColor")
        fun bind(data: ItemLaporaneResponse) {
            binding.apply {
                tvTitleLaporan.text = data.type
                tvTglLaporanSarana.text = data.tanggal
                tvLokasi.text = data.lokasi
//                tvMerk.text = data.merk
                tvStatusLaporan.text = data.status
                tvNameUser.text = data.namePelapor


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

                Glide.with(itemView.context)
                    .load(BuildConfig.IMAGE_URL + data.foto)
                    .into(binding.imgPelaporan)

                itemView.setOnClickListener {
                    onItemClick(data)
                }
             itemView.setOnLongClickListener {
                    onLongClick(data)
                    return@setOnLongClickListener true
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
