package com.anto.daftartamu.daftar

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Daftar::class], version = 1)
abstract class DaftarDatabase : RoomDatabase() {

    abstract fun daftarDAO(): DaftarDAO
    companion object {
        private var instance: DaftarDatabase? = null
        fun getInstance(context: Context): DaftarDatabase? {
            if (instance == null) {
                synchronized(DaftarDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DaftarDatabase::class.java, "daftar_database"
                    )
                        .fallbackToDestructiveMigration()
                        .addCallback(roomCallback)
                        .build()
                }
            }
            return instance
        }
        fun destroyInstance() {
            instance = null
        }
        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDbAsyncTask(instance)
                    .execute()
            }
        }
    }
    class PopulateDbAsyncTask(db: DaftarDatabase?) : AsyncTask<Unit, Unit, Unit>() {
        private val daftarDAO = db?.daftarDAO()
        override fun doInBackground(vararg p0: Unit?) {
            daftarDAO?.insert(Daftar("Nana", "Jalan Mawar", "0812123131", 1))
        }
    }
}