package com.example.armysecurity.ui.event.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.armysecurity.data.Relics
import com.example.armysecurity.data.War
import com.example.armysecurity.databinding.RowWarBinding

class WarAdapter(private val onItemClickListener: ((War) -> Unit)) : PagingDataAdapter<War, WarViewHolder>(DIFF_CALLBACK) {
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<War>() {
            override fun areItemsTheSame(oldItem: War, newItem: War): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: War, newItem: War): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WarViewHolder {

        return WarViewHolder(
            RowWarBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),onItemClickListener
        )
    }

    override fun onBindViewHolder(holder: WarViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class WarViewHolder(private val binding: RowWarBinding, private val onItemClickListener: ((War) -> Unit)): RecyclerView.ViewHolder(binding.root) {
    var data: War? = null

    fun bind(data: War?){
        binding.name.text = data?.title
        binding.sub.text = data?.place

        binding.root.setOnClickListener{
            data?.let{onItemClickListener(it)}
        }
    }
}