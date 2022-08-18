package com.example.armysecurity.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.armysecurity.data.Cemetery
import com.example.armysecurity.data.CemeteryDaejeon
import com.example.armysecurity.databinding.RowCemeteryBinding
import com.example.armysecurity.databinding.RowCemeteryDaejeonBinding


class CemeteryDaejeonAdapter(private val onItemClickListener: ((CemeteryDaejeon) -> Unit)) : PagingDataAdapter<CemeteryDaejeon, CemeteryDaejeonViewHolder>(DIFF_CALLBACK) {
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CemeteryDaejeon>() {
            override fun areItemsTheSame(oldItem: CemeteryDaejeon, newItem: CemeteryDaejeon): Boolean =
                oldItem.name == newItem.name
                        && oldItem.sn == newItem.sn
                        && oldItem.birthday == newItem.birthday

            override fun areContentsTheSame(oldItem: CemeteryDaejeon, newItem: CemeteryDaejeon): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CemeteryDaejeonViewHolder {

        return CemeteryDaejeonViewHolder(
            RowCemeteryDaejeonBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),onItemClickListener
        )
    }

    override fun onBindViewHolder(holder: CemeteryDaejeonViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class CemeteryDaejeonViewHolder(private val binding: RowCemeteryDaejeonBinding, private val onItemClickListener: ((CemeteryDaejeon) -> Unit)): RecyclerView.ViewHolder(binding.root) {
    var data: Cemetery? = null

    fun bind(data: CemeteryDaejeon?){
        binding.name.text = data?.name
        binding.sn.text = data?.sn
        binding.birthday.text = data?.birthday

        binding.root.setOnClickListener{
            data?.let{onItemClickListener(it)}
        }
    }
}