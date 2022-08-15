package com.example.armysecurity.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.armysecurity.R
import com.example.armysecurity.databinding.FragmentEventBinding
import com.example.armysecurity.databinding.FragmentGuarderBinding

class EventFragment : Fragment() {
    private lateinit var binding: FragmentEventBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentEventBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initListener()

        return binding.root
    }

    private fun initListener(){

        binding.warLayout.setOnClickListener{
            findNavController().navigate(R.id.action_tab_btm_event_to_warFragment)
        }
        binding.tripLayout.setOnClickListener{
            findNavController().navigate(R.id.action_tab_btm_event_to_tripFragment)
        }
        binding.flyLayout.setOnClickListener{
            findNavController().navigate(R.id.action_tab_btm_event_to_flyFragment)
        }
        binding.saleLayout.setOnClickListener{
            findNavController().navigate(R.id.action_tab_btm_event_to_saleFragment)
        }
        binding.pledgeLayout.setOnClickListener{
            findNavController().navigate(R.id.action_tab_btm_event_to_pledgeFragment)
        }
    }

}