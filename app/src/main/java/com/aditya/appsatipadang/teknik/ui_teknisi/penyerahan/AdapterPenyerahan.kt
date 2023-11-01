package com.aditya.appsatipadang.teknik.ui_teknisi.penyerahan

import android.annotation.SuppressLint
import android.content.ClipData
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aditya.appsatipadang.BuildConfig
import com.aditya.appsatipadang.data.remote.request.PenyerahanItem
import com.aditya.appsatipadang.databinding.ListPenyerahanBinding
import com.bumptech.glide.Glide


class AdapterPenyerahan(
    private val onItemClick: (PenyerahanItem) -> Unit,
//    private val itemClick : (PenyerahanItem) -> ClipData.Item
) : ListAdapter<PenyerahanItem, AdapterPenyerahan.ViewHolder>(DIFF_CALLBACK) {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListPenyerahanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }




    inner class ViewHolder(private val binding: ListPenyerahanBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("ResourceAsColor")
        fun bind(data: PenyerahanItem) {
            binding.apply {
                tvName.text = data.namaPenerima
                tvTglLaporanSarana.text = data.tglDiserahkan
                tvNoLaporan.text = data.noPengaduan
                Glide.with(itemView.context)
                    .load(BuildConfig.IMAGE_URL + data.foto)
                    .into(imgPelaporan)
                itemView.setOnClickListener {
                    onItemClick(data)
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<PenyerahanItem> =
            object : DiffUtil.ItemCallback<PenyerahanItem>() {
                override fun areItemsTheSame(
                    oldItem: PenyerahanItem,
                    newItem: PenyerahanItem
                ): Boolean {
                    return oldItem.noPengaduan == newItem.noPengaduan
                }

                override fun areContentsTheSame(
                    oldItem: PenyerahanItem,
                    newItem: PenyerahanItem
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}
