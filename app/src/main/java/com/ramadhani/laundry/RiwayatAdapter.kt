package com.ramadhani.laundry

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RiwayatAdapter(private val transaksiList: List<TransaksiData>) : RecyclerView.Adapter<RiwayatAdapter.RiwayatViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RiwayatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_riwayat, parent, false)
        return RiwayatViewHolder(view)
    }

    override fun onBindViewHolder(holder: RiwayatViewHolder, position: Int) {
        val transaksi = transaksiList[position]
        holder.tvNamaPelanggan.text = transaksi.namaPelanggan
        holder.tvLayanan.text = transaksi.layanan
        holder.tvSatuan.text = "Satuan: ${transaksi.satuan}"
        holder.tvTotalHarga.text = "Total Harga: Rp ${transaksi.totalHarga}"
        holder.tvTanggal.text = "Tanggal: ${transaksi.tanggal}"
    }

    override fun getItemCount(): Int = transaksiList.size

    inner class RiwayatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNamaPelanggan: TextView = itemView.findViewById(R.id.tvNamaPelanggan)
        val tvLayanan: TextView = itemView.findViewById(R.id.tvLayanan)
        val tvSatuan: TextView = itemView.findViewById(R.id.tvSatuan)
        val tvTotalHarga: TextView = itemView.findViewById(R.id.tvHargaTotal)
        val tvTanggal: TextView = itemView.findViewById(R.id.tvTanggalTransaksi)
    }
}

