package com.example.armysecurity.ui.event

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.armysecurity.R
import com.example.armysecurity.data.Relics
import com.example.armysecurity.data.War
import com.example.armysecurity.data.WarMan
import com.example.armysecurity.databinding.FragmentCemeteryDaejeonDetailBinding
import com.example.armysecurity.databinding.FragmentCemeterySeoulDetailBinding
import com.example.armysecurity.databinding.FragmentWarDetailBinding
import org.parceler.Parcels

class WarDetailFragment : Fragment() {
    private lateinit var binding:FragmentWarDetailBinding
    private var relics: Relics? = null
    private var war: War? = null
    private var warMan: WarMan? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentWarDetailBinding.inflate(layoutInflater)
        arguments?.let {
            if(it.getString("type") == "Relics"){
                relics = Parcels.unwrap(it.getParcelable("data"))
            }else if(it.getString("type") == "War"){
                war = Parcels.unwrap(it.getParcelable("data"))
            }else if(it.getString("type") == "WarMan"){
                warMan = Parcels.unwrap(it.getParcelable("data"))
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        relics?.let {
            binding.relics = it
            binding.relicsLayout.visibility = View.VISIBLE
        }
        war?.let {
            binding.war = it
            binding.WarLayout.visibility = View.VISIBLE
        }
        warMan?.let {
            binding.warMan = it
            binding.WarManLayout.visibility = View.VISIBLE
        }
        return binding.root
    }
}