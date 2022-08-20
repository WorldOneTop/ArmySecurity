package com.example.armysecurity.ui.event

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.armysecurity.R
import com.example.armysecurity.api.MndAPI
import com.example.armysecurity.data.Trip
import com.example.armysecurity.databinding.*
import com.example.armysecurity.ui.event.adapter.FlyAdapter
import com.example.armysecurity.ui.event.adapter.FlyPagerAdapter
import com.example.armysecurity.ui.event.adapter.TripAdapter
import com.example.armysecurity.ui.event.adapter.TripPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.parceler.Parcels


class TripFragment : Fragment() {
    private lateinit var binding:FragmentTripBinding
    private val TAB_MENU = listOf("위치 정보","행사 일정")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentTripBinding.inflate(layoutInflater)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.pagerView.adapter = activity?.let { TripPagerAdapter(it.supportFragmentManager, lifecycle) }

        TabLayoutMediator(binding.tabLayout, binding.pagerView) { tab, position ->
            tab.text = TAB_MENU[position]
        }.attach()

        return binding.root
    }
}

class TripInfoPage : Fragment(){
    private lateinit var binding: PageTripInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PageTripInfoBinding.inflate(layoutInflater)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.hompage.setOnClickListener{
            startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse("https://www.army.mil.kr/event/security/index.jsp"))
            )
        }
        return binding.root
    }

}

class TripListPage : Fragment(){
    private lateinit var binding:PageTripListBinding
    private lateinit var adapter: TripAdapter
    private lateinit var data:List<Trip>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PageTripListBinding.inflate(layoutInflater)
        adapter = TripAdapter {
            findNavController().navigate(R.id.action_tripFragment_to_warDetailFragment,
                bundleOf("type" to "Trip", "data" to Parcels.wrap(it))
            )

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding.rvTrip.adapter = adapter
        binding.rowField.name.text = getString(R.string.trip_field_name)
        binding.rowField.sub.text = getString(R.string.war_field_date)


        initData()
        return binding.root
    }

    private fun initData(){
        CoroutineScope(Dispatchers.IO).launch {
            data = MndAPI.request.getData(MndAPI.TYPE.TRIP,0,300).TRIP.row
            withContext(Dispatchers.Main){
                adapter.setData(data)
                binding.pbTripLayout.visibility = View.GONE
                if(data.isEmpty())
                    binding.emptyData.visibility = View.VISIBLE
            }

        }
    }

}

