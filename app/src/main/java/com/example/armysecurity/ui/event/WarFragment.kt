package com.example.armysecurity.ui.event

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.armysecurity.R
import com.example.armysecurity.databinding.FragmentWarBinding
import com.example.armysecurity.db.AppDB
import com.example.armysecurity.ui.event.adapter.WarPagerAdapter
import com.example.armysecurity.ui.search.CemeteryAdapter
import com.example.armysecurity.viewModels.WarVM
import com.google.android.material.tabs.TabLayoutMediator
import org.parceler.Parcels


class WarFragment : Fragment() {
    private lateinit var binding:FragmentWarBinding

    private val viewModel: WarVM by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                WarVM(AppDB.getInstance(requireContext()).dbDao()) as T
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentWarBinding.inflate(layoutInflater)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        binding.pagerWar.adapter = activity?.let { WarPagerAdapter(viewModel, it.supportFragmentManager, lifecycle) }

        TabLayoutMediator(binding.tabWar, binding.pagerWar) { tab, position ->
            tab.text = resources.getStringArray(R.array.war_tab)[position]
        }.attach()


        return binding.root
    }


}