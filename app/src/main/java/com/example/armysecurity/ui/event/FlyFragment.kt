package com.example.armysecurity.ui.event

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.armysecurity.R
import com.example.armysecurity.api.GithubAPI
import com.example.armysecurity.api.MndAPI
import com.example.armysecurity.data.Fly
import com.example.armysecurity.data.Trip
import com.example.armysecurity.databinding.FragmentFlyBinding
import com.example.armysecurity.databinding.FragmentTripBinding
import com.example.armysecurity.databinding.PageFlyInfoBinding
import com.example.armysecurity.databinding.PageFlyListBinding
import com.example.armysecurity.ui.event.adapter.FlyAdapter
import com.example.armysecurity.ui.event.adapter.FlyPagerAdapter
import com.example.armysecurity.ui.event.adapter.TripAdapter
import com.example.armysecurity.ui.event.adapter.WarPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.parceler.Parcels

class FlyFragment : Fragment() {
    private lateinit var binding:FragmentFlyBinding
    private val TAB_MENU = listOf("블랙이글스란","행사 일정")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentFlyBinding.inflate(layoutInflater)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.pagerView.adapter = activity?.let { FlyPagerAdapter(it.supportFragmentManager, lifecycle) }

        TabLayoutMediator(binding.tabLayout, binding.pagerView) { tab, position ->
            tab.text = TAB_MENU[position]
        }.attach()

        return binding.root
    }

}

class FlyInfoPage : Fragment(){
    private lateinit var binding:PageFlyInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PageFlyInfoBinding.inflate(layoutInflater)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.facebook.setOnClickListener{
            startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/blackeagle.kr"))
            )
        }
        binding.instagrame.setOnClickListener{
            startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/rokaf.blackeagles/"))
            )
        }
        binding.youtube.setOnClickListener{
            startActivity(Intent(Intent.ACTION_VIEW)
                .setData(Uri.parse("https://www.youtube.com/channel/UC6-a1WNabZX37MjQQkdcIxA"))
                .setPackage("com.google.android.youtube")
            )
        }


        return binding.root
    }

}


class FlyListPage : Fragment(){
    private lateinit var binding:PageFlyListBinding
    private lateinit var adapter: FlyAdapter
    private lateinit var data:List<Fly>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PageFlyListBinding.inflate(layoutInflater)
        adapter = FlyAdapter {
//            startActivity(
//                Intent(Intent.ACTION_VIEW, Uri.parse(it.glry_link_addr))
//            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding.rv.adapter = adapter
        binding.rowField.name.text = getString(R.string.fly_field_name)
        binding.rowField.rank.text = getString(R.string.fly_field_place)
        binding.rowField.identity.text = getString(R.string.fly_field_date)


        initData()
        return binding.root
    }

    private fun initData(){
        CoroutineScope(Dispatchers.IO).launch {
            data = GithubAPI.request.getFly().fly!!
            withContext(Dispatchers.Main){
                adapter.setData(data)
                binding.pbLayout.visibility = View.GONE
                if(data.isEmpty())
                    binding.emptyData.visibility = View.VISIBLE
            }

        }
    }
}

