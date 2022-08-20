package com.example.armysecurity.ui.event

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.armysecurity.R
import com.example.armysecurity.databinding.PageWarMainBinding


// 전쟁기념관 메인
class WarMainPage : Fragment() {
    private lateinit var binding:PageWarMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PageWarMainBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.warHomepage.setOnClickListener{
            startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.war_main_page)))
            )
        }
        return binding.root
    }

}