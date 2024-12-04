package com.ramadhani.laundry

import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*

class LaporanActivity : AppCompatActivity() {

    private lateinit var datePicker: DatePicker
    private lateinit var tvLaporan: TextView
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_laporan)

        // Inisialisasi View
        datePicker = findViewById(R.id.datePicker)
        tvLaporan = findViewById(R.id.tvLaporan)

        // Inisialisasi referensi database
        databaseReference = FirebaseDatabase.getInstance("https://laundry-3df75-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("Layanan/Transaksi")

        // Set listener untuk DatePicker
        datePicker.init(
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        ) { _, year, month, dayOfMonth ->
            // Konversi tanggal ke format yang sesuai dengan data di Firebase
            val date = "$dayOfMonth-${month + 1}-$year" // Format: dd-MM-yyyy
            fetchLaporan(date)
        }
    }

    private fun fetchLaporan(date: String) {
        // Ambil data transaksi berdasarkan tanggal
        databaseReference.orderByChild("tanggal").equalTo(date).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Bersihkan laporan sebelumnya
                val laporan = StringBuilder()

                if (snapshot.exists()) {
                    for (data in snapshot.children) {
                        val transaksi = data.getValue(TransaksiData::class.java)
                        if (transaksi != null) {
                            laporan.append("Nama: ${transaksi.namaPelanggan}\n")
                            laporan.append("Layanan: ${transaksi.layanan}\n")
                            laporan.append("Satuan: ${transaksi.satuan}\n")
                            laporan.append("Total Harga: ${transaksi.totalHarga}\n")
                            laporan.append("\n")
                        }
                    }
                    // Menampilkan laporan
                    tvLaporan.text = if (laporan.isNotEmpty()) laporan.toString() else "Tidak ada transaksi untuk tanggal ini."
                } else {
                    tvLaporan.text = "Tidak ada transaksi untuk tanggal ini."
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("LaporanActivity", "Database error: ${error.message}")
            }
        })
    }
}
