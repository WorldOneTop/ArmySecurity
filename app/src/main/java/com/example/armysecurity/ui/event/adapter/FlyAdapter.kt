package com.example.armysecurity.ui.event.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.armysecurity.data.Cemetery
import com.example.armysecurity.databinding.RowCemeteryBinding

class FlyAdapter (private val onItemClickListener: ((Cemetery) -> Unit)) : PagingDataAdapter<Cemetery, FlyViewHolder>(DIFF_CALLBACK) {
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Cemetery>() {
            override fun areItemsTheSame(oldItem: Cemetery, newItem: Cemetery): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Cemetery, newItem: Cemetery): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlyViewHolder {

        return FlyViewHolder(
            RowCemeteryBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),onItemClickListener
        )
    }

    override fun onBindViewHolder(holder: FlyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class FlyViewHolder(private val binding: RowCemeteryBinding, private val onItemClickListener: ((Cemetery) -> Unit)): RecyclerView.ViewHolder(binding.root) {
    var data: Cemetery? = null

    fun bind(data: Cemetery?){
        binding.name.text = data?.name
        binding.rank.text = data?.rank
        binding.identity.text = data?.identity

        binding.root.setOnClickListener{
            data?.let{onItemClickListener(it)}
        }
    }
}