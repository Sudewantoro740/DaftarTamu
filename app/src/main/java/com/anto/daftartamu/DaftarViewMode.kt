package com.anto.daftartamu

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.anto.daftartamu.daftar.Daftar
import com.anto.daftartamu.daftar.DaftarRepository

class DaftarViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: DaftarRepository = DaftarRepository(application)
    private var alldaftar: LiveData<List<Daftar>> = repository.getAllData()
    fun insert(daftar: Daftar) {
        repository.insert(daftar)
    }
    fun update(daftar: Daftar) {
        repository.update(daftar)
    }
    fun delete(daftar: Daftar) {
        repository.delete(daftar)
    }
    fun deleteAllDaftar() {
        repository.deleteAll()
    }
    fun getAllDaftar(): LiveData<List<Daftar>> {
        return alldaftar
    }
}