package com.example.armysecurity.ui.search

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.example.armysecurity.databinding.FragmentGuarderBinding


class GuarderFragment : Fragment() {
    private lateinit var binding: FragmentGuarderBinding
    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentGuarderBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        initWebView()
        return binding.root
    }


    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView(){
        val dialog = Dialog(requireContext())
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(ProgressBar(requireContext()))
        dialog.setCanceledOnTouchOutside(false)
        dialog.setOnCancelListener { activity?.onBackPressed() }
        dialog.show()

        webView = binding.root
        webView.settings.javaScriptEnabled = true
        webView.loadUrl("file:///android_asset/guarder.html") //https://www.imhc.mil.kr/cop/search/searchGonghunList.do
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                if(newProgress >= 100) {
                    dialog.dismiss()
                }
                super.onProgressChanged(view, newProgress)
            }
        }
    }
}
