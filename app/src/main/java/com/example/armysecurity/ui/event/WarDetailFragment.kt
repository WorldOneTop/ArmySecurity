package com.example.armysecurity.ui.event

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.armysecurity.R
import com.example.armysecurity.data.*
import com.example.armysecurity.databinding.FragmentCemeteryDaejeonDetailBinding
import com.example.armysecurity.databinding.FragmentCemeterySeoulDetailBinding
import com.example.armysecurity.databinding.FragmentWarDetailBinding
import com.example.armysecurity.viewModels.MainVM
import com.google.gson.annotations.SerializedName
import org.parceler.Parcels

class WarDetailFragment : Fragment() {
    private var LOCATE = "참여하기 - "
    private var MEMO:String? = null
    private lateinit var binding:FragmentWarDetailBinding
    private var relics: Relics? = null
    private var war: War? = null
    private var warMan: WarMan? = null
    private var trip: Trip? = null
    private var sale: Sale? = null
    private val viewModel: MainVM by activityViewModels{
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                MainVM() as T
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentWarDetailBinding.inflate(layoutInflater)
        arguments?.let {
            when(it.getString("type")){
                "Relics" -> {
                    relics = Parcels.unwrap(it.getParcelable("data"))
                    LOCATE += "전쟁 기념관 - 유물 목록"
                }
                "War" -> {
                    war = Parcels.unwrap(it.getParcelable("data"))
                    LOCATE += "전쟁 기념관 - 전쟁 정보"
                }
                "WarMan" -> {
                    warMan = Parcels.unwrap(it.getParcelable("data"))
                    LOCATE += "전쟁 기념관 - 전쟁 영웅"
                }
                "Trip" -> {
                    trip = Parcels.unwrap(it.getParcelable("data"))
                    LOCATE += "안보견학 현장체험학습"
                }
                "Sale" -> {
                    sale = Parcels.unwrap(it.getParcelable("data"))
                    LOCATE += "병사 할인 혜택 정보"
                }
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
        trip?.let {
            binding.trip = it
            binding.tripLayout.visibility = View.VISIBLE
        }
        sale?.let {
            binding.sale = it
            binding.saleLayout.visibility = View.VISIBLE
        }
        if(isBookmarke()){
            binding.fullHeart.visibility = View.VISIBLE
        }
        binding.fullHeart.setOnClickListener{
            viewModel.removeData(requireContext(), createMyData())
            it.visibility = View.GONE
        }
        binding.emptyHeart.setOnClickListener{
            viewModel.addData(requireContext(), createMyData())
            binding.fullHeart.visibility = View.VISIBLE
        }

        return binding.root
    }

    private fun isBookmarke(): Boolean{
        MEMO = viewModel.isBookmarke(createMyData())
        return MEMO != null
    }
    private fun createMyData() =
        MyData(LOCATE, MEMO ?: "", relics = relics, war = war, warMan = warMan, trip = trip, sale = sale)
}