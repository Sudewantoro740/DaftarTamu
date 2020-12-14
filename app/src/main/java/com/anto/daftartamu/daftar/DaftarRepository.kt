package com.anto.daftartamu.daftar

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData

class DaftarRepository(application: Application) {
    private var daftarDAO: DaftarDAO
    private var alldaftar: LiveData<List<Daftar>>
    init {
        val database: DaftarDatabase = DaftarDatabase.getInstance(
            application.applicationContext
        )!!
        daftarDAO = database.daftarDAO()
        alldaftar = daftarDAO.getAllData()
    }
    fun insert(daftar: Daftar) {
        val insertDaftarAsyncTask = InsertDaftarAsyncTask(daftarDAO).execute(daftar)
    }
    fun update(daftar: Daftar) {
        val updateDaftarAsyncTask = UpdateDaftarAsyncTaskAsyncTask(daftarDAO).execute(daftar)
    }
    fun delete(daftar: Daftar) {
        val deleteDaftarAsyncTask = DeleteDaftarAsyncTask(daftarDAO).execute(daftar)
    }
    fun deleteAll() {
        val deleteAllAsyncTask = DeleteAllAsyncTask(
            daftarDAO
        ).execute()
    }
    fun getAllData(): LiveData<List<Daftar>> {
        return alldaftar
    }
    companion object {
        private class InsertDaftarAsyncTask(daftarDAO: DaftarDAO) : AsyncTask<Daftar, Unit, Unit>() {
            val daftarDAO = daftarDAO
            override fun doInBackground(vararg p0: Daftar?) {
                daftarDAO.insert(p0[0]!!)
            }
        }
        private class UpdateDaftarAsyncTaskAsyncTask(daftarDAO: DaftarDAO) : AsyncTask<Daftar, Unit, Unit>() {
            val daftarDAO = daftarDAO
            override fun doInBackground(vararg p0: Daftar?) {
                daftarDAO.update(p0[0]!!)
            }
        }
        private class DeleteDaftarAsyncTask(daftarDAO: DaftarDAO) : AsyncTask<Daftar, Unit, Unit>() {
            val daftarDAO = daftarDAO
            override fun doInBackground(vararg p0: Daftar?) {
                daftarDAO.delete(p0[0]!!)
            }
        }
        private class DeleteAllAsyncTask(daftarDAO: DaftarDAO) : AsyncTask<Unit, Unit, Unit>() {
            val daftarDAO = daftarDAO
            override fun doInBackground(vararg p0: Unit?) {
                daftarDAO.deleteAllData()
            }
        }
    }
}