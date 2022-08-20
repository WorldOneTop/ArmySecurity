package com.example.armysecurity.ui.event.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.armysecurity.data.Cemetery
import com.example.armysecurity.data.Fly
import com.example.armysecurity.data.Trip
import com.example.armysecurity.databinding.RowCemeteryBinding
import com.example.armysecurity.databinding.RowWarBinding

class FlyAdapter (private val onItemClickListener: ((Fly) -> Unit)) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val data: ArrayList<Fly> = arrayListOf()

    fun setData(data: List<Fly>) {
        val prev = this.data.size
        this.data.clear()
        notifyItemRangeRemoved(0, prev)

        this.data.addAll(data)
        notifyItemRangeInserted(0, data.size + 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlyViewHolder {
        return FlyViewHolder(
            RowCemeteryBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),onItemClickListener
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as FlyViewHolder).bind(data[position])
    }

    override fun getItemCount(): Int  = data.size


}

class FlyViewHolder(private val binding: RowCemeteryBinding, private val onItemClickListener: ((Fly) -> Unit)): RecyclerView.ViewHolder(binding.root) {
    var data: Fly? = null

    fun bind(data: Fly?){
        binding.name.text = data?.enatvnm
        binding.rank.text = data?.plc
        binding.identity.text = data?.dates

        binding.root.setOnClickListener{
            data?.let{onItemClickListener(it)}
        }
    }
}