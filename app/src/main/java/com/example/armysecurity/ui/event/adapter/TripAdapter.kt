package com.example.armysecurity.ui.event.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.armysecurity.data.Trip
import com.example.armysecurity.databinding.RowWarBinding

class TripAdapter(private val onItemClickListener: ((Trip) -> Unit)) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val data: ArrayList<Trip> = arrayListOf()

    fun setData(data: List<Trip>) {
        val prev = this.data.size
        this.data.clear()
        notifyItemRangeRemoved(0, prev)

        this.data.addAll(data)
        notifyItemRangeInserted(0, data.size + 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
        return TripViewHolder(
            RowWarBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),onItemClickListener
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as TripViewHolder).bind(data[position])
    }

    override fun getItemCount(): Int  = data.size


}

class TripViewHolder(private val binding: RowWarBinding, private val onItemClickListener: ((Trip) -> Unit)): RecyclerView.ViewHolder(binding.root) {
    var data: Trip? = null

    fun bind(data: Trip?){
        binding.name.text = data?.name
        binding.sub.text = data?.perid

        binding.root.setOnClickListener{
            data?.let{onItemClickListener(it)}
        }
    }
}