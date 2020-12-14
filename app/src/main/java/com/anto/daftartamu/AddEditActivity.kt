package com.anto.daftartamu

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_daftartamu.*

class AddEditActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_ID = "com.anto.praktikummobile10.EXTRA_ID"
        const val EXTRA_NAMA = "com.anto.praktikummobile10.EXTRA_NAMA"
        const val EXTRA_ALAMAT = "com.anto.praktikummobile10.EXTRA_ALAMAT"
        const val EXTRA_NOTELPON = "com.anto.praktikummobile10.EXTRA_NOTELPON"
        const val EXTRA_PRIORITAS = "com.anto.praktikummobile10.EXTRA_PRIORITAS"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_daftartamu)

        number_picker_priority.minValue = 1
        number_picker_priority.maxValue = 5

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_close_24)

        if (intent.hasExtra(EXTRA_ID)) {
            title = "Edit Daftar Tamu"
            edit_text_nama.setText(intent.getStringExtra(EXTRA_NAMA))
            edit_text_alamat.setText(intent.getStringExtra(EXTRA_ALAMAT))
            edit_text_notelpon.setText(intent.getStringExtra(EXTRA_NOTELPON))
            number_picker_priority.value = intent.getIntExtra(EXTRA_PRIORITAS, 1)
        }
        else {
            title = "Tambah Daftar Tamu"
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_daftartamu_menu, menu)
        return true     }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item?.itemId) {
            R.id.save_daftar -> {
                saveDaftar()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveDaftar() {
        if (edit_text_nama.text.toString().trim().isBlank() || edit_text_alamat.text.toString().trim().isBlank()) {
            Toast.makeText(this, "Daftar Tamu kosong!", Toast.LENGTH_SHORT).show()
            return
        }

        val data = Intent().apply {
            putExtra(EXTRA_NAMA, edit_text_nama.text.toString())
            putExtra(EXTRA_ALAMAT, edit_text_alamat.text.toString())
            putExtra(EXTRA_NOTELPON, edit_text_notelpon.text.toString())
            putExtra(EXTRA_PRIORITAS, number_picker_priority.value)
            if (intent.getIntExtra(EXTRA_ID, -1) != -1) {
                putExtra(EXTRA_ID, intent.getIntExtra(EXTRA_ID, -1))
            }
        }

        setResult(Activity.RESULT_OK, data)
        finish()
    }
}