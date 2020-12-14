package com.anto.daftartamu

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anto.daftartamu.daftar.Daftar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        const val ADD_DAFTAR_REQUEST = 1
        const val EDIT_DAFTAR_REQUEST = 2
    }

    private lateinit var daftarViewModel: DaftarViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonAddDaftar.setOnClickListener {
            startActivityForResult(
                Intent(this, AddEditActivity::class.java),
                ADD_DAFTAR_REQUEST
            )
        }
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)
        val adapter = DaftarAdapter()
        recycler_view.adapter = adapter

        daftarViewModel = ViewModelProviders.of(this).get(DaftarViewModel::class.java)
        daftarViewModel.getAllDaftar().observe(this, Observer<List<Daftar>> {
            adapter.submitList(it)
        })

        ItemTouchHelper(

            object :
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT).or(
                    ItemTouchHelper.DOWN)) {

                override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                    return false

                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                    //  cardview.setCardBackgroundColor(Color.parseColor("Red"))
                    AlertDialog.Builder(viewHolder.itemView.getContext())
                        // Judul
                        .setTitle("Warning")
                        // Pesan yang di tamopilkan
                        .setMessage("Ingin Dihapus ?")
                        .setPositiveButton("Ya", DialogInterface.OnClickListener(){ dialogInterface, i ->
                            daftarViewModel.delete(adapter.getDaftarAt(viewHolder.adapterPosition))
                            Toast.makeText(baseContext, "Catatan dihapus!", Toast.LENGTH_SHORT).show()
                        })
                        .setNegativeButton("Tidak", DialogInterface.OnClickListener { dialogInterface, i ->
                            Toast.makeText(baseContext, "Catatan Tidak Terhapus", Toast.LENGTH_LONG).show()
                            adapter.notifyItemChanged(viewHolder.adapterPosition)
                        })
                        .show()

                }
            }
        ).attachToRecyclerView(recycler_view)
        adapter.setOnItemClickListener(object : DaftarAdapter.OnItemClickListener {
            override fun onItemClick(daftar: Daftar) {
                val intent = Intent(baseContext, AddEditActivity::class.java)
                intent.putExtra(AddEditActivity.EXTRA_ID, daftar.id)
                intent.putExtra(AddEditActivity.EXTRA_NAMA, daftar.nama)
                intent.putExtra(AddEditActivity.EXTRA_ALAMAT, daftar.alamat)
                intent.putExtra(AddEditActivity.EXTRA_NOTELPON, daftar.notelpon)
                intent.putExtra(AddEditActivity.EXTRA_PRIORITAS, daftar.priority)

                startActivityForResult(intent, EDIT_DAFTAR_REQUEST)             }
        }
        )
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true     }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item?.itemId) {
            R.id.delete_all_daftar -> {
                daftarViewModel.deleteAllDaftar()
                Toast.makeText(this, "Semua sudah dihapus!", Toast.LENGTH_SHORT).show()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_DAFTAR_REQUEST && resultCode == Activity.RESULT_OK) {
            val newDaftar = Daftar(
                data!!.getStringExtra(AddEditActivity.EXTRA_NAMA).toString(),
                data.getStringExtra(AddEditActivity.EXTRA_ALAMAT).toString(),
                data.getStringExtra(AddEditActivity.EXTRA_NOTELPON).toString(),
                data.getIntExtra(AddEditActivity.EXTRA_PRIORITAS, 1)
            )
            daftarViewModel.insert(newDaftar)
            Toast.makeText(this, "Catatan disimpan!", Toast.LENGTH_SHORT).show()
        } else if (requestCode == EDIT_DAFTAR_REQUEST && resultCode == Activity.RESULT_OK) {
            val id = data?.getIntExtra(AddEditActivity.EXTRA_ID, -1)

            if (id == -1) {
                Toast.makeText(this, "Pembaharuan gagal!", Toast.LENGTH_SHORT).show()
            }

            val updateDaftar = Daftar(
                data!!.getStringExtra(AddEditActivity.EXTRA_NAMA).toString(),
                data.getStringExtra(AddEditActivity.EXTRA_ALAMAT).toString(),
                data.getStringExtra(AddEditActivity.EXTRA_NOTELPON).toString(),
                data.getIntExtra(AddEditActivity.EXTRA_PRIORITAS, 1)
            )
            updateDaftar.id = data.getIntExtra(AddEditActivity.EXTRA_ID, -1)
            daftarViewModel.update(updateDaftar)
        } else {
            Toast.makeText(this, "Catatan tidak disimpan!", Toast.LENGTH_SHORT).show()
        }
    }
}