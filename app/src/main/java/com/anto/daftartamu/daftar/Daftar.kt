package com.anto.daftartamu.daftar

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tabeldaftar")
data class Daftar(
    var nama: String,
    var alamat: String,
    var notelpon: String,
    var priority: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}