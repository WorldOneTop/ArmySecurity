package com.example.armysecurity.ui.event.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.armysecurity.data.Sale
import com.example.armysecurity.databinding.RowWarBinding

class SaleAdapter(private val onItemClickListener: ((Sale) -> Unit)) : PagingDataAdapter<Sale, SaleViewHolder>(DIFF_CALLBACK) {
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Sale>() {
            override fun areItemsTheSame(oldItem: Sale, newItem: Sale): Boolean =
                oldItem.instltnnm == newItem.instltnnm

            override fun areContentsTheSame(oldItem: Sale, newItem: Sale): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SaleViewHolder {

        return SaleViewHolder(
            RowWarBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),onItemClickListener
        )
    }

    override fun onBindViewHolder(holder: SaleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class SaleViewHolder(private val binding: RowWarBinding, private val onItemClickListener: ((Sale) -> Unit)): RecyclerView.ViewHolder(binding.root) {
    var data: Sale? = null

    fun bind(data: Sale?){
        binding.name.text = data?.instltnnm
        binding.sub.text = data?.dcntenatvnm

        binding.root.setOnClickListener{
            data?.let{onItemClickListener(it)}
        }
    }
}