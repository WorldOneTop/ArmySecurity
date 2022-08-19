package com.example.armysecurity.ui.event.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.armysecurity.data.Relics
import com.example.armysecurity.databinding.RowWarBinding


class RelicsAdapter(private val onItemClickListener: ((Relics) -> Unit)) : PagingDataAdapter<Relics, RelicsViewHolder>(DIFF_CALLBACK) {
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Relics>() {
            override fun areItemsTheSame(oldItem: Relics, newItem: Relics): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Relics, newItem: Relics): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RelicsViewHolder {

        return RelicsViewHolder(
            RowWarBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),onItemClickListener
        )
    }

    override fun onBindViewHolder(holder: RelicsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class RelicsViewHolder(private val binding: RowWarBinding, private val onItemClickListener: ((Relics) -> Unit)): RecyclerView.ViewHolder(binding.root) {
    var data: Relics? = null

    fun bind(data: Relics?){
        binding.name.text = data?.ttl
        binding.sub.text = data?.obtmplace

        binding.root.setOnClickListener{
            data?.let{onItemClickListener(it)}
        }
    }
}