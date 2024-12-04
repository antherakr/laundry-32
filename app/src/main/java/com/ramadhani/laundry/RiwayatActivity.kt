package com.ramadhani.laundry

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class RiwayatActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var riwayatAdapter: RiwayatAdapter
    private val transaksiList: ArrayList<TransaksiData> = ArrayList()
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_riwayat)

        // Inisialisasi RecyclerView
        recyclerView = findViewById(R.id.rvRiwayat)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Inisialisasi Adapter
        riwayatAdapter = RiwayatAdapter(transaksiList)
        recyclerView.adapter = riwayatAdapter

        // Inisialisasi referensi database
        databaseReference = FirebaseDatabase.getInstance("https://laundry-3df75-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Layanan/Transaksi")

        databaseReference.orderByChild("Transaksi").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                transaksiList.clear() // Bersihkan data lama
                if (snapshot.exists()) {
                    for (data in snapshot.children) {
                        val transaksi = data.getValue(TransaksiData::class.java)
                        if (transaksi != null) {
                            transaksiList.add(transaksi)
                        }
                    }
                    riwayatAdapter.notifyDataSetChanged() // Perbarui adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("RiwayatActivity", "Database error: ${error.message}")
            }
        })


        // Memuat data dari database
        loadDataFromDatabase()
    }

    private fun loadDataFromDatabase() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                transaksiList.clear() // Bersihkan data lama
                if (snapshot.exists()) {
                    for (data in snapshot.children) {
                        val transaksi = data.getValue(TransaksiData::class.java)
                        if (transaksi != null) {
                            transaksiList.add(transaksi)
                        }
                    }
                    riwayatAdapter.notifyDataSetChanged() // Perbarui adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("RiwayatActivity", "Database error: ${error.message}")
            }
        })
    }
}
