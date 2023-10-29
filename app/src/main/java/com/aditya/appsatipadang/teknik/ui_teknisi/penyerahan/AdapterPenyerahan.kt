package com.aditya.appsatipadang.teknik.ui_teknisi.penyerahan

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aditya.appsatipadang.BuildConfig
import com.aditya.appsatipadang.data.remote.response.PenyerahanResponse
import com.aditya.appsatipadang.databinding.ListPenyerahanBinding
import com.bumptech.glide.Glide


class AdapterPenyerahan(

) : ListAdapter<PenyerahanResponse.Penyerahan, AdapterPenyerahan.ViewHolder>(DIFF_CALLBACK) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListPenyerahanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }


    inner class ViewHolder(private val binding: ListPenyerahanBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("ResourceAsColor")
        fun bind(data: PenyerahanResponse.Penyerahan) {
            binding.apply {
                tvName.text = data.namaPenerima
                tvTglLaporanSarana.text = data.tglDserahkan
                tvNoLaporan.text = data.noPengaduan
                Glide.with(itemView.context)
                    .load(BuildConfig.IMAGE_URL + data.foto)
                    .into(binding.imgPelaporan)


            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<PenyerahanResponse.Penyerahan> =
            object : DiffUtil.ItemCallback<PenyerahanResponse.Penyerahan>() {
                override fun areItemsTheSame(
                    oldItem: PenyerahanResponse.Penyerahan,
                    newItem: PenyerahanResponse.Penyerahan
                ): Boolean {
                    return oldItem.noPengaduan == newItem.noPengaduan
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(
                    oldItem: PenyerahanResponse.Penyerahan,
                    newItem: PenyerahanResponse.Penyerahan
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}
