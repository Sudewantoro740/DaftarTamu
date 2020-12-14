package com.anto.daftartamu.daftar

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DaftarDAO {
    @Insert
    fun insert(daftar: Daftar)
    @Update
    fun update(daftar: Daftar)
    @Delete
    fun delete(daftar: Daftar)
    @Query("DELETE FROM tabeldaftar")
    fun deleteAllData()
    @Query("SELECT * FROM tabeldaftar ORDER BY priority DESC")
    fun getAllData(): LiveData<List<Daftar>>
}