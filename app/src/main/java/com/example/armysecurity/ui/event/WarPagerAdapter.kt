package com.example.armysecurity.ui.event

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.armysecurity.ui.search.CemeteryFragment

class WarPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 ->  CemeteryFragment()
            1 ->  WarRelicsPage()
            2 ->  WarManPage()
            3 ->  WarInfoPage()
            else -> WarPlcPage()
        }
}