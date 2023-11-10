package com.mediatama.appsatipadang.admin.ui.pengaduan_admin.fragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mediatama.appsatipadang.data.remote.response.LaporanItem
import com.mediatama.appsatipadang.databinding.ListDihapusBinding

class AdapterHistoryDihapus :
    ListAdapter<LaporanItem, AdapterHistoryDihapus.ViewHolder>(DIFF_CALLBACK) {
    inner class ViewHolder(
        private val binding: ListDihapusBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: LaporanItem) {
            binding.tvDeskripsiKesalahan.text = data.alasan.toString()
            binding.tvTanggal.text = data.tanggal.toString()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListDihapusBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("DiffUtilEquals")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<LaporanItem> =
            object : DiffUtil.ItemCallback<LaporanItem>() {
                override fun areItemsTheSame(oldItem: LaporanItem, newItem: LaporanItem): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: LaporanItem,
                    newItem: LaporanItem
                ): Boolean {
                    return oldItem == newItem
                }

            }
    }
}