package com.example.armysecurity.ui.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.armysecurity.R
import com.example.armysecurity.data.Cemetery
import com.example.armysecurity.data.CemeteryDaejeon
import com.example.armysecurity.data.MyData
import com.example.armysecurity.data.MyDataList
import com.example.armysecurity.databinding.FragmentCemeteryDaejeonDetailBinding
import com.example.armysecurity.databinding.FragmentCemeterySeoulDetailBinding
import com.example.armysecurity.viewModels.MainVM
import org.parceler.Parcels

class CemeteryDetailFragment : Fragment() {
    private var LOCATE = "찾아보기 - 현충원 - "
    private var MEMO:String? = null
    private var bindingSeoul:FragmentCemeterySeoulDetailBinding? = null
    private var bindingDaejeon:FragmentCemeteryDaejeonDetailBinding? = null

    private var seoul: Cemetery? = null
    private var daejeon: CemeteryDaejeon? = null

    private val viewModel:MainVM by activityViewModels{
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                MainVM() as T
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            if(it.getString("type") == "seoul"){
                LOCATE += "서울"
                seoul = Parcels.unwrap(it.getParcelable("data"))
                bindingSeoul = FragmentCemeterySeoulDetailBinding.inflate(layoutInflater)
            }else{
                LOCATE += "대전"
                daejeon = Parcels.unwrap(it.getParcelable("data"))
                bindingDaejeon = FragmentCemeteryDaejeonDetailBinding.inflate(layoutInflater)
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingSeoul?.let {
            MEMO = viewModel.isBookmarke(MyData(LOCATE, cemetery = seoul!!))
            if(MEMO != null){
                it.fullHeart.visibility = View.VISIBLE
            }
            it.data = seoul
            it.fullHeart.setOnClickListener{ view ->
                viewModel.removeData(requireContext(), MyData(LOCATE,MEMO!!, cemetery = seoul!!))
                view.visibility = View.GONE
            }
            it.emptyHeart.setOnClickListener{ _ ->
                MEMO = ""
                viewModel.addData(requireContext(), MyData(LOCATE,cemetery = seoul!!))
                it.fullHeart.visibility = View.VISIBLE
            }
        }
        bindingDaejeon?.let {
            MEMO =viewModel.isBookmarke(MyData(LOCATE, cemeteryDaejeon = daejeon!!))
            if(MEMO != null){
                it.fullHeart.visibility = View.VISIBLE
            }
            it.data = daejeon
            it.fullHeart.setOnClickListener{ view ->
                viewModel.removeData(requireContext(), MyData(LOCATE,MEMO!!,cemeteryDaejeon = daejeon!!))
                view.visibility = View.GONE
            }
            it.emptyHeart.setOnClickListener{ _ ->
                MEMO = ""
                viewModel.addData(requireContext(), MyData(LOCATE,cemeteryDaejeon = daejeon!!))
                it.fullHeart.visibility = View.VISIBLE
            }
        }
        return bindingSeoul?.root ?: bindingDaejeon?.root
    }

}