package com.example.armysecurity.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.armysecurity.R
import com.example.armysecurity.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentSearchBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding.guarderLayout.setOnClickListener{
            findNavController().navigate(R.id.action_tab_btm_search_to_guarderFragment)
        }
        binding.cemeteryLayout.setOnClickListener{
            findNavController().navigate(R.id.action_tab_btm_search_to_cemeteryFragment)
        }

        return binding.root
    }

}