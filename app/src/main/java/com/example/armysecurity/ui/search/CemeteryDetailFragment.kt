package com.example.armysecurity.ui.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.armysecurity.R
import com.example.armysecurity.data.Cemetery
import com.example.armysecurity.data.CemeteryDaejeon
import com.example.armysecurity.databinding.FragmentCemeteryDaejeonDetailBinding
import com.example.armysecurity.databinding.FragmentCemeterySeoulDetailBinding
import org.parceler.Parcels

class CemeteryDetailFragment : Fragment() {
    private var bindingSeoul:FragmentCemeterySeoulDetailBinding? = null
    private var bindingDaejeon:FragmentCemeteryDaejeonDetailBinding? = null

    private var seoul: Cemetery? = null
    private var daejeon: CemeteryDaejeon? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            if(it.getString("type") == "seoul"){
                seoul = Parcels.unwrap(it.getParcelable("data"))
                bindingSeoul = FragmentCemeterySeoulDetailBinding.inflate(layoutInflater)
            }else{
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
            it.data = seoul
        }
        bindingDaejeon?.let {
            it.data = daejeon
        }
        return bindingSeoul?.root ?: bindingDaejeon?.root
    }

}