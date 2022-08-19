package com.example.armysecurity.ui.event.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.armysecurity.data.Cemetery
import com.example.armysecurity.ui.event.*
import com.example.armysecurity.ui.search.CemeteryFragment
import com.example.armysecurity.viewModels.WarVM

class WarPagerAdapter(val vm:WarVM, fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 5
    }

    override fun createFragment(position: Int): Fragment =
        when (position) {

            0 ->  WarMainPage()
            1 ->  WarRelicsPage(vm)
            2 ->  WarInfoPage(vm)
            3 ->  WarManPage(vm)
            4 -> WarPlcPage()
            else -> WarMainPage()
        }

}