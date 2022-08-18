package com.example.armysecurity.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.armysecurity.R
import com.example.armysecurity.databinding.FragmentCemeteryBranchBinding

class CemeteryBranchFragment : Fragment() {
    private lateinit var binding: FragmentCemeteryBranchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentCemeteryBranchBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding.seoulLayout.setOnClickListener{
            findNavController().navigate(R.id.action_cemeteryBranchFragment_to_cemeteryFragment)
        }
        binding.daejeonLayout.setOnClickListener{
            findNavController().navigate(R.id.action_cemeteryBranchFragment_to_cemeteryDaejeonFragment)
        }
        return binding.root
    }

}