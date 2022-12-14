package com.example.armysecurity.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.armysecurity.data.Fly
import com.example.armysecurity.data.MyData
import com.example.armysecurity.databinding.RowBookmarkeBinding
import com.example.armysecurity.databinding.RowCemeteryBinding

class MyPageAdapter (private val onItemClickListener: ((MyData) -> Unit), private val onEditListener: ((MyData) -> Unit)) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val data: ArrayList<MyData> = arrayListOf()

    fun setData(data: List<MyData>) {
        val prev = this.data.size
        this.data.clear()
        notifyItemRangeRemoved(0, prev)

        this.data.addAll(data)
        notifyItemRangeInserted(0, data.size + 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPageViewHolder {
        return MyPageViewHolder(
            RowBookmarkeBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),onItemClickListener, onEditListener
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MyPageViewHolder).bind(data[position])
    }

    override fun getItemCount(): Int  = data.size


}

class MyPageViewHolder(
    private val binding: RowBookmarkeBinding, private val onItemClickListener: ((MyData) -> Unit), private val onEditListener: ((MyData) -> Unit)
): RecyclerView.ViewHolder(binding.root) {
    var data: MyData? = null

    fun bind(data: MyData?){
        data?.let {
            binding.locate.text = data.locate
            binding.memo.text = data.memo
            binding.data1.text = getField1(it)
            binding.data2.text = getField2(it)
            setCategory(it)
        }

        binding.locate.setOnClickListener{ _ ->
            data?.let{onEditListener(it)}
        }
        binding.root.setOnClickListener{ _ ->
            data?.let{onItemClickListener(it)}
        }
    }
    private fun setCategory(d:MyData){
        binding.field1.text =
            if(d.fly !=null) "?????? ???"
            else if(d.relics !=null) "??????"
            else if(d.sale !=null) "?????? ???"
            else if(d.trip !=null) "???????????? ???"
            else if(d.war !=null) "?????? ???"
            else if(d.warMan !=null) "??????"
            else "????????? ???"
        binding.field2.text =
            if(d.fly !=null) "?????? ??????"
            else if(d.relics !=null) "?????????"
            else if(d.sale !=null) "?????? ?????? ???"
            else if(d.trip !=null) "???????????? ??????"
            else if(d.war !=null) "??????"
            else if(d.warMan !=null) "?????? ??????"
            else "?????? ??????"
    }

    private fun getField1(d:MyData):String =
        d.fly?.enatvnm ?: d.relics?.ttl ?: d.sale?.instltnnm ?: d.trip?.name ?: d.war?.title ?: d.warMan?.title ?: d.cemetery?.name ?: "${d.cemeteryDaejeon?.name}"

    private fun getField2(d:MyData):String =
        d.fly?.dates ?: d.relics?.obtmplace ?:d.sale?.dcntenatvnm ?: d.trip?.perid ?: d.war?.place ?: d.warMan?.award ?: d.cemetery?.movePlc ?: "${d.cemeteryDaejeon?.locname} ${d.cemeteryDaejeon?.block}"

}