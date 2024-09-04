package org.d3ifcool.kulinerkuy.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.marginLeft
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.d3ifcool.kulinerkuy.R
import org.d3ifcool.kulinerkuy.databinding.ItemMainBinding
import org.d3ifcool.kulinerkuy.model.KulinerKuy

class DashboardAdapter( private val handler: ClickHandler
)  : ListAdapter<KulinerKuy, DashboardAdapter.ViewHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<KulinerKuy>() {
            override fun areItemsTheSame(
                oldData: KulinerKuy, newData: KulinerKuy
            ): Boolean {
                return oldData.id == newData.id
            }
            override fun areContentsTheSame(
                oldData: KulinerKuy, newData: KulinerKuy
            ): Boolean {
                return oldData == newData
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMainBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemMainBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(kulinerkuy: KulinerKuy) {
            binding.viewNamaRes.text = kulinerkuy.nama
            binding.viewTanggalRes.text = kulinerkuy.tanggal
            val pos = adapterPosition
            itemView.isSelected = selectionIds.contains(kulinerkuy.id)
            itemView.setOnClickListener { handler.onClick(pos, kulinerkuy) }
            itemView.setOnLongClickListener { handler.onLongClick(pos) }
            if (kulinerkuy.isChecked) {
                binding.imageView.visibility = android.view.View.VISIBLE
            }
            else {
                binding.imageView.visibility = android.view.View.GONE
            }
            if (kulinerkuy.tag == 0) {
                binding.shape.visibility = android.view.View.GONE
                //set margin left
                val params = binding.imageView.layoutParams as ViewGroup.MarginLayoutParams
                params.setMargins(100, 10, 0, 0)
            } else if (kulinerkuy.tag == 1) {
                binding.shape.setImageResource(R.drawable.label_item1)
            } else if (kulinerkuy.tag == 2) {
                binding.shape.setImageResource(R.drawable.label_item2)
            } else if (kulinerkuy.tag == 3) {
                binding.shape.setImageResource(R.drawable.label_item3)
            }
        }

    }

    interface ClickHandler {
        fun onClick(position: Int, kulinerkuy: KulinerKuy)
        fun onLongClick(position: Int): Boolean

    }

    private val selectionIds = ArrayList<Int>()
    fun toggleSelection(pos: Int) {
        val id = getItem(pos).id
        if (selectionIds.contains(id))
            selectionIds.remove(id)
        else
            selectionIds.add(id)
        notifyDataSetChanged()
    }
    fun getSelection(): List<Int> {
        return selectionIds
    }
    fun resetSelection() {
        selectionIds.clear()
        notifyDataSetChanged()
    }
}
