package com.example.armysecurity.ui.event.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.armysecurity.data.Relics
import com.example.armysecurity.data.WarMan
import com.example.armysecurity.databinding.RowWarBinding

class WarManAdapter(private val onItemClickListener: ((WarMan) -> Unit)) : PagingDataAdapter<WarMan, WarManViewHolder>(DIFF_CALLBACK) {
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<WarMan>() {
            override fun areItemsTheSame(oldItem: WarMan, newItem: WarMan): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: WarMan, newItem: WarMan): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WarManViewHolder {

        return WarManViewHolder(
            RowWarBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),onItemClickListener
        )
    }

    override fun onBindViewHolder(holder: WarManViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class WarManViewHolder(private val binding: RowWarBinding, private val onItemClickListener: ((WarMan) -> Unit)): RecyclerView.ViewHolder(binding.root) {
    var data: WarMan? = null

    fun bind(data: WarMan?){
        binding.name.text = data?.title
        binding.sub.text = data?.award

        binding.root.setOnClickListener{
            data?.let{onItemClickListener(it)}
        }
    }
}