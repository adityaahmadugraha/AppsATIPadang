package com.aditya.appsatipadang.admin.ui.pengaduan_admin

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aditya.appsatipadang.BuildConfig
import com.aditya.appsatipadang.data.remote.response.ItemLaporaneResponse
import com.aditya.appsatipadang.databinding.ListPelaporanBinding
import com.aditya.appsatipadang.utils.Constant
import com.bumptech.glide.Glide

class PengaduanAdapter(
    private val onItemClick: (ItemLaporaneResponse) -> Unit
) : ListAdapter<ItemLaporaneResponse, PengaduanAdapter.ViewHolder>(DIFF_CALLBACK) {

    private val maxItemCount = 5

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListPelaporanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ListPelaporanBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ItemLaporaneResponse) {
            binding.apply {
                tvTitleLaporan.text = data.type
                tvTglLaporanSarana.text = Constant.convertDateFormat(data.tanggal.toString())
                tvNameAlat.text = data.jenis
                tvMerkAlat.text = data.merk
                tvStatusLaporan.text = data.status
                Glide.with(itemView.context)
                    .load(BuildConfig.IMAGE_URL + data.foto)
                    .into(binding.imgPelaporan)

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