package com.anto.daftartamu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anto.daftartamu.daftar.Daftar
import kotlinx.android.synthetic.main.daftartamu_item.view.*

class DaftarAdapter : ListAdapter<Daftar, DaftarAdapter.DaftarHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Daftar>() {
            override fun areItemsTheSame(oldItem: Daftar, newItem: Daftar): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(oldItem: Daftar, newItem: Daftar): Boolean {
                return oldItem.nama == newItem.nama && oldItem.alamat == newItem.alamat && oldItem.notelpon == newItem.notelpon
                        && oldItem.priority == newItem.priority
            }
        }
    }
    private var listener: OnItemClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DaftarHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.daftartamu_item, parent, false)
        return DaftarHolder(itemView)
    }
    override fun onBindViewHolder(holder: DaftarHolder, position: Int) {
        val currentDaftar: Daftar = getItem(position)
        holder.textViewTitle.text = currentDaftar.nama
        holder.textViewPriority.text = currentDaftar.priority.toString()
        holder.textViewDescription.text = currentDaftar.alamat
        holder.textViewNotelpon.text = currentDaftar.notelpon
    }
    fun getDaftarAt(position: Int): Daftar {
        return getItem(position)
    }
    inner class DaftarHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(getItem(position))
                }
            }
        }
        var textViewTitle: TextView = itemView.text_view_nama
        var textViewPriority: TextView = itemView.text_view_priority
        var textViewDescription: TextView = itemView.text_view_alamat
        var textViewNotelpon: TextView = itemView.text_view_notelpon

    }
    interface OnItemClickListener {
        fun onItemClick(daftar: Daftar)
    }
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}